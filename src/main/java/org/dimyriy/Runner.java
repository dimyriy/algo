package org.dimyriy;

import org.dimyriy.algorithms.sort.HeapSort;
import org.dimyriy.algorithms.sort.InsertionSort;
import org.dimyriy.algorithms.sort.MergeSort;
import org.dimyriy.algorithms.sort.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
@SuppressWarnings("unused")
public class Runner {
  private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
  private static final int PROBLEM_SIZE = 1000000;
  private static final Random RANDOM = new Random();
  private static final InsertionSort<Integer> INSERTION_SORT = new InsertionSort<>();
  private static final HeapSort<Integer> HEAP_SORT = new HeapSort<>();
  private static final MergeSort<Integer> MERGE_SORT = new MergeSort<>();

  public static void main(final String[] args) {
    final Integer[] c = new Integer[PROBLEM_SIZE];
    for (int i = 0; i < PROBLEM_SIZE; i++) {
      c[i] = RANDOM.nextInt(10000);
    }
    final Sort<Integer> sort = HEAP_SORT;
    LOGGER.info("Start");
    sort.sort(c);
    LOGGER.info("End");
    LOGGER.info("Sorted list? {}.", sort.isSortedAsc(c));
    //noinspection ConstantConditions
    if (PROBLEM_SIZE < 100) {
      LOGGER.info("Result: {}.", String.join(",", Arrays.stream(c).map(Object::toString).collect(Collectors.toList())));
    }
    LOGGER.info("Done");
  }
}
