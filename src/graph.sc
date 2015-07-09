import scalax.collection.Graph
import scalax.collection.GraphPredef._
val g = Graph(1~2, 3)
for (node <- g.nodes.iterator){
  println(node.toString())
}

val max_distance = 6
for (distance <- 1 to max_distance) {
  println(distance)
}