package org.dimyriy.algorithms.string;

import org.dimyriy.util.NumberUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 02.09.18
 */
class RabinKarpSearch extends AbstractSubstringSearch {
  @Override
  public int searchImpl(@Nonnull final char[] source, @Nonnull final char[] term) {
    final MutableHashedString termHash = new MutableHashedString(term.length, term);
    final MutableHashedString slidingSourceHash = new MutableHashedString(term.length, source);
    for (int i = term.length; i < source.length; i++) {
      slidingSourceHash.skipFirstCharAndAppendLastChar(source[i - term.length], source[i]);
      if (termHash.equals(slidingSourceHash)) {
        boolean isEqual = true;
        for (int j = 0; j < term.length; j++) {
          if (source[i - term.length + j + 1] != term[j]) {
            isEqual = false;
            break;
          }
        }
        if (isEqual) {
          return i - term.length + 1;
        }
      }
    }
    return -1;
  }

  private static final class MutableHashedString {
    private static final int BASE_EXP = 8;
    private static final int BASE = 1 << BASE_EXP;
    private final int prime;
    private final int aInXMinusOneModP;
    private int hash = 0;

    MutableHashedString(final int windowSize, @Nonnull final char[] string) {
      this.prime = choosePrime();
      this.aInXMinusOneModP = calculateAInXMinusOneModP(windowSize);
      for (int i = 0; i < windowSize; i++) {
        append(string[i]);
      }
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof MutableHashedString)) return false;
      final MutableHashedString that = (MutableHashedString) o;
      return hash == that.hash;
    }

    void append(final char c) {
      hash = (hash * BASE + symbolHash(c)) % prime;
    }

    void skipFirstCharAndAppendLastChar(final char firstChar, final char lastChar) {
      hash = (((hash - (symbolHash(firstChar) * aInXMinusOneModP) % prime) * BASE) % prime + symbolHash(lastChar)) % prime;
    }

    int symbolHash(final char symbol) {
      return (int) symbol;
    }

    private int choosePrime() {
      return (int) NumberUtil.nextPrime(BASE + 1);
    }

    private int calculateAInXMinusOneModP(final int windowSize) {
      int aInXMinusOneModP = 1;
      for (int i = 0; i < windowSize - 1; i++) {
        aInXMinusOneModP = (aInXMinusOneModP * BASE) % prime;
      }
      return aInXMinusOneModP;
    }
  }
}
