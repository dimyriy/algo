package org.dimyriy.algorithms.string;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 02.09.18
 */
public class NaiveSearch extends AbstractSubstringSearch {
  @Override
  public int searchImpl(@Nonnull final char[] source, @Nonnull final char[] term) {
    for (int i = term.length; i < source.length; i++) {
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
    return -1;
  }
}
