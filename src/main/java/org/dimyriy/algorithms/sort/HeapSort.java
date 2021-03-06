package org.dimyriy.algorithms.sort;

import org.dimyriy.datastructures.heap.Heap;
import org.dimyriy.util.CollectionUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
public class HeapSort<T extends Comparable<T>> extends AbstractSort<T> {
  @Override
  void sortImpl(@Nonnull final T[] arr) {
    int size = arr.length;
    Heap.buildMaxHeap(arr, size);
    for (int i = size - 1; i > 0; i--) {
      CollectionUtil.swap(arr, 0, i);
      size--;
      Heap.maxHeapify(arr, size, 0);
    }
  }
}
