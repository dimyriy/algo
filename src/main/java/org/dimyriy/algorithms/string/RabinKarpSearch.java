package org.dimyriy.algorithms.string;

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
      slidingSourceHash.skip(source[i - term.length]);
      slidingSourceHash.append(source[i]);
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
    private int hash = 0;

    MutableHashedString(final int termLength, @Nonnull final char[] string) {
      for (int i = 0; i < termLength; i++) {
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

    void append(final char symbol) {
      hash += symbol;
    }

    void skip(final char symbol) {
      hash -= symbol;
    }
  }
}
