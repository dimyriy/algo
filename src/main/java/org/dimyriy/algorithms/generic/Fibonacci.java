package org.dimyriy.algorithms.generic;

import java.util.ArrayList;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@SuppressWarnings("SameParameterValue")
class Fibonacci {
  private final ArrayList<Integer> cache = new ArrayList<>(4);

  Fibonacci() {
    cache.add(0);
    cache.add(1);
  }

  int fRecursive(final int n) {
    if (n < 2) {
      return n;
    }
    return fRecursive(n - 1) + fRecursive(n - 2);
  }

  int fIterative(final int n) {
    if (n < 2) {
      return n;
    }
    int current = 1;
    int previous = 0;
    for (int i = 1; i < n; i++) {
      final int temp = current + previous;
      previous = current;
      current = temp;
    }
    return current;
  }

  int fMemoizationBased(final int n) {
    if (cache.size() > n) {
      return cache.get(n);
    } else {
      final int nth = fMemoizationBased(n - 1) + fMemoizationBased(n - 2);
      cache.add(n, nth);
      return nth;
    }
  }
}
