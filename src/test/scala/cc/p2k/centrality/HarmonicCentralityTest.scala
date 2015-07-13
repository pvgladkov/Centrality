package cc.p2k.centrality

import org.scalatest._

import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._

class HarmonicCentralityTest extends FunSuite {

  test("1") {
    val g = Graph[Int, HyperEdge](1~2, 1~3, 1~4, 2~3)
    val res = HarmonicCentrality[Int](g)
    val valid = Map(1->3.0, 2->2.5, 3->2.5, 4->2.0)
    for ((k,v) <- res.iterator){
      assert(
        BigDecimal(v).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble == valid(k)
      )
    }
  }

  test("2") {
    val g = Graph[Int, HyperEdge](1~2, 2~3, 3~4, 2~4, 5~1)
    val res = HarmonicCentrality[Int](g)
    val valid = Map(1->3.0, 2->3.5, 3->2.8, 4->2.8, 5->2.2)
    for ((k,v) <- res.iterator){
      assert(
        BigDecimal(v).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble == valid(k)
      )
    }
  }

  test("3") {
    val g = Graph[Int, HyperEdge](1~>2, 2~>3, 3~>4, 2~>4, 5~1)
    val res = HarmonicCentrality[Int](g)
    val valid = Map(1->3.0, 2->2.0, 3->1.0, 4->0.0, 5->2.2)
    for ((k,v) <- res.iterator){
      assert(
        BigDecimal(v).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble == valid(k)
      )
    }
  }

  test("4") {
    val g = Graph[String, HyperEdge]("one"~>"two")
    val res = HarmonicCentrality[String](g)
    val valid = Map("one"->1.0, "two"->0.0)
    for ((k,v) <- res.iterator){
      assert(
        BigDecimal(v).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble == valid(k)
      )
    }
  }
}