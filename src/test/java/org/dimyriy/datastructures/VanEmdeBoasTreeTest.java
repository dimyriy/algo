package org.dimyriy.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dmitrii Bogdanov
 * Created at 10.08.18
 */
class VanEmdeBoasTreeTest {

  @Test
  void creatingTreeLargerThanAllowedUniverseSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new VanEmdeBoasTree(Integer.MAX_VALUE));
  }

  @Test
  void creatingTreeOfNegativeSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new VanEmdeBoasTree(-1));
  }

  @Test
  void creatingTreeOfNonPowerOfTwoSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new VanEmdeBoasTree(31));
  }

  @Test
  void creatingTreeOfZeroSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new VanEmdeBoasTree(31));
  }

  @Test
  void creatingTreeOfPowerOfTwoSizeCreatesInstance() {
    assertDoesNotThrow(() -> {
      new VanEmdeBoasTree(32);
    });
  }
}