package org.dimyriy.datastructures;

import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * <p>
 * Created on 09.08.18
 */
public class VanEmdeBoasTree {
  private static final int MAX_UNIVERSE_SIZE = 1 << 31;
  private final int size;
  private NullableInt min = NullableInt.NULL;
  private NullableInt max = NullableInt.NULL;
  private VanEmdeBoasTree[] clusters = null;
  private VanEmdeBoasTree summary = null;

  public VanEmdeBoasTree(final int size) {
    if (!NumberUtil.isPowerOfTwo(size)) {
      throw new IllegalArgumentException("Universe can only be a 6power of two size");
    }
    if (size > MAX_UNIVERSE_SIZE) {
      throw new IllegalArgumentException("Size cannot be larger than " + Integer.MAX_VALUE);
    }
    this.size = size;
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
