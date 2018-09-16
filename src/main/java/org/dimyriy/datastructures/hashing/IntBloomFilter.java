package org.dimyriy.datastructures.hashing;

import org.dimyriy.util.NumberUtil;

import java.util.BitSet;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class IntBloomFilter {
  private final BitSet bitSet;
  private final IntHashFunction intHashFunction;

  IntBloomFilter(final int capacity) {
    bitSet = new BitSet(capacity);
    intHashFunction = new IntHashFunction(capacity);
  }

  void insert(final long key) {
    bitSet.set(intHashFunction.firstHash(key, bitSet.size() - 1));
    bitSet.set(intHashFunction.secondHash(key, bitSet.size() - 1));
  }

  boolean contains(final long key) {
    return bitSet.get(intHashFunction.firstHash(key, bitSet.length() - 1)) && bitSet.get(intHashFunction.firstHash(key, bitSet.length() - 1));
  }

  private static class IntHashFunction {
    private final int prime1;
    private final int prime2;
    private final int nonRandomSalt11 = 0b10110110110011010;
    private final int nonRandomSalt12 = 0b11110101110101011;
    private final int nonRandomSalt21 = 0b101100010100101001;
    private final int nonRandomSalt22 = 0b10001101011001001;
    private final int shift1 = 5;
    private final int shift2 = 7;

    IntHashFunction(final int universeSize) {
      prime1 = NumberUtil.nextRandomPrime(universeSize * 67);
      prime2 = NumberUtil.nextRandomPrime(universeSize * 121);
    }

    int firstHash(final long key, final int universeSize) {
      return generateHash(key, universeSize, shift1, nonRandomSalt11, nonRandomSalt12, prime1);
    }

    int secondHash(final long key, final int universeSize) {
      return generateHash(key, universeSize, shift2, nonRandomSalt21, nonRandomSalt22, prime2);
    }

    private int generateHash(final long key, final int universeSize, final int shift, final int salt1, final int salt2, final int mod) {
      return (int) Math.abs(((((key & salt1) << shift) ^ (key ^ salt2)) % mod));
    }
  }
}
