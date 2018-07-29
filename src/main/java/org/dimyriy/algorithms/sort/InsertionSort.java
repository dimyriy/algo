package org.dimyriy.algorithms.sort;

import org.dimyriy.util.ArrayUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> {
  @Override
  void sortImpl(@Nonnull final T[] arr) {
    sortImpl(arr, 0, arr.length);
  }

  void sortImpl(@Nonnull final T[] arr, final int start, final int end) {
    for (int i = start + 1; i < end; i++) {
      for (int j = i; j > 0 && arr[j - 1].compareTo(arr[j]) > 0; j--) {
        ArrayUtil.swap(arr, j, j - 1);
      }
    }
  }

  @Override
  public boolean isFast() {
    return false;
  }
}
