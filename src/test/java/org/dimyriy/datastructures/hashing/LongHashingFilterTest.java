package org.dimyriy.datastructures.hashing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class LongHashingFilterTest {

  @Test
  void testSmallPositiveProbabilityIsLessThanSixtyPercentForBloomFilter() {
    assertFalsePositiveProbabilityForFilterIsLessThanMaxProbability(createBloomFilter(), 60.);
  }

  @Test
  void testSmallPositiveProbabilityIsLessThanTenPercentForCuckooFilter() {
    assertFalsePositiveProbabilityForFilterIsLessThanMaxProbability(createCuckooFilter(), 10.);
  }

  private void assertFalsePositiveProbabilityForFilterIsLessThanMaxProbability(final Function<long[], LongHashingFilter> filterCreator,
                                                                               final double maxProbability) {
    for (int capacity = 2 << 16; capacity < 2 << 22; capacity = capacity << 2) {
      final long[] input = new Random().longs(capacity).distinct().toArray();
      final LongHashingFilter bf = filterCreator.apply(input);
      putHalfOfArrayValuesIntoFilter(input, bf);
      final int falsePositives = calculateNumberOfFalsePositives(input, bf);
      final int falseNegatives = calculateNumberOfFalseNegatives(input, bf);
      Assertions.assertEquals(0, falseNegatives);
      assertThat("falsePositive probability for capacity " + capacity,
                 calculatePercentageOfFalsePositives(input, falsePositives) * 100,
                 lessThan(maxProbability));
    }
  }

  private Function<long[], LongHashingFilter> createBloomFilter() {
    return (i) -> new LongBloomFilter(halfArray(i));
  }

  private Function<long[], LongHashingFilter> createCuckooFilter() {
    return (i) -> new LongCuckooFilter(halfArray(i));
  }

  private double calculatePercentageOfFalsePositives(final long[] input, final double falsePositives) {
    return ((falsePositives + 0.) / halfArray(input));
  }

  private int calculateNumberOfFalsePositives(final long[] input, final LongHashingFilter filter) {
    int falsePositives = 0;
    for (int i = halfArray(input); i < input.length; i++) {
      falsePositives += filter.contains(input[i]) ? 1 : 0;
    }
    return falsePositives;
  }

  private int calculateNumberOfFalseNegatives(final long[] input, final LongHashingFilter filter) {
    int falseNegatives = 0;
    for (int i = 0; i < halfArray(input); i++) {
      falseNegatives += filter.contains(input[i]) ? 0 : 1;
    }
    return falseNegatives;
  }

  private void putHalfOfArrayValuesIntoFilter(final long[] input, final LongHashingFilter filter) {
    for (int i = 0; i < halfArray(input); i++) {
      filter.insert(input[i]);
    }
  }

  private int halfArray(final long[] arr) {
    return arr.length / 2;
  }
}