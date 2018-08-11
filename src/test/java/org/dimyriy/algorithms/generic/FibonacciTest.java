package org.dimyriy.algorithms.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class FibonacciTest {
  @Test
  void recursiveMethodReturnsCorrectAnswer() {
    Assertions.assertEquals(10946, new Fibonacci().fRecursive(21));
  }

  @Test
  void iterativeMethodReturnsCorrectAnswer() {
    Assertions.assertEquals(10946, new Fibonacci().fIterative(21));
  }

  @Test
  void memoizationBasedMethodReturnsCorrectAnswer() {
    Assertions.assertEquals(10946, new Fibonacci().fMemoizationBased(21));
  }

  @Test
  void iterativeMethodReturnsCorrectFirstFibonacciNumber() {
    Assertions.assertEquals(1, new Fibonacci().fIterative(1));
  }

  @Test
  void iterativeMethodReturnsCorrectZerothFibonacciNumber() {
    Assertions.assertEquals(0, new Fibonacci().fIterative(0));
  }

  @Test
  void recursiveMethodReturnsCorrectFirstFibonacciNumber() {
    Assertions.assertEquals(1, new Fibonacci().fRecursive(1));
  }

  @Test
  void recursiveMethodReturnsCorrectZerothFibonacciNumber() {
    Assertions.assertEquals(0, new Fibonacci().fRecursive(0));
  }

  @Test
  void memoizationBasedMethodReturnsCorrectFirstFibonacciNumber() {
    Assertions.assertEquals(1, new Fibonacci().fMemoizationBased(1));
  }

  @Test
  void memoizationBasedMethodReturnsCorrectZerothFibonacciNumber() {
    Assertions.assertEquals(0, new Fibonacci().fMemoizationBased(0));
  }
}