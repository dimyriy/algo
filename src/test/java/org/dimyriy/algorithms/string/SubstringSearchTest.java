package org.dimyriy.algorithms.string;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 02.09.18
 */
@SuppressWarnings("SpellCheckingInspection")
class SubstringSearchTest {
  private static final Random RANDOM = new Random();
  private static final int MAX_STRING_SIZE = (int) Math.min(Runtime.getRuntime().maxMemory() >> 2, 1 << 25);
  private final String source = "dsjkhkrhjhbwbtbgmnbjkahstjkghfajnrnasdjhjsdhbfhbtvqghmewhbhjbybthjhbghbrdbsearchtermklfjdslkfrjtbhbtnbsnmbgmfhfurg";
  private final String term = "searchterm";
  private final String longSearchTerm = source;

  @Test
  void testNaiveAlgorithmReturnsCorrectTermPositionInString() {
    Assert.assertThat(new NaiveSearch().search(source.toCharArray(), term.toCharArray()), CoreMatchers.is(CoreMatchers.equalTo(source.indexOf(term))));
  }

  @Test
  void testRabinKarpAlgorithmReturnsCorrectTermPositionInString() {
    Assert.assertThat(new RabinKarpSearch().search(source.toCharArray(), term.toCharArray()), CoreMatchers.is(CoreMatchers.equalTo(source.indexOf(term))));
  }

  @Test
  void testRabinKarpAlgorithmReturnsCorrectTermPositionForReallyLargeString() {
    final int indexOfTerm = RANDOM.nextInt(MAX_STRING_SIZE / 2);
    final char[] source = generateReallyLargeString(indexOfTerm);
    Assert.assertThat(new RabinKarpSearch().search(source, longSearchTerm.toCharArray()), CoreMatchers.is(CoreMatchers.equalTo(indexOfTerm)));
  }

  private char[] generateReallyLargeString(final int indexOfTerm) {
    final char[] source = new char[MAX_STRING_SIZE];
    for (int i = 0; i < indexOfTerm; i++) {
      source[i] = generateRandomChar();
    }
    for (int i = indexOfTerm; i < indexOfTerm + longSearchTerm.length(); i++) {
      source[i] = longSearchTerm.charAt(i - indexOfTerm);
    }
    for (int i = indexOfTerm + longSearchTerm.length(); i < source.length; i++) {
      source[i] = generateRandomChar();
    }
    return source;
  }

  private char generateRandomChar() {
    return (char) (RANDOM.nextInt('z' - 'a') + 'a');
  }
}