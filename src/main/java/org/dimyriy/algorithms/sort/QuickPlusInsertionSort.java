package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@SuppressWarnings("unused")
public class QuickPlusInsertionSort<T extends Comparable<T>> extends QuickSort<T> {
  private final InsertionSort<T> insertionSort = new InsertionSort<>();

  @Override
  public boolean isFast() {
    return true;
  }

  @Override
  void sortImpl(@Nonnull final T[] arr) {
    quickSortWithInsertionForSmallArrays(arr, 0, arr.length - 1);
  }

  private void quickSortWithInsertionForSmallArrays(@Nonnull final T[] arr, final int lo, final int hi) {
    if (lo < hi) {
      if (hi - lo < MAX_ARRAY_SIZE_FOR_INSERTION_SORT) {
        insertionSort.sortImpl(arr, lo - 1, hi + 1);
      } else {
        final int partition = partition(arr, lo, hi);
        quickSortWithInsertionForSmallArrays(arr, lo, partition);
        quickSortWithInsertionForSmallArrays(arr, partition + 1, hi);
      }
    }
  }
}
