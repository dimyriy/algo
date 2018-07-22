package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T> {
  @Override
  public void sort(@Nullable final T[] arr) {
    if (arr != null && arr.length > 0) {
      sortImpl(arr);
    }
  }

  @Override
  public boolean isSorted(@Nonnull final T[] arr) {
    if (arr.length < 2) {
      return true;
    } else {
      for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i].compareTo(arr[i + 1]) >= 0) {
          return false;
        }
      }
      return true;
    }
  }

  void swap(@Nonnull final T[] arr, final int i, final int j) {
    final T t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  abstract void sortImpl(@Nonnull final T[] arr);
}
