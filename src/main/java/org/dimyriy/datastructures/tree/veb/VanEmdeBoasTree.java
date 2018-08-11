package org.dimyriy.datastructures.tree.veb;

import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * <p>
 * Created on 09.08.18
 */
class VanEmdeBoasTree {
  private static final int MAX_UNIVERSE_SIZE = 1 << 30;
  private final int size;
  private NullableInt min = NullableInt.NULL;
  private NullableInt max = NullableInt.NULL;
  private VanEmdeBoasTree[] clusters = null;
  private VanEmdeBoasTree summary = null;

  VanEmdeBoasTree(final int size) {
    if (size < 1) {
      throw new IllegalArgumentException("Cannot create tree of size smaller then 1");
    }
    if (!NumberUtil.isPowerOfTwo(size)) {
      throw new IllegalArgumentException("Universe can only be a power of two size");
    }
    if (size > MAX_UNIVERSE_SIZE) {
      throw new IllegalArgumentException("Size cannot be larger than " + MAX_UNIVERSE_SIZE);
    }
    this.size = size;
  }

  public static int high(final int x) {
    return 0;
  }

  public void insert(final int x) {
    if (min == NullableInt.NULL) {
      min = new NullableInt(x);
      max = new NullableInt(x);
    }
  }

  private static final class NullableInt {
    private static final NullableInt NULL = new NullableInt();
    private final int v;
    private final boolean isNull;

    private NullableInt() {
      this.isNull = true;
      v = 0;
    }

    NullableInt(final int v) {
      this.isNull = false;
      this.v = v;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof NullableInt)) return false;

      final NullableInt that = (NullableInt) o;

      if (v != that.v) return false;
      return isNull == that.isNull;
    }

    @Override public int hashCode() {
      int result = v;
      result = 31 * result + (isNull ? 1 : 0);
      return result;
    }
  }
}
