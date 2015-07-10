package hc

import org.scalatest._
import org.scalatest.FunSuite

import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._

class HarmonicCentralityTest extends FunSuite with ShouldMatchers {

  test("first") {
    val g = Graph[Int, HyperEdge](1~2, 1~3, 1~4, 2~3)
    val res = HarmonicCentrality(g)
    for ((k,v) <- res.iterator){
      println(k,v)
    }
  }

  test("second") {
    assert(true)
  }
}