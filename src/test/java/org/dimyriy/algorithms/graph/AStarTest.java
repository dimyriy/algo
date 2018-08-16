package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.LabeledPoint2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.08.18
 */
class AStarTest {
  private GraphPathFinderTest pathFinderTest;

  @BeforeEach
  void setUp() {
    pathFinderTest = new GraphPathFinderTest();
    pathFinderTest.setUp();
  }

  @Test
  void testFindShortestPathInGeometricalGraphReturnsCorrectPath() {
    final GraphPathFinder<LabeledPoint2d> algorithm = new AStar<>(pathFinderTest.createGeometricalGraph());
    pathFinderTest.assertShortestPathInGeoGraph(algorithm);
  }
}
