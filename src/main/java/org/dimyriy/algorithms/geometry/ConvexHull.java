package org.dimyriy.algorithms.geometry;

import org.dimyriy.datastructures.Point2d;
import org.dimyriy.util.Tuple;

import java.util.Collections;
import java.util.List;

import static org.dimyriy.util.CollectionUtil.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 08.08.18
 */
public class ConvexHull {
  public static final int LARGEST_INDEPENDENTLY_CORRECT_CONVEX_HULL_SIZE = 3;

  public List<Point2d> findConvexHull(final List<Point2d> points) {
    if (points.size() < LARGEST_INDEPENDENTLY_CORRECT_CONVEX_HULL_SIZE) {
      return points;
    } else {
      Collections.sort(points);
      return findConvexHullRecursively(points);
    }
  }

  public List<Point2d> findConvexHullRecursively(final List<Point2d> points) {
    if (points.size() < LARGEST_INDEPENDENTLY_CORRECT_CONVEX_HULL_SIZE) {
      return points;
    }
    final Tuple<List<Point2d>, List<Point2d>> divide = divide(points);
    final List<Point2d> left = findConvexHullRecursively(divide.getI1());
    final List<Point2d> right = findConvexHullRecursively(divide.getI2());
    return merge(left, right);
  }

  public Tuple<List<Point2d>, List<Point2d>> divide(final List<Point2d> points) {
    return new Tuple<>(points.subList(0, middle(points)), points.subList(middle(points) + 1, lastIndex(points)));
  }

  public List<Point2d> merge(final List<Point2d> left, final List<Point2d> right) {
    int i = 1, j = 1;
    while(left.get(i).y > )
    return null;
  }

  public int nextClockwisePointInLeft(final List<Point2d> hull, final int i) {
    final double iY = hull.get(i).y;
    double yMin = Double.MAX_VALUE;
    int currentPointIndex = i;
    while (yMin >= iY) {
      currentPointIndex = decrementIndexCyclic(hull, currentPointIndex);
      yMin = hull.get(currentPointIndex).y;
    }
    return currentPointIndex;
  }

  public int nextCounterClockwisePointLeft(final List<Point2d> hull) {
    for (final Point2d point2d : hull) {

    }
    return 0;
  }
}
