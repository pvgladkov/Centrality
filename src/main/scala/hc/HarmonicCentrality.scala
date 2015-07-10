package hc

import com.twitter.algebird._
import collection.mutable
import scalax.collection.Graph
import scalax.collection.GraphEdge._


object HarmonicCentrality {

  def apply(G: Graph[Int, HyperEdge], max_distance: Int = 6):mutable.HashMap[String, Double] = {

    val BIT_SIZE = 12
    val hll = new HyperLogLogMonoid(BIT_SIZE)

    val harmonic = new mutable.HashMap[String, Double]() {
      override def default(key:String) = 0.toDouble
    }

    val t_steps_set = new mutable.HashMap[String, mutable.HashMap[Int, HLL]]() {
      override def default(key:String) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }
    }

    for (node <- G.nodes.iterator){
      val _item = node.toString().getBytes

      t_steps_set(node.toString()) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }

      t_steps_set(node.toString())(0) += hll.create(_item)
    }

    for (distance <- 1 to max_distance) {
      for (node <- G.nodes.iterator){

        t_steps_set(node.toString())(distance) += t_steps_set(node.toString())(distance - 1)

        for (next_node <- G.get(node).diSuccessors.iterator){
          t_steps_set(node.toString())(distance) += t_steps_set(next_node.toString())(distance - 1)
        }

        val current = t_steps_set(node.toString())(distance).estimatedSize
        val prev = t_steps_set(node.toString())(distance - 1).estimatedSize

        harmonic(node.toString()) += BigDecimal((current - prev) / distance)
          .setScale(5, BigDecimal.RoundingMode.HALF_UP)
          .toDouble
      }
    }

    harmonic
  }
}
