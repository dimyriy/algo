package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSort<T> {

  @Override
  void sortImpl(@Nonnull final T[] arr) {
    final T[] ts = sortRecursively(arr);
    System.arraycopy(ts, 0, arr, 0, ts.length);
  }

  @SuppressWarnings("unchecked")
  private T[] sortRecursively(@Nonnull final T[] arr) {
    if (arr.length <= 1) {
      return arr;
    }
    final int median = findMedian(arr);
    final T[] left = (T[]) new Comparable[median];
    final T[] right = (T[]) new Comparable[arr.length - median];
    System.arraycopy(arr, 0, left, 0, median);
    System.arraycopy(arr, median, right, 0, arr.length - median);
    return merge(sortRecursively(left), sortRecursively(right));
  }

  private int findMedian(@Nonnull final T[] arr) {
    return arr.length / 2;
  }

  @SuppressWarnings("unchecked")
  private T[] merge(@Nonnull final T[] left, @Nonnull final T[] right) {
    final int resultLength = left.length + right.length;
    final Comparable[] result = new Comparable[resultLength];
    int currentLeftIndex = 0;
    int currentRightIndex = 0;
    while (currentLeftIndex < left.length && currentRightIndex < right.length) {
      if (left[currentLeftIndex].compareTo(right[currentRightIndex]) <= 0) {
        result[currentLeftIndex + currentRightIndex] = left[currentLeftIndex];
        currentLeftIndex++;
      } else {
        result[currentLeftIndex + currentRightIndex] = right[currentRightIndex];
        currentRightIndex++;
      }
    }
    for (; currentLeftIndex < left.length; currentLeftIndex++) {
      result[currentLeftIndex + currentRightIndex] = left[currentLeftIndex];
    }
    for (; currentRightIndex < right.length; currentRightIndex++) {
      result[currentLeftIndex + currentRightIndex] = right[currentRightIndex];
    }
    return (T[]) result;
  }
}
