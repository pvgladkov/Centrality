package hc

import com.twitter.algebird._
import collection.mutable


trait HarmonicCentrality[T] {

  def harmonic_centrality(G: T, max_distance: Int = 6) = {

    val BIT_SIZE = 12
    val hll = new HyperLogLogMonoid(BIT_SIZE)

    var harmonic = new mutable.HashMap[Int, Int]() {
      override def default(key:Int) = 0
    }

    var t_steps_set = new mutable.HashMap[Int, mutable.HashMap[Int, HLL]]() {
      override def default(key:Int) = new mutable.HashMap[Int, HLL]() {
        override def default(key:Int) = hll.zero
      }
    }
  }
}
