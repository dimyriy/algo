package org.dimyriy.datastructures.hashing;

import java.util.BitSet;

/**
 * @author Dmitrii Bogdanov
 * Created at 17.09.18
 */
class LongCuckooFilter implements LongHashingFilter {
  private final BitSet bitSetOne;
  private final BitSet bitSetTwo;
  private final LongHashingFilter.IntHashFunction intHashFunctionOne;
  private final LongHashingFilter.IntHashFunction intHashFunctionTwo;

  LongCuckooFilter(final int capacity) {
    bitSetOne = new BitSet(capacity);
    bitSetTwo = new BitSet(capacity);
    intHashFunctionOne = new IntHashFunction(capacity);
    intHashFunctionTwo = new IntHashFunction(capacity);
  }

  @Override
  public void insert(final long key) {
    final int firstHash = intHashFunctionOne.firstHash(key);
    final int secondHash = intHashFunctionOne.secondHash(key);
    if (!bitSetOne.get(firstHash)) {
      bitSetOne.set(firstHash);
    } else {
      bitSetTwo.set(intHashFunctionTwo.firstHash(key));
    }
    if (!bitSetOne.get(secondHash)) {
      bitSetOne.set(secondHash);
    } else {
      bitSetTwo.set(intHashFunctionTwo.secondHash(key));
    }
  }

  @Override
  public boolean contains(final long key) {
    final int firstHashOne = intHashFunctionOne.firstHash(key);
    final int secondHashOne = intHashFunctionOne.secondHash(key);
    final boolean firstHashFirstArray = bitSetOne.get(firstHashOne);
    final boolean secondHashFirstArray = bitSetOne.get(secondHashOne);
    final int firstHashTwo = intHashFunctionTwo.firstHash(key);
    final int secondHashTwo = intHashFunctionTwo.secondHash(key);
    final boolean firstHashSecondArray = bitSetTwo.get(firstHashTwo);
    final boolean secondHashSecondArray = bitSetTwo.get(secondHashTwo);
    return (firstHashFirstArray || firstHashSecondArray) && (secondHashFirstArray || secondHashSecondArray);
  }
}
