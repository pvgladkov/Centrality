import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import hc.HarmonicCentrality


BigDecimal(2.toDouble / 6).setScale(5, BigDecimal.RoundingMode.UP).toDouble

val g = Graph[Int, HyperEdge](1~2, 1~3, 1~4, 2~3)

for (node <- g.get(1).diSuccessors.iterator){
  println(node.toString)
}
for (node <- g.get(2).diSuccessors.iterator){
  println(node.toString)
}
for (node <- g.get(3).diSuccessors.iterator){
  println(node.toString)
}
for (node <- g.get(4).diSuccessors.iterator){
  println(node.toString)
}
HarmonicCentrality(g)
