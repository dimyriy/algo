package org.dimyriy.algorithms.string;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 02.09.18
 */
public interface SubstringSearch {
  int search(@Nonnull char[] source, @Nonnull char[] term);
}
