package org.dimyriy.util;

/**
 * @author Dmitrii Bogdanov
 * Created at 08.08.18
 */
public class Tuple<I1, I2> {
  private final I1 i1;
  private final I2 i2;

  public Tuple(final I1 i1, final I2 i2) {
    this.i1 = i1;
    this.i2 = i2;
  }

  public I1 getI1() {
    return i1;
  }

  public I2 getI2() {
    return i2;
  }
}
