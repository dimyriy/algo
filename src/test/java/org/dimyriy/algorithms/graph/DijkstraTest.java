package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.AdjGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class DijkstraTest {
  private PathSearchTest pathSearchTest;

  @BeforeEach
  void setUp() {
    pathSearchTest = new PathSearchTest();
  }

  @Test
  void findShortestPath() {
    Assertions.assertEquals(shortestPath(), new Dijkstra<>(createSimpleGraph()).findShortestPath(new AdjGraph.Vertex<>(1), new AdjGraph.Vertex<>(6)));
  }

  private List<AdjGraph.Vertex<Integer>> shortestPath() {
    final List<AdjGraph.Vertex<Integer>> pathToVertex = new LinkedList<>();
    pathToVertex.add(new AdjGraph.Vertex<>(1));
    pathToVertex.add(new AdjGraph.Vertex<>(2));
    pathToVertex.add(new AdjGraph.Vertex<>(5));
    pathToVertex.add(new AdjGraph.Vertex<>(6));
    return pathToVertex;
  }

  private AdjGraph<Integer> createSimpleGraph() {
    final AdjGraph<Integer> graph = new AdjGraph<>();
    pathSearchTest.createEdge(1, 2, graph, 2);
    pathSearchTest.createEdge(1, 3, graph, 4);
    pathSearchTest.createEdge(2, 4, graph, 4);
    pathSearchTest.createEdge(2, 3, graph, 1);
    pathSearchTest.createEdge(2, 5, graph, 2);
    pathSearchTest.createEdge(3, 5, graph, 3);
    pathSearchTest.createEdge(4, 5, graph, 3);
    pathSearchTest.createEdge(4, 6, graph, 2);
    pathSearchTest.createEdge(5, 6, graph, 2);
    return graph;
  }
}
