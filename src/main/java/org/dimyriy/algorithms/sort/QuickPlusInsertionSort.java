package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@SuppressWarnings("unused")
public class QuickPlusInsertionSort<T extends Comparable<T>> extends QuickSort<T> {
  private static final int MAX_ARRAY_SIZE_FOR_QUICKSORT = 32;
  private final InsertionSort<T> insertionSort = new InsertionSort<>();

  @Override
  void sortImpl(@Nonnull final T[] arr) {
    quickSort(arr, 0, arr.length - 1);
  }

  private void quickSort(@Nonnull final T[] arr, final int lo, final int hi) {
    if (lo < hi) {
      if (hi - lo < MAX_ARRAY_SIZE_FOR_QUICKSORT) {
        insertionSort.sortImpl(arr, lo - 1, hi + 1);
      } else {
        final int partition = partition(arr, lo, hi);
        quickSort(arr, lo, partition);
        quickSort(arr, partition + 1, hi);
      }
    }
  }
}
