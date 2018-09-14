package org.dimyriy.algorithms.binary;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 *
 * Created on 14.09.18
 *
 * Copyright (c) 2018, Check24 Vergleichsportal GmbH
 */
class BinaryGapTest {

  @Test
  void findMaxGapOnZeroReturnsZero() {
    Assert.assertEquals(0, new BinaryGap().findMaxGap(0));
  }

  @Test
  void findMaxGapOnOneReturnsZero() {
    Assert.assertEquals(0, new BinaryGap().findMaxGap(1));
  }

  @Test
  void findMaxGapOnAllOnesReturnsZero() {
    Assert.assertEquals(0, new BinaryGap().findMaxGap(0b1111111111111111));
  }

  @Test
  void findMaxGapOnAllZeroesReturnsZero() {
    Assert.assertEquals(0, new BinaryGap().findMaxGap(0b000000000000));
  }

  @Test
  void findMaxGapOnIntegerWithOneGapOfSizeOneReturnsOne() {
    Assert.assertEquals(1, new BinaryGap().findMaxGap(0b101));
  }

  @Test
  void findMaxGapOnIntegerWithOneGapOfSizeOneSurrondedByOnesReturnsOne() {
    Assert.assertEquals(1, new BinaryGap().findMaxGap(0b11110111));
  }

  @Test
  void findMaxGapOnIntegerWithOneGapOfSizeNReturnsN() {
    Assert.assertEquals(10, new BinaryGap().findMaxGap(0b1110_000_000_000_11));
  }

  @Test
  void findMaxGapOnIntegerWithOneGapOfSizeNStartingAtFirstBitReturnsN() {
    Assert.assertEquals(10, new BinaryGap().findMaxGap(0b1110_000_000_0001));
  }

  @Test
  void findMaxGapOnIntegerWithOneGapOfSizeNEndingAtLastBitReturnsN() {
    Assert.assertEquals(10, new BinaryGap().findMaxGap(0b10_000_000_000_111));
  }

  @Test
  void findMaxGapOnIntegerWithMGapsOfSizeNReturnsN() {
    Assert.assertEquals(4, new BinaryGap().findMaxGap(0b10000100001000010000100001));
  }

  @Test
  void findMaxGapOnIntegerWithGapsOfDifferentSizesReturnsOne() {
    Assert.assertEquals(4, new BinaryGap().findMaxGap(0b100010001000010000101));
  }
}