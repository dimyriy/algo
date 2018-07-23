package org.dimyriy.datastructures;

import org.dimyriy.util.ArrayUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
public class Heap {
  public static <T extends Comparable<T>> boolean isMaxHeap(@Nonnull final T[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (!isMaxHeapAt(arr, i)) {
        return false;
      }
    }
    return true;
  }

  public static <T extends Comparable<T>> void maxHeapify(@Nonnull final T[] arr) {
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      maxHeapify(arr, i);
    }
  }

  private static <T extends Comparable<T>> void maxHeapify(@Nonnull final T[] arr, final int i) {
    guard(arr, i);
    int largestElement;
    final int left = left(i);
    if (left < arr.length && arr[left].compareTo(arr[i]) > 0) {
      largestElement = left;
    } else {
      largestElement = i;
    }
    final int right = right(i);
    if (right < arr.length && arr[right].compareTo(arr[largestElement]) > 0) {
      largestElement = right;
    }
    if (largestElement != i) {
      ArrayUtil.swap(arr, i, largestElement);
      maxHeapify(arr, largestElement);
    }
  }

  public static <T extends Comparable<T>> boolean isMaxHeapAt(final T[] arr, final int i) {
    guard(arr, i);
    if (isLeave(arr, i)) {
      return true;
    } else {
      final int left = left(i);
      final int right = right(i);
      if (right >= arr.length) {
        return arr[i].compareTo(arr[left]) >= 0;
      } else {
        return arr[i].compareTo(arr[left]) >= 0 && arr[i].compareTo(arr[right]) >= 0;
      }
    }
  }

  private static int left(final int i) {
    return 2 * i + 1;
  }

  private static int right(final int i) {
    return 2 * i + 2;
  }

  private static <T extends Comparable<T>> boolean isLeave(final T[] arr, final int i) {
    guard(arr, i);
    return right(i) > arr.length;
  }

  private static <T extends Comparable<T>> void guard(final T[] arr, final int i) {
    if (i >= arr.length) {
      throw new HeapIndexOutOfBoundsException(i);
    }
  }

  private static class HeapIndexOutOfBoundsException extends IndexOutOfBoundsException {
    HeapIndexOutOfBoundsException(final int i) {
      super("Checking for element outside of array: " + i);
    }
  }
}
