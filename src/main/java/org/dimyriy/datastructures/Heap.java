package org.dimyriy.datastructures;

import org.dimyriy.util.CollectionUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
public class Heap {
  public static <T extends Comparable<T>> boolean isMaxHeap(@Nonnull final T[] arr, final int size) {
    for (int i = 0; i < size; i++) {
      if (!isMaxHeapAt(arr, i, size)) {
        return false;
      }
    }
    return true;
  }

  public static <T extends Comparable<T>> void buildMaxHeap(@Nonnull final T[] arr, final int size) {
    for (int i = size / 2; i > -1; i--) {
      maxHeapify(arr, size, i);
    }
  }

  public static <T extends Comparable<T>> void maxHeapify(
      @Nonnull final T[] arr, final int size, final int i) {
    guard(i, size);
    int largestElement = i;
    final int left = left(i);
    if (left < size && arr[left].compareTo(arr[i]) > 0) {
      largestElement = left;
    }
    final int right = right(i);
    if (right < size && arr[right].compareTo(arr[largestElement]) > 0) {
      largestElement = right;
    }
    if (largestElement != i) {
      CollectionUtil.swap(arr, i, largestElement);
      maxHeapify(arr, size, largestElement);
    }
  }

  public static <T extends Comparable<T>> boolean isMaxHeapAt(final T[] arr, final int i, final int size) {
    guard(i, size);
    if (isLeave(i, size)) {
      return true;
    } else {
      final int left = left(i);
      final int right = right(i);
      if (right >= size) {
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

  private static boolean isLeave(final int i, final int size) {
    guard(i, size);
    return right(i) > size;
  }

  private static void guard(final int i, final int size) {
    if (i != 0 && i >= size) {
      throw new HeapIndexOutOfBoundsException(i);
    }
  }

  private static class HeapIndexOutOfBoundsException extends IndexOutOfBoundsException {
    HeapIndexOutOfBoundsException(final int i) {
      super("Checking for element outside of array: " + i);
    }
  }
}
