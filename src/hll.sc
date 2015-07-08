import com.twitter.algebird.{HyperLogLogMonoid, HyperLogLog, HLL, DenseHLL}
import scala.collection.mutable
import scala.math.BigInt.int2bigInt
val BIT_SIZE = 12
val hll = new HyperLogLogMonoid(BIT_SIZE)
var globalHll = hll.zero
val ids_1 = Array(12, 4, 555, 4).map(_.toByteArray)
val h = ids_1.map(id_array => hll.create(id_array)).reduce(_ + _)
h.estimatedSize.toInt
globalHll.estimatedSize.toInt

var harmonic = new mutable.HashMap[Int, Int]() {
  override def default(key:Int) = 5
}

var t_steps_set = new mutable.HashMap[Int, mutable.HashMap[Int, HLL]]() {
  override def default(key:Int) = new mutable.HashMap[Int, HLL]() {
    override def default(key:Int) = hll.zero
  }
}

t_steps_set(4)(4).estimatedSize.toInt


import scalax.collection.Graph
import scalax.collection.GraphPredef._
val g = Graph(1~2)
g.edges