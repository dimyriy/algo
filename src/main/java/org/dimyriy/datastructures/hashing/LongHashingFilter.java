package org.dimyriy.datastructures.hashing;

import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * Created at 17.09.18
 */
public interface LongHashingFilter {
  void insert(final long key);

  boolean contains(final long key);

  class IntHashFunction {
    private static final int NON_RANDOM_SALT_11 = 0b10110110110011010;
    private static final int NON_RANDOM_SALT_12 = 0b11110101110101011;
    private static final int NON_RANDOM_SALT_21 = 0b101100010100101001;
    private static final int NON_RANDOM_SALT_22 = 0b10001101011001001;
    private static final int SHIFT_1 = 5;
    private static final int SHIFT_2 = 7;
    private final int prime1;
    private final int prime2;

    IntHashFunction(final int universeSize) {
      prime1 = NumberUtil.nextRandomPrime(universeSize * 67);
      prime2 = NumberUtil.nextRandomPrime(universeSize * 121);
    }

    int firstHash(final long key, final int universeSize) {
      return generateHash(key, universeSize, SHIFT_1, NON_RANDOM_SALT_11, NON_RANDOM_SALT_12, prime1);
    }

    int secondHash(final long key, final int universeSize) {
      return generateHash(key, universeSize, SHIFT_2, NON_RANDOM_SALT_21, NON_RANDOM_SALT_22, prime2);
    }

    private int generateHash(final long key, final int universeSize, final int shift, final int salt1, final int salt2, final int mod) {
      return (int) (Math.abs(((((key & salt1) << shift) ^ (key ^ salt2)) % mod))) % universeSize;
    }
  }
}
