package org.dimyriy.datastructures.heap;

import org.dimyriy.datastructures.Heap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
class HeapTest {
  private static final Random RANDOM = new Random();
  private Integer[] maxHeapArray;
  private Integer[] nonMaxHeapArray;
  private Integer[] maxHeapTriadArray;
  private Integer[] nonMaxHeapTriadArray;
  private Integer[] randomArray;

  @BeforeEach
  void setUp() {
    maxHeapArray = new Integer[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
    nonMaxHeapArray = new Integer[]{16, 14, 10, 19, 8, 7, 9, 3, 2, 4, 1};
    maxHeapTriadArray = new Integer[]{3, 2, 1};
    nonMaxHeapTriadArray = new Integer[]{2, 3, 1};
    randomArray = new Integer[RANDOM.nextInt(10000)];
    for (int i = 0; i < randomArray.length; i++) {
      randomArray[i] = RANDOM.nextInt();
    }
  }

  @Test
  void testIsMaxHeapReturnsTrueForZeroElementArray() {
    Assertions.assertTrue(Heap.isMaxHeap(new Integer[]{}));
  }

  @Test
  void testIsMaxHeapReturnsTrueForOneElementArray() {
    Assertions.assertTrue(Heap.isMaxHeap(new Integer[]{1}));
  }

  @Test
  void testIsMaxHeapReturnsTrueForMaxHeapArray() {
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapArray));
  }

  @Test
  void testIsMaxHeapReturnsFalseForArrayWithNonMaxFirstElement() {
    Assertions.assertFalse(Heap.isMaxHeap(new Integer[]{11, 14, 10, 8, 7, 9, 3, 2, 4, 1}));
  }

  @Test
  void testIsMaxHeapReturnsFalseForNonMaxHeapArray() {
    Assertions.assertFalse(Heap.isMaxHeap(nonMaxHeapArray));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueMaxHeapForMaxHeapTriadArray() {
    Assertions.assertTrue(Heap.isMaxHeapAt(new Integer[]{3, 2, 1}, 0));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueForLeftLeave() {
    Assertions.assertTrue(Heap.isMaxHeapAt(maxHeapTriadArray, 1));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueForRightLeave() {
    Assertions.assertTrue(Heap.isMaxHeapAt(maxHeapTriadArray, 2));
  }

  @Test
  void testIsMaxHeapAtReturnsFalseMaxHeapForMaxHeapTriadArray() {
    Assertions.assertFalse(Heap.isMaxHeapAt(nonMaxHeapTriadArray, 0));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromMaxHeapArray() {
    Heap.maxHeapify(maxHeapArray);
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromNonMaxHeapArray() {
    Heap.maxHeapify(nonMaxHeapArray);
    Assertions.assertTrue(Heap.isMaxHeap(nonMaxHeapArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromMaxHeapTriadArray() {
    Heap.maxHeapify(maxHeapTriadArray);
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapTriadArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromNonMaxHeapTriadArray() {
    Heap.maxHeapify(nonMaxHeapTriadArray);
    Assertions.assertTrue(Heap.isMaxHeap(nonMaxHeapTriadArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromRandomArray() {
    Heap.maxHeapify(randomArray);
    Assertions.assertTrue(Heap.isMaxHeap(randomArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromoneElementArray() {
    final Integer[] oneElementArray = new Integer[]{100};
    Heap.maxHeapify(oneElementArray);
    Assertions.assertTrue(Heap.isMaxHeap(oneElementArray));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromZeroElementArray() {
    final Integer[] zeroElementArray = {};
    Heap.maxHeapify(zeroElementArray);
    Assertions.assertTrue(Heap.isMaxHeap(zeroElementArray));
  }
}
