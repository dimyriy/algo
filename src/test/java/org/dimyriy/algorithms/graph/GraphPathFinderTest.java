package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class GraphPathFinderTest {
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

  private void createEdge(final int v1, final int v2, final AdjGraph<Integer> graph, final int weight) {
    final AdjGraph.Vertex<Integer> vertex1 = verticesCache.computeIfAbsent(v1, integer -> new AdjGraph.Vertex<>(v1));
    final AdjGraph.Vertex<Integer> vertex2 = verticesCache.computeIfAbsent(v2, integer -> new AdjGraph.Vertex<>(v2));
    graph.addNeighbor(vertex1, vertex2, weight);
  }

  @Test
  void depthFirstSearchTraverseFindsCorrectPaths() {
    final AdjGraph<Integer> graph = createGraph();
    Assert.assertEquals(expectedPath(), new DFS<>(graph).traverse(new AdjGraph.Vertex<>(2)));
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

  private List<AdjGraph.Vertex<Integer>> expectedPath() {
    final List<AdjGraph.Vertex<Integer>> expectedPath = new LinkedList<>();
    expectedPath.add(new AdjGraph.Vertex<>(2));
    expectedPath.add(new AdjGraph.Vertex<>(0));
    expectedPath.add(new AdjGraph.Vertex<>(1));
    expectedPath.add(new AdjGraph.Vertex<>(3));
    return expectedPath;
  }

  AdjGraph<Integer> createSimpleGraph() {
    final AdjGraph<Integer> graph = new AdjGraph<>();
    createEdge(1, 2, graph, 2);
    createEdge(1, 3, graph, 4);
    createEdge(2, 4, graph, 4);
    createEdge(2, 3, graph, 1);
    createEdge(2, 5, graph, 2);
    createEdge(3, 5, graph, 3);
    createEdge(4, 5, graph, 3);
    createEdge(4, 6, graph, 2);
    createEdge(5, 6, graph, 2);
    return graph;
  }

  List<AdjGraph.Vertex<Integer>> expectedShortestPath() {
    final List<AdjGraph.Vertex<Integer>> pathToVertex = new LinkedList<>();
    pathToVertex.add(new AdjGraph.Vertex<>(1));
    pathToVertex.add(new AdjGraph.Vertex<>(2));
    pathToVertex.add(new AdjGraph.Vertex<>(5));
    pathToVertex.add(new AdjGraph.Vertex<>(6));
    return pathToVertex;
  }

  void assertSimpleShortestPathIsCorrect(GraphPathFinder<Integer> algorithm) {
    final List<AdjGraph.Vertex<Integer>> calculatedShortestPath = algorithm.findPath(new AdjGraph.Vertex<>(1), new AdjGraph.Vertex<>(6));
    Assertions.assertEquals(expectedShortestPath(), calculatedShortestPath);
  }
}