package org.dimyriy.util;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 23.07.18
 */
@SuppressWarnings("unused")
public class CollectionUtil {
  public static <T extends Comparable<T>> void swap(@Nonnull final T[] arr, final int i, final int j) {
    final T t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static <T> T first(final List<T> list) {
    return list.get(0);
  }

  public static <T> T last(final List<T> list) {
    return list.get(lastIndex(list));
  }

  public static <T> int middle(final List<T> list) {
    return list.size() / 2;
  }

  public static <T> int incrementIndexCyclic(final List<T> list, final int i) {
    if (i == list.size() - 1) {
      if (list.isEmpty()) {
        throw new ArrayIndexOutOfBoundsException(0);
      }
      return 0;
    } else {
      return i + 1;
    }
  }

  public static <T> int decrementIndexCyclic(final List<T> list, final int i) {
    if (i == 0) {
      if (list.isEmpty()) {
        throw new ArrayIndexOutOfBoundsException(0);
      }
      return list.size() - 1;
    } else {
      return i - 1;
    }
  }

  private static <T> int lastIndex(final List<T> list) {
    return list.size() - 1;
  }
}
