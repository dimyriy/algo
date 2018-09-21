package org.dimyriy.util;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 09.08.18
 */
public class NumberUtil {
  private static final Random RANDOM = new Random();

  private NumberUtil() {
  }

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
      final long root = (long) Math.ceil(Math.sqrt(number));
      for (long i = 2; i <= root; i++) {
        if (number % i == 0) {
          isPrime = false;
          break;
        }
      }
      if (isPrime)
        return number;
    }
  }

  public static int nextRandomPrime(final int limit) {
    return (int) NumberUtil.nextPrime(Math.abs((int) (nextRandomPrime() % limit)));
  }

  private static long nextRandomPrime() {
    return NumberUtil.nextPrime((long) RANDOM.nextInt() * RANDOM.nextInt());
  }
}
