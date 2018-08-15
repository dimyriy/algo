package org.dimyriy.algorithms.sort;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.07.18
 */
public interface Sort<T extends Comparable<T>> {
  void sort(@Nullable T[] arr);

  boolean isSortedAsc(@Nonnull T[] arr);

  boolean isFast();

  boolean isDuplicatesAllowed();
}
