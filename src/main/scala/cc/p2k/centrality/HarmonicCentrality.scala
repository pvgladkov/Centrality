package cc.p2k.centrality

import com.twitter.algebird._
import collection.mutable
import scalax.collection.Graph
import scalax.collection.GraphEdge._


object HarmonicCentrality {

  def apply[T](G: Graph[T, HyperEdge], maxDistance: Int = 6):mutable.HashMap[T, Double] = {

    val BIT_SIZE = 12
    val hll = new HyperLogLogMonoid(BIT_SIZE)

    val harmonic = new mutable.HashMap[T, Double]() {
      override def default(key:T) = 0.toDouble
    }

    val tStepsSet = new mutable.HashMap[T, mutable.HashMap[Int, HLL]]() {
      override def default(key:T) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }
    }

    for (node <- G.nodes.iterator){
      val _item = node.toString().getBytes

      tStepsSet(node) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = new HyperLogLogMonoid(BIT_SIZE).zero
      }

      tStepsSet(node)(0) += hll.create(_item)
    }

    for (distance <- 1 to maxDistance) {
      for (node <- G.nodes.iterator){

        tStepsSet(node)(distance) += tStepsSet(node)(distance - 1)

        for (next_node <- G.get(node).diSuccessors.iterator){
          tStepsSet(node)(distance) += tStepsSet(next_node)(distance - 1)
        }

        val current = tStepsSet(node)(distance).estimatedSize
        val prev = tStepsSet(node)(distance - 1).estimatedSize

        harmonic(node) += BigDecimal((current - prev) / distance)
          .setScale(5, BigDecimal.RoundingMode.HALF_UP)
          .toDouble
      }
    }

    harmonic
  }
}
