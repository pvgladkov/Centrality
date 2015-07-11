package hc

import org.scalatest._
import org.scalatest.FunSuite

import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._

class HarmonicCentralityTest extends Suite {

  def testGraph1(info: Informer) {
    val g = Graph[Int, HyperEdge](1~2, 1~3, 1~4, 2~3)
    val res = HarmonicCentrality(g)
    for ((k,v) <- res.iterator){
      info((k,v).toString())
    }
  }

  def testGraph2(info: Informer) {
    val g = Graph[Int, HyperEdge](1~2, 2~3, 3~4, 2~4, 5~1)
    val res = HarmonicCentrality(g)
    for ((k,v) <- res.iterator){
      info((k,v).toString())
    }
  }
}