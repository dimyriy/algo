package org.dimyriy.algorithms.geometry;

import org.dimyriy.datastructures.Point2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 08.08.18
 */
class ConvexHullTest {

  @Test
  void nextClockwisePointLeft() {
  }

  @Test
  void nextCounterClockwisePointLeft() {
    final ConvexHull convexHull = new ConvexHull();
    final List<Point2d> points = Arrays.asList(new Point2d(1, 2), new Point2d(2, 3), new Point2d(3, 2), new Point2d(2, 6));
    final int nextClockwisePoint = convexHull.nextCounterClockwisePointLeft(points);
    Assertions.assertEquals(2, nextClockwisePoint);
  }
}