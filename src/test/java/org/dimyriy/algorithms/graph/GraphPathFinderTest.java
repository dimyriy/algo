package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.LabeledPoint2d;
import org.dimyriy.datastructures.graph.AdjGraph;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class GraphPathFinderTest {
  private Map<Integer, AdjGraph.Vertex<Integer>> verticesCache;
  private Map<String, LabeledPoint2d> points;

  @BeforeEach
  void setUp() {
    points = new HashMap<>();
    verticesCache = new HashMap<>();
  }

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

  void assertSimpleShortestPathIsCorrect(final GraphPathFinder<Integer> algorithm) {
    final List<AdjGraph.Vertex<Integer>> calculatedShortestPath = algorithm.findPath(new AdjGraph.Vertex<>(1), new AdjGraph.Vertex<>(6));
    Assertions.assertEquals(expectedShortestPath(), calculatedShortestPath);
  }

  AdjGraph<LabeledPoint2d> createGeometricalGraph() {
    final AdjGraph<LabeledPoint2d> graph = new AdjGraph<>();
    points.put("A", new LabeledPoint2d(1000, 1000, "A"));
    points.put("B", new LabeledPoint2d(2000, 3000, "B"));
    points.put("C", new LabeledPoint2d(1000, 4000, "C"));
    points.put("D", new LabeledPoint2d(3000, 2000, "D"));
    points.put("E", new LabeledPoint2d(4000, 2000, "E"));
    points.put("F", new LabeledPoint2d(5000, 3000, "F"));
    points.put("G", new LabeledPoint2d(4000, 4000, "G"));
    points.put("H", new LabeledPoint2d(3000, 5000, "H"));
    points.put("I", new LabeledPoint2d(1000, 8000, "I"));
    points.put("J", new LabeledPoint2d(10000, 8000, "J"));
    points.put("K", new LabeledPoint2d(7000, 3000, "K"));
    points.put("L", new LabeledPoint2d(6000, 4000, "L"));
    points.put("M", new LabeledPoint2d(5000, 5000, "M"));
    points.put("N", new LabeledPoint2d(4000, 6000, "N"));
    points.put("O", new LabeledPoint2d(9000, 4000, "O"));
    points.put("P", new LabeledPoint2d(7000, 6000, "P"));
    points.put("Q", new LabeledPoint2d(6000, 6000, "Q"));
    points.put("R", new LabeledPoint2d(5000, 7000, "R"));
    addEdge(graph, "A", "B");
    addEdge(graph, "B", "D");
    addEdge(graph, "D", "E");
    addEdge(graph, "E", "F");
    addEdge(graph, "F", "L");
    addEdge(graph, "L", "K");
    addEdge(graph, "K", "O");
    addEdge(graph, "O", "J");
    addEdge(graph, "J", "P");
    addEdge(graph, "L", "O");
    addEdge(graph, "L", "G");
    addEdge(graph, "B", "G");
    addEdge(graph, "G", "P");
    addEdge(graph, "P", "Q");
    addEdge(graph, "Q", "M");
    addEdge(graph, "G", "N");
    addEdge(graph, "N", "R");
    addEdge(graph, "N", "H");
    addEdge(graph, "C", "H");
    addEdge(graph, "B", "H");
    addEdge(graph, "A", "C");
    addEdge(graph, "C", "I");
    addEdge(graph, "R", "I");
    addEdge(graph, "K", "P");
    addEdge(graph, "C", "R");
    addEdge(graph, "M", "G");
    return graph;
  }

  void assertShortestPathInGeoGraph(final GraphPathFinder<LabeledPoint2d> algorithm) {
    final List<AdjGraph.Vertex<LabeledPoint2d>> path = algorithm.findPath(new AdjGraph.Vertex<>(getPointFromCache("Q")),
                                                                          new AdjGraph.Vertex<>(getPointFromCache("A")));
    Assertions.assertEquals(getExpectedShortestPathInGeometricalGraph(), path);
  }

  private void createEdge(final int v1, final int v2, final AdjGraph<Integer> graph, final int weight) {
    final AdjGraph.Vertex<Integer> vertex1 = verticesCache.computeIfAbsent(v1, integer -> new AdjGraph.Vertex<>(v1));
    final AdjGraph.Vertex<Integer> vertex2 = verticesCache.computeIfAbsent(v2, integer -> new AdjGraph.Vertex<>(v2));
    graph.addNeighbor(vertex1, vertex2, weight);
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

  private List<AdjGraph.Vertex<Integer>> expectedShortestPath() {
    final List<AdjGraph.Vertex<Integer>> pathToVertex = new LinkedList<>();
    pathToVertex.add(new AdjGraph.Vertex<>(1));
    pathToVertex.add(new AdjGraph.Vertex<>(2));
    pathToVertex.add(new AdjGraph.Vertex<>(5));
    pathToVertex.add(new AdjGraph.Vertex<>(6));
    return pathToVertex;
  }

  private LabeledPoint2d getPointFromCache(final String label) {
    return points.get(label);
  }

  private List<AdjGraph.Vertex<LabeledPoint2d>> getExpectedShortestPathInGeometricalGraph() {
    return Arrays.asList(
        new AdjGraph.Vertex<>(getPointFromCache("Q")),
        new AdjGraph.Vertex<>(getPointFromCache("M")),
        new AdjGraph.Vertex<>(getPointFromCache("G")),
        new AdjGraph.Vertex<>(getPointFromCache("B")),
        new AdjGraph.Vertex<>(getPointFromCache("A"))
    );
  }

  private void addEdge(final AdjGraph<LabeledPoint2d> graph, final String label1, final String label2) {
    addEdge(graph, points.get(label1), points.get(label2));
  }

  private void addEdge(final AdjGraph<LabeledPoint2d> graph, final LabeledPoint2d point1, final LabeledPoint2d point2) {
    graph.addNeighbor(new AdjGraph.Vertex<>(point1), new AdjGraph.Vertex<>(point2), (int) Math.ceil(point1.distance(point2)));
  }
}