package hc

import com.twitter.algebird._
import collection.mutable
import scalax.collection.Graph


trait HarmonicCentrality {

  def harmonic_centrality(G: Graph, max_distance: Int = 6):mutable.HashMap[String, Int] = {

    val BIT_SIZE = 12
    val hll = new HyperLogLogMonoid(BIT_SIZE)

    val harmonic = new mutable.HashMap[String, Int]() {
      override def default(key:String) = 0
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

        val current = t_steps_set(node.toString())(distance).estimatedSize.toInt
        val prev = t_steps_set(node.toString())(distance - 1).estimatedSize.toInt
        harmonic(node.toString()) += (current - prev) / distance
      }
    }

    harmonic
  }
}
