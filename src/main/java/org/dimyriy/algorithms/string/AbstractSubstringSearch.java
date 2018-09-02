package org.dimyriy.algorithms.string;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * @author Dmitrii Bogdanov
 * Created at 02.09.18
 */
public abstract class AbstractSubstringSearch implements SubstringSearch {

  @Override
  public int search(@Nonnull final char[] source, @Nonnull final char[] term) {
    if (term.length == 0) {
      return -1;
    }
    if (term.length > source.length) {
      return -1;
    }
    if (term.length == source.length) {
      return Arrays.equals(term, source) ? 0 : -1;
    }
    if (term.length == 1) {
      for (int i = 0; i < source.length; i++) {
        if (source[i] == term[0]) {
          return i;
        }
      }
    }
    return searchImpl(source, term);
  }

  public abstract int searchImpl(@Nonnull char[] source, @Nonnull char[] term);
}
