package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.LabeledPoint2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class DijkstraTest {
  private GraphPathFinderTest pathFinderTest;

  @BeforeEach
  void setUp() {
    pathFinderTest = new GraphPathFinderTest();
    pathFinderTest.setUp();
  }

  @Test
  void testFindShortestPathReturnsCorrectPath() {
    pathFinderTest.assertSimpleShortestPathIsCorrect(new Dijkstra<>(pathFinderTest.createSimpleGraph()));
  }


  @Test
  void testFindShortestPathInGeometricalGraphReturnsCorrectPath() {
    final GraphPathFinder<LabeledPoint2d> algorithm = new Dijkstra<>(pathFinderTest.createGeometricalGraph());
    pathFinderTest.assertShortestPathInGeoGraph(algorithm);
  }

}
