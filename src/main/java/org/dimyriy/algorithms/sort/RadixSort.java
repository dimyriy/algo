package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 28.07.18
 */
@SuppressWarnings("SameParameterValue")
public class RadixSort<T extends Comparable<T>> extends AbstractSort<T> {
  @Override
  void sortImpl(@Nonnull final T[] arr) {
    if (!(arr instanceof Integer[])) {
      throw new ClassCastException("Only applicable to integers");
    }
    sort((Integer[]) arr, 4);

  }

  private void sort(@Nonnull final Integer[] arr, final int base2Exponent) {
    final int[] temp = new int[arr.length];
    final int[] buckets = new int[1 << base2Exponent];
    final int[] pref = new int[1 << base2Exponent];
    final int nGroups = (int) Math.ceil(32 / (1. + base2Exponent));
    final int radix = (1 << base2Exponent) - 1;
    for (int c = 0, exp = 0; c < nGroups; c++, exp += base2Exponent) {
      for (int j = 0; j < buckets.length; j++) {
        buckets[j] = 0;
      }
      for (final int el : arr) {
        final int bucketIndex = bucketIndex(el, exp, radix);
        buckets[bucketIndex]++;
      }
      pref[0] = 0;
      for (int i = 1; i < buckets.length; i++) {
        pref[i] = pref[i - 1] + buckets[i - 1];
      }

      for (final int el : arr) {
        temp[pref[bucketIndex(el, exp, radix)]++] = el;
      }

      for (int i = 0; i < arr.length; i++) {
        arr[i] = temp[i];
      }
    }
  }

  private int bucketIndex(final int el, final int exp, final int radix) {
    return (el >> exp) & radix;
  }
}
