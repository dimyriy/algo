package org.dimyriy.datastructures.hashing;

import java.util.BitSet;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class LongBloomFilter implements LongHashingFilter {
  private final BitSet bitSet;
  private final LongHashingFilter.IntHashFunction intHashFunction;

  LongBloomFilter(final int capacity) {
    bitSet = new BitSet(capacity);
    intHashFunction = new LongHashingFilter.IntHashFunction(capacity);
  }

  @Override
  public void insert(final long key) {
    bitSet.set(intHashFunction.firstHash(key));
    bitSet.set(intHashFunction.secondHash(key));
  }

  @Override
  public boolean contains(final long key) {
    return bitSet.get(intHashFunction.firstHash(key)) && bitSet.get(intHashFunction.secondHash(key));
  }
}
