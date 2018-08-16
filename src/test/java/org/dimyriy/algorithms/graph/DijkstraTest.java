package org.dimyriy.algorithms.graph;

import org.junit.jupiter.api.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class DijkstraTest {
  private GraphPathFinderTest pathFinderTest;

  @BeforeEach
  void setUp() {
    pathFinderTest = new GraphPathFinderTest();
  }

  @Test
  void findShortestPath() {
    final Dijkstra<Integer> algorithm = new Dijkstra<>(pathFinderTest.createSimpleGraph());
    pathFinderTest.assertSimpleShortestPathIsCorrect(algorithm);
  }

}
