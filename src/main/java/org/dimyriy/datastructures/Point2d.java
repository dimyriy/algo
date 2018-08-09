package org.dimyriy.datastructures;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 08.08.18
 */
public class Point2d implements Comparable<Point2d> {
  public final double x;
  public final double y;

  public Point2d(final double x, final double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int compareTo(@Nonnull final Point2d o) {
    return Double.compare(x, o.x);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Point2d)) return false;

    final Point2d point2d = (Point2d) o;

    if (Double.compare(point2d.x, x) != 0) return false;
    return Double.compare(point2d.y, y) == 0;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(x);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
