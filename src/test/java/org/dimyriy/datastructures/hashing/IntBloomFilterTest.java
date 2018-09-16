package org.dimyriy.datastructures.hashing;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class IntBloomFilterTest {

  @Test
  void testSmallPositiveProbabilityIsLessThanTwentyPercent() {
    for (int capacity = 2 << 16; capacity < 2 << 22; capacity = capacity << 2) {
      final long[] input = new Random().longs(capacity).distinct().toArray();
      final IntBloomFilter bf = new IntBloomFilter(halfArray(input));
      putHalfOfArrayValuesIntoFilter(input, bf);
      final int falsePositives = calculateNumberOfFalsePositives(input, bf);
      assertThat("falsePositive probability for capacity " + capacity, calculatePercentageOfFalsePositives(input, falsePositives) * 100, lessThan(20.));
    }
  }

  private double calculatePercentageOfFalsePositives(final long[] input, final double falsePositives) {
    return ((falsePositives + 0.) / halfArray(input));
  }

  private int calculateNumberOfFalsePositives(final long[] input, final IntBloomFilter bf) {
    int falsePositives = 0;
    for (int i = halfArray(input); i < input.length; i++) {
      falsePositives += bf.contains(input[i]) ? 1 : 0;
    }
    return falsePositives;
  }

  private void putHalfOfArrayValuesIntoFilter(final long[] input, final IntBloomFilter bf) {
    for (int i = 0; i < halfArray(input); i++) {
      bf.insert(input[i]);
    }
  }

  private int halfArray(final long[] arr) {
    return arr.length / 2;
  }
}