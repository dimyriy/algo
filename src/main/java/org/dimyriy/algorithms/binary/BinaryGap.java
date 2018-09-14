package org.dimyriy.algorithms.binary;

/**
 * @author Dmitrii Bogdanov
 *
 * Created on 14.09.18
 *
 * Copyright (c) 2018, Check24 Vergleichsportal GmbH
 */
class BinaryGap {
  private static final int INT_LENGTH = Integer.SIZE;
  private static final int MAX_GAP = INT_LENGTH - 1;
  private static final int ALL_ONES = ~0;

  int findMaxGap(final int number) {
    for (int gapSize = MAX_GAP; gapSize > 0; gapSize--) {
      for (int currentBit = 0; currentBit <= INT_LENGTH - 1 - gapSize; currentBit++) {
        final int mask = createMaskOfSizeNStartingAtBit(gapSize, currentBit);
        final int expectedGap = createGapOfSizeNStartingAtBit(gapSize, currentBit);
        if (expectedGap == (number & mask)) {
          return gapSize - 1;
        }
      }
    }
    return 0;
  }

  private int createMaskOfSizeNStartingAtBit(final int gapSize, final int bitNumber) {
    return (ALL_ONES << bitNumber) & (ALL_ONES >>> (INT_LENGTH - gapSize - bitNumber - 1));
  }

  private int createGapOfSizeNStartingAtBit(final int gapSize, final int bitNumber) {
    return (1 + (1 << (gapSize))) << bitNumber;
  }
}
