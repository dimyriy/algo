package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.LabeledPoint2d;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
    final GraphPathFinder<LabeledPoint2d> algorithm = new AStar(pathFinderTest.createGeometricalGraph());
    pathFinderTest.assertShortestPathInGeoGraph(algorithm);
  }

  @Test
  void testFindShortestPathInGeometricalGraphReturnsCorrectPathInLessEnqueuesThanDijkstra() {
    final AStar aStar = new AStar(pathFinderTest.createGeometricalGraph());
    final Dijkstra<LabeledPoint2d> dijkstra = new Dijkstra<>(pathFinderTest.createGeometricalGraph());
    pathFinderTest.assertShortestPathInGeoGraph(aStar);
    pathFinderTest.assertShortestPathInGeoGraph(dijkstra);
    Assert.assertThat(aStar.getNumberOfIterations(), Matchers.lessThan(dijkstra.getNumberOfIterations()));
  }
}
