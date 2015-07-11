package hc

import com.twitter.algebird._
import collection.mutable
import scalax.collection.Graph
import scalax.collection.GraphEdge._


class HarmonicCentrality[T] {

  def apply(G: Graph[T, HyperEdge], max_distance: Int = 6):mutable.HashMap[T, Double] = {

    val BIT_SIZE = 12
    val hll = new HyperLogLogMonoid(BIT_SIZE)

    val harmonic = new mutable.HashMap[T, Double]() {
      override def default(key:T) = 0.toDouble
    }

    val t_steps_set = new mutable.HashMap[T, mutable.HashMap[Int, HLL]]() {
      override def default(key:T) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }
    }

    for (node <- G.nodes.iterator){
      val _item = node.toString().getBytes

      t_steps_set(node) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }

      t_steps_set(node)(0) += hll.create(_item)
    }

    for (distance <- 1 to max_distance) {
      for (node <- G.nodes.iterator){

        t_steps_set(node)(distance) += t_steps_set(node)(distance - 1)

        for (next_node <- G.get(node).diSuccessors.iterator){
          t_steps_set(node)(distance) += t_steps_set(next_node)(distance - 1)
        }

        val current = t_steps_set(node)(distance).estimatedSize
        val prev = t_steps_set(node)(distance - 1).estimatedSize

        harmonic(node) += BigDecimal((current - prev) / distance)
          .setScale(5, BigDecimal.RoundingMode.HALF_UP)
          .toDouble
      }
    }

    harmonic
  }
}
