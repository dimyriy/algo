package org.dimyriy.util;

/**
 * @author Dmitrii Bogdanov
 * Created at 09.08.18
 */
public class NumberUtil {
  public static boolean isPowerOfTwo(final int number) {
    return number != 0 && (number & (number - 1)) == 0;
  }
}
