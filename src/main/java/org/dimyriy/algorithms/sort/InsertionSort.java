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
    for (int i = 1; i < arr.length; i++) {
      for (int j = i; j > 0 && arr[j - 1].compareTo(arr[j]) > 0; j--) {
        ArrayUtil.swap(arr, j, j - 1);
      }
    }
  }
}
