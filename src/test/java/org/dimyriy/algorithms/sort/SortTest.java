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
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
class SortTest {
  private static final Random RANDOM = new Random();
  private static final int RELATIVELY_LARGE_ARRAY_SIZE = 1000000;
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

  private static Stream<Sort<Integer>> fastSortingAlgorithms() {
    return allSortingAlgorithms.stream().map(SortTest::newInstanceSuppressed).filter(Sort::isFast);
  }

  @BeforeAll
  static void setUpClass() {
    final Reflections reflections = new Reflections("org.dimyriy.algorithms");
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
    Assertions.assertTrue(algorithm.isSortedAsc(sortedArray),
                          algoName(algorithm) + ".isSortedAsc returns true for sorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsTrueForEmptyArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertTrue(algorithm.isSortedAsc(new Integer[]{}),
                          algoName(algorithm) + ".isSortedAsc returns true for empty array");
  }

  @SuppressWarnings("ConstantConditions")
  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedThrowsNPEForNullArg(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertThrows(NullPointerException.class, () -> algorithm.isSortedAsc(null),
                            algoName(algorithm) + ".isSortedAsc throws NPE for null argument");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsTrueForOneElementArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertTrue(algorithm.isSortedAsc(new Integer[]{1}),
                          algoName(algorithm) + ".isSortedAsc returns true for sorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsFalseForUnsortedArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertFalse(algorithm.isSortedAsc(unSortedArray),
                           algoName(algorithm) + ".isSortedAsc returns false for unsorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesUnsortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    algorithm.sort(unSortedArray);
    Assertions.assertTrue(algorithm.isSortedAsc(unSortedArray),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @ParameterizedTest
  @MethodSource("fastSortingAlgorithms")
  void testSortLeavesRelativelyLargeRandomlyGeneratedUnsortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    final Integer[] relativelyLargeRandomArray = new Integer[RELATIVELY_LARGE_ARRAY_SIZE];
    for (int i = 0; i < relativelyLargeRandomArray.length; i++) {
      relativelyLargeRandomArray[i] = RANDOM.nextInt(10000);
    }
    algorithm.sort(relativelyLargeRandomArray);
    Assertions.assertTrue(algorithm.isSortedAsc(relativelyLargeRandomArray),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesSortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    algorithm.sort(sortedArray);
    Assertions.assertTrue(algorithm.isSortedAsc(sortedArray),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesOneElementArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    final Integer[] arr = {1};
    algorithm.sort(arr);
    Assertions.assertTrue(algorithm.isSortedAsc(arr),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesEmptyArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    final Integer[] arr = {};
    algorithm.sort(arr);
    Assertions.assertTrue(algorithm.isSortedAsc(arr),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortThrowsNPEForNullArg(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertThrows(NullPointerException.class,
                            () -> algorithm.sort(null),
                            algoName(algorithm) + ".sort(null) throws NPE");
  }

  private String algoName(@Nonnull final Sort<Integer> algorithm) {
    return algorithm.getClass().getSimpleName();
  }
}
