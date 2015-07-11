# Harmonic Centrality

Harmonic centrality of a node x is the sum of the reciprocal of the shortest path distances from all other nodes to x.

Math formula:

![formula](http://upload.wikimedia.org/math/b/b/0/bb039f0850211e3f763c648178cb30b4.png)

It uses HyperLogLog algorithm.

Example:

```scala
import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import hc.HarmonicCentrality

val g = Graph[Int, HyperEdge](1~2, 2~3, 3~4, 2~4, 5~1)
val res = HarmonicCentrality[Int](g)
for ((k,v) <- res.iterator){
  println((k, v).toString())
}

```

Output:

```
(2,3.50238)
(5,2.168)
(4,2.8351100000000002)
(1,3.0019600000000004)
(3,2.8351100000000002)
```

links:

1. https://events.yandex.ru/lib/talks/1287/
2. http://infoscience.epfl.ch/record/200525/files/%5BEN%5DASNA09.pdf
3. https://en.wikipedia.org/wiki/Centrality
