package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.AdjGraph;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class PathSearchTest {
  private final Map<Integer, AdjGraph.Vertex<Integer>> verticesCache = new ConcurrentHashMap<>();

  @Test
  void hashCodeReturnsSameValueForTwoDifferentButEqualObjects() {
    final AdjGraph.Vertex<Integer> integerVertex1 = new AdjGraph.Vertex<>(50);
    final AdjGraph.Vertex<Integer> integerVertex2 = new AdjGraph.Vertex<>(50);
    Assertions.assertEquals(integerVertex1.hashCode(), integerVertex2.hashCode());
  }

  @Test
  void equalsReturnsTrueForTwoDifferentButEqualObjects() {
    final AdjGraph.Vertex<Integer> integerVertex1 = new AdjGraph.Vertex<>(50);
    final AdjGraph.Vertex<Integer> integerVertex2 = new AdjGraph.Vertex<>(50);
    Assertions.assertEquals(integerVertex1, integerVertex2);
  }

  @Test
  void breadthFirstTraverseFindsCorrectPaths() {
    final AdjGraph<Integer> graph = createGraph();
    Assert.assertEquals(expectedPath(), new BFS<>(graph).traverse(new AdjGraph.Vertex<>(2)));
  }

  @Test
  void depthFirstSearchTraverseFindsCorrectPaths() {
    final AdjGraph<Integer> graph = createGraph();
    Assert.assertEquals(expectedPath(), new DFS<>(graph).traverse(new AdjGraph.Vertex<>(2)));
  }

  private List<AdjGraph.Vertex<Integer>> expectedPath() {
    final List<AdjGraph.Vertex<Integer>> expectedPath = new LinkedList<>();
    expectedPath.add(new AdjGraph.Vertex<>(2));
    expectedPath.add(new AdjGraph.Vertex<>(0));
    expectedPath.add(new AdjGraph.Vertex<>(1));
    expectedPath.add(new AdjGraph.Vertex<>(3));
    return expectedPath;
  }

  private AdjGraph<Integer> createGraph() {
    final AdjGraph<Integer> graph = new AdjGraph<>();
    createEdge(0, 1, graph, 1);
    createEdge(0, 2, graph, 1);
    createEdge(1, 2, graph, 1);
    createEdge(2, 0, graph, 1);
    createEdge(2, 3, graph, 1);
    return graph;
  }

  void createEdge(final int v1, final int v2, final AdjGraph<Integer> graph, final int weight) {
    final AdjGraph.Vertex<Integer> vertex1 = verticesCache.computeIfAbsent(v1, integer -> new AdjGraph.Vertex<>(v1));
    final AdjGraph.Vertex<Integer> vertex2 = verticesCache.computeIfAbsent(v2, integer -> new AdjGraph.Vertex<>(v2));
    graph.addNeighbor(vertex1, vertex2, weight);
  }

}