package org.dimyriy.algorithms.generic;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class IntegerReversal {

  int inverse(int number) {
    if (number == 0) {
      return 0;
    }
    final boolean isNegative = number < 0;
    if (isNegative) {
      number = -number;
    }
    final int nDigits = nDigits(number);
    if (nDigits == 1) {
      return number;
    }
    int result = 0;
    for (int i = nDigits - 1; i >= 0; i--) {
      final int divisor = powerOf10(i);
      final int ithDigit = getIthDigit(number, divisor);
      number -= ithDigit * divisor;
      result += ithDigit * powerOf10(nDigits - i - 1);
    }
    return !isNegative ? result : -result;
  }

  int nDigits(int number) {
    if (number < 0) {
      number = -number;
    }
    if (number == 0) {
      return 1;
    }
    return (int) Math.floor(Math.log10(number)) + 1;
  }

  private int getIthDigit(final int number, final int i) {
    final int divisor = powerOf10(i - 1);
    return (number - number % divisor) / divisor;
  }

  private int powerOf10(final int exp) {
    return (int) Math.pow(10, exp);
  }
}
