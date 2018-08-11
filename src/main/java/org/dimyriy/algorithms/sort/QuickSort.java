package org.dimyriy.algorithms.sort;

import org.dimyriy.util.CollectionUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 28.07.18
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {
  @Override
  void sortImpl(@Nonnull final T[] arr) {
    quickSort(arr, 0, arr.length - 1);
  }

  int partition(@Nonnull final T[] arr, final int lo, final int hi) {
    final T pivot = arr[lo];
    int i = lo - 1;
    int j = hi + 1;
    while (true) {
      do {
        i++;
      } while (arr[i].compareTo(pivot) < 0);
      do {
        j--;
      } while (arr[j].compareTo(pivot) > 0);
      if (i >= j) {
        return j;
      }
      CollectionUtil.swap(arr, i, j);
    }
  }

  private void quickSort(@Nonnull final T[] arr, final int lo, final int hi) {
    if (lo < hi) {
      final int partition = partition(arr, lo, hi);
      quickSort(arr, lo, partition);
      quickSort(arr, partition + 1, hi);
    }
  }
}
