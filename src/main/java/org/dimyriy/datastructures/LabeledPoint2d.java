package org.dimyriy.datastructures;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Dmitrii Bogdanov
 * Created at 08.08.18
 */
public class LabeledPoint2d implements Comparable<LabeledPoint2d> {
  private final double x;
  private final double y;
  private final String label;

  public LabeledPoint2d(final double x, final double y, final String label) {
    this.x = x;
    this.y = y;
    this.label = label;
  }

  @Override
  public int compareTo(@Nonnull final LabeledPoint2d o) {
    return Double.compare(x, o.x);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, label);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final LabeledPoint2d that = (LabeledPoint2d) o;
    return Double.compare(that.x, x) == 0 &&
        Double.compare(that.y, y) == 0 &&
        Objects.equals(label, that.label);
  }

  @Override
  public String toString() {
    return label;
  }

  public double distance(final LabeledPoint2d labeledPoint2D) {
    final double xDistance = this.x - labeledPoint2D.x;
    final double yDistance = this.y - labeledPoint2D.y;
    return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
  }
}
