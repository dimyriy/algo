package org.dimyriy.algorithms.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class IntegerReversalTest {
  private IntegerReversal integerReversal;

  @BeforeEach
  void setUp() {
    integerReversal = new IntegerReversal();
  }

  @Test
  void nDigitsForZeroShouldReturn1() {
    Assertions.assertEquals(1, integerReversal.nDigits(0));
  }

  @Test
  void nDigitsForOneDigitNumberShouldReturn1() {
    Assertions.assertEquals(1, new IntegerReversal().nDigits(5));
  }

  @Test
  void nDigitsForTwoDigitNumberShouldReturn2() {
    Assertions.assertEquals(2, integerReversal.nDigits(15));
  }

  @Test
  void nDigitsForPrettyLargeNumberOfDigitsNumberShouldReturnCorrectAnswer() {
    Assertions.assertEquals(9, integerReversal.nDigits(249867984));
  }

  @Test
  void nDigitsForPrettyLargeNumberOfDigitsNegativeNumberShouldReturnCorrectAnswer() {
    Assertions.assertEquals(9, integerReversal.nDigits(-649867384));
  }

  @Test
  void inverseOfZeroShouldReturnZero() {
    Assertions.assertEquals(0, integerReversal.inverse(0));
  }

  @Test
  void inverseOfOneShouldReturnOne() {
    Assertions.assertEquals(1, integerReversal.inverse(1));
  }

  @Test
  void inverseOfOneDigitNumberShouldReturnTheSameNumber() {
    Assertions.assertEquals(7, integerReversal.inverse(7));
  }

  @Test
  void inverseOfPrettyLargeNumberOfDigitsNegativeNumberShouldReturnCorrectAnswer() {
    Assertions.assertEquals(-48376894, integerReversal.inverse(-49867384));
  }

  @Test
  void inverseOfPrettyLargeNumberOfDigitsNumberShouldReturnCorrectAnswer() {
    Assertions.assertEquals(489760694, integerReversal.inverse(496067984));
  }
}