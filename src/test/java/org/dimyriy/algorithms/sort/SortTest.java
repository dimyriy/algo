package org.dimyriy.algorithms.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
class SortTest {

  private static List<Class<? extends Sort>> allSortingAlgorithms;
  private Integer[] sortedArray;
  private Integer[] unSortedArray;

  @SuppressWarnings("unchecked")
  private static Sort<Integer> newInstanceSuppressed(@Nonnull final Class<? extends Sort> type) {
    try {
      return (Sort<Integer>) type.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static Stream<Sort<Integer>> allSortingAlgorithms() {
    return allSortingAlgorithms.stream().map(SortTest::newInstanceSuppressed);
  }

  @BeforeAll
  static void setUpClass() {
    final Reflections reflections = new Reflections();
    final Set<Class<? extends Sort>> subTypesOf = reflections.getSubTypesOf(Sort.class);
    allSortingAlgorithms = subTypesOf.stream()
                                     .filter(t -> !t.isInterface() && !Modifier.isAbstract(t.getModifiers()))
                                     .collect(Collectors.toList());
  }

  @BeforeEach
  void setUp() {
    sortedArray = new Integer[]{1, 2, 3, 3, 4, 5, 6, 10, 150, Integer.MAX_VALUE};
    unSortedArray = new Integer[]{1, 2, 3, 4, 5, 6, 3, 149, 150, Integer.MAX_VALUE};
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsTrueForSortedArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertTrue(algorithm.isSorted(sortedArray),
                          algoName(algorithm) + ".isSorted returns true for sorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsFalseForUnsortedArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertFalse(algorithm.isSorted(unSortedArray),
                           algoName(algorithm) + ".isSorted returns false for unsorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    algorithm.sort(unSortedArray);
    Assertions.assertTrue(algorithm.isSorted(unSortedArray),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  private String algoName(@Nonnull final Sort<Integer> algorithm) {
    return algorithm.getClass().getSimpleName();
  }
}