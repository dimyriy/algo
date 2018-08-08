package org.dimyriy.util;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
public class CollectionUtil {
  public static <T extends Comparable<T>> void swap(@Nonnull final T[] arr, final int i, final int j) {
    final T t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static <T> T last(final List<T> list) {
    return list.get(list.size() - 1);
  }

  public static <T> T first(final List<T> list) {
    return list.get(0);
  }
}
