package org.dimyriy.datastructures.heap;

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
  private Integer[] maxHeapEvenLengthArray;
  private Integer[] maxHeapOddLengthArray;
  private Integer[] nonMaxHeapEvenLengthArray;
  private Integer[] nonMaxHeapOddLengthArray;
  private Integer[] maxHeapTriadArray;
  private Integer[] nonMaxHeapTriadArray;
  private Integer[] randomArray;

  @BeforeEach
  void setUp() {
    maxHeapEvenLengthArray = new Integer[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1, 0};
    maxHeapOddLengthArray = new Integer[]{16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
    nonMaxHeapEvenLengthArray = new Integer[]{16, 14, 10, 19, 8, 7, 9, 3, 2, 4, 1};
    nonMaxHeapOddLengthArray = new Integer[]{16, 14, 10, 19, 8, 7, 9, 3, 2, 4, 1, 14};
    maxHeapTriadArray = new Integer[]{3, 2, 1};
    nonMaxHeapTriadArray = new Integer[]{2, 3, 1};
    randomArray = new Integer[100];
    for (int i = 0; i < randomArray.length; i++) {
      randomArray[i] = RANDOM.nextInt(50);
    }
  }

  @Test
  void testIsMaxHeapReturnsTrueForZeroElementArray() {
    final Integer[] a = {};
    Assertions.assertTrue(Heap.isMaxHeap(a, a.length));
  }

  @Test
  void testIsMaxHeapReturnsTrueForOneElementArray() {
    final Integer[] a = {1};
    Assertions.assertTrue(Heap.isMaxHeap(a, a.length));
  }

  @Test
  void testIsMaxHeapReturnsTrueForMaxHeapArray() {
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapEvenLengthArray, maxHeapEvenLengthArray.length));
  }

  @Test
  void testIsMaxHeapReturnsFalseForArrayWithNonMaxFirstElement() {
    final Integer[] a = {11, 14, 10, 8, 7, 9, 3, 2, 4, 1};
    Assertions.assertFalse(Heap.isMaxHeap(a, a.length));
  }

  @Test
  void testIsMaxHeapReturnsFalseForNonMaxHeapArray() {
    Assertions.assertFalse(Heap.isMaxHeap(nonMaxHeapEvenLengthArray, nonMaxHeapEvenLengthArray.length));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueMaxHeapForMaxHeapTriadArray() {
    final Integer[] a = {3, 2, 1};
    Assertions.assertTrue(Heap.isMaxHeapAt(a, 0, a.length));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueForLeftLeave() {
    Assertions.assertTrue(Heap.isMaxHeapAt(maxHeapTriadArray, 1, maxHeapTriadArray.length));
  }

  @Test
  void testIsMaxHeapAtReturnsTrueForRightLeave() {
    Assertions.assertTrue(Heap.isMaxHeapAt(maxHeapTriadArray, 2, maxHeapTriadArray.length));
  }

  @Test
  void testIsMaxHeapAtReturnsFalseMaxHeapForMaxHeapTriadArray() {
    Assertions.assertFalse(Heap.isMaxHeapAt(nonMaxHeapTriadArray, 0, nonMaxHeapTriadArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromMaxHeapArrayOfEventLength() {
    Heap.buildMaxHeap(maxHeapEvenLengthArray, maxHeapEvenLengthArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapEvenLengthArray, maxHeapEvenLengthArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromNonMaxHeapArrayOfEvenLength() {
    Heap.buildMaxHeap(nonMaxHeapEvenLengthArray, nonMaxHeapEvenLengthArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(nonMaxHeapEvenLengthArray, nonMaxHeapEvenLengthArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromMaxHeapArrayOfOddLength() {
    Heap.buildMaxHeap(maxHeapOddLengthArray, maxHeapOddLengthArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapOddLengthArray, maxHeapOddLengthArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromNonMaxHeapArrayOfOddLength() {
    Heap.buildMaxHeap(nonMaxHeapOddLengthArray, nonMaxHeapOddLengthArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(nonMaxHeapOddLengthArray, nonMaxHeapOddLengthArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromMaxHeapTriadArray() {
    Heap.buildMaxHeap(maxHeapTriadArray, maxHeapTriadArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(maxHeapTriadArray, maxHeapTriadArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromNonMaxHeapTriadArray() {
    Heap.buildMaxHeap(nonMaxHeapTriadArray, nonMaxHeapTriadArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(nonMaxHeapTriadArray, nonMaxHeapTriadArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromRandomArray() {
    Heap.buildMaxHeap(randomArray, randomArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(randomArray, randomArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromOneElementArray() {
    final Integer[] oneElementArray = new Integer[]{100};
    Heap.buildMaxHeap(oneElementArray, oneElementArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(oneElementArray, oneElementArray.length));
  }

  @Test
  void testBuildMaxHeapCreatesMaxHeapFromZeroElementArray() {
    final Integer[] zeroElementArray = {};
    Heap.buildMaxHeap(zeroElementArray, zeroElementArray.length);
    Assertions.assertTrue(Heap.isMaxHeap(zeroElementArray, zeroElementArray.length));
  }
}
