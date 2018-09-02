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

  public static long nextPrime(long number) {
    while (true) {
      boolean isPrime = true;
      number++;
      final int root = (int) Math.sqrt(number);
      for (int i = 2; i <= root; i++) {
        if (number % i == 0) {
          isPrime = false;
          break;
        }
      }
      if (isPrime)
        return number;
    }
  }

  public static int log2(long number) {
    int log2 = 0;
    while ((number >>= 1) > 0) {
      log2++;
    }
    return log2;
  }
}
