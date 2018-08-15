package org.dimyriy.util;

/**
 * @author Dmitrii Bogdanov
 * Created at 09.08.18
 */
public class NumberUtil {
  public static boolean isPowerOfTwo(final int number) {
    return number != 0 && (number & (number - 1)) == 0;
  }

  public static int nextPowerOfTwo(final int number) {
    if (number == Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Number is too large");
    }
    return 1 << (int) Math.ceil(Math.log10(number) * Math.log(10));
  }
}
