package org.dimyriy.algorithms.sort;

import org.junit.Assert;
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

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
class SortTest {
  private static final Random RANDOM = new Random();
  private static final int RELATIVELY_LARGE_ARRAY_SIZE = 10000;
  private static List<Class<? extends Sort>> allSortingAlgorithms;
  private Integer[] sortedArrayWithDuplicates;
  private Integer[] unsortedArrayWithDuplicates;
  private Integer[] sortedArrayWithoutDuplicates;
  private Integer[] unsortedArrayWithoutDuplicates;

  @BeforeEach
  void setUp() {
    sortedArrayWithDuplicates = new Integer[]{1, 2, 3, 3, 4, 5, 6, 10, 150, Integer.MAX_VALUE};
    sortedArrayWithoutDuplicates = new Integer[]{1, 2, 3, 4, 5, 6, 10, 8, 9};
    unsortedArrayWithDuplicates = new Integer[]{1, 2, 3, 4, 5, 6, 3, 149, 150, Integer.MAX_VALUE};
    unsortedArrayWithoutDuplicates = new Integer[]{1, 9, 6, 2, 4, 12, 8, 7, 14};
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testIsSortedReturnsTrueForSortedArray(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertTrue(algorithm.isSortedAsc(sortedArrayWithDuplicates),
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
    Assertions.assertFalse(algorithm.isSortedAsc(unsortedArrayWithDuplicates),
                           algoName(algorithm) + ".isSortedAsc returns false for unsorted array");
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesUnsortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    sortAndAssertSorted(algorithm, algorithm.isDuplicatesAllowed() ? unsortedArrayWithDuplicates : unsortedArrayWithoutDuplicates);
  }

  @ParameterizedTest
  @MethodSource("fastSortingAlgorithms")
  void testSortLeavesRelativelyLargeRandomlyGeneratedUnsortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    sortAndAssertSorted(algorithm, algorithm.isDuplicatesAllowed() ? createRandomArrayOfSizeWithElementsSmallerOrEqualTo(RELATIVELY_LARGE_ARRAY_SIZE, 10000) :
                                   createRandomDistinctArrayOfSizeWithElementsSmallerOrEqualTo(RELATIVELY_LARGE_ARRAY_SIZE, RELATIVELY_LARGE_ARRAY_SIZE));
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesSortedArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    sortAndAssertSorted(algorithm, algorithm.isDuplicatesAllowed() ? sortedArrayWithDuplicates : sortedArrayWithoutDuplicates);
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesOneElementArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    sortAndAssertSorted(algorithm, new Integer[]{1});
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortLeavesEmptyArrayInSortedState(@Nonnull final Sort<Integer> algorithm) {
    sortAndAssertSorted(algorithm, new Integer[]{});
  }

  @ParameterizedTest
  @MethodSource("allSortingAlgorithms")
  void testSortThrowsNPEForNullArg(@Nonnull final Sort<Integer> algorithm) {
    Assertions.assertThrows(NullPointerException.class,
                            () -> algorithm.sort(null),
                            algoName(algorithm) + ".sort(null) throws NPE");
  }

  private void sortAndAssertSorted(@Nonnull final Sort<Integer> algorithm, final Integer[] problem) {
    final Integer[] initialArray = problem.clone();
    algorithm.sort(problem);
    Assert.assertThat(problem, arrayContainingInAnyOrder(initialArray));
    Assertions.assertTrue(algorithm.isSortedAsc(problem),
                          algoName(algorithm) + ".sort(arr) leaves an array in sorted state");
  }

  @SuppressWarnings("SameParameterValue")
  private Integer[] createRandomArrayOfSizeWithElementsSmallerOrEqualTo(final int size, final int bound) {
    return RANDOM.ints(1, bound).limit(size).boxed().toArray(Integer[]::new);
  }

  @SuppressWarnings("SameParameterValue")
  private Integer[] createRandomDistinctArrayOfSizeWithElementsSmallerOrEqualTo(final int size, final int bound) {
    return RANDOM.ints(1, bound).limit(size * 2).distinct().limit(size).boxed().toArray(Integer[]::new);
  }

  private String algoName(@Nonnull final Sort<Integer> algorithm) {
    return algorithm.getClass().getSimpleName();
  }

  @BeforeAll
  static void setUpClass() {
    final Reflections reflections = new Reflections("org.dimyriy.algorithms");
    final Set<Class<? extends Sort>> subTypesOf = reflections.getSubTypesOf(Sort.class);
    allSortingAlgorithms = subTypesOf.stream()
                                     .filter(t -> !t.isInterface() && !Modifier.isAbstract(t.getModifiers()))
                                     .collect(Collectors.toList());
  }

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
}
