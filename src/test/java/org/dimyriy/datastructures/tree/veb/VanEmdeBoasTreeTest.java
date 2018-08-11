package org.dimyriy.datastructures.tree.veb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 10.08.18
 */
class VanEmdeBoasTreeTest {

  @Test
  void creatingTreeLargerThanAllowedUniverseSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(Integer.MAX_VALUE));
  }

  @Test
  void creatingTreeOfNegativeSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(-1));
  }

  @Test
  void creatingTreeOfLessThanMinUniverseSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(1));
  }

  @Test
  void creatingTreeOfNonPowerOfTwoSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(31));
  }

  @Test
  void creatingTreeOfZeroSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(0));
  }

  @Test
  void creatingTreeOfPowerOfTwoSizeCreatesInstance() {
    assertDoesNotThrow(() -> {
      VanEmdeBoasTree.create(32);
    });
  }

  @Test
  void creatingTreeOsSizeTwoCreatesSpecialCaseTree() {
    assertEquals(VanEmdeBoasTree.VanEmdeBoasTreeOfMinUniverseSize.class, VanEmdeBoasTree.create(2).getClass());
  }

  @Test
  void insertingIntoTreeElementLargerThenMaxUniverseSizeThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      VanEmdeBoasTree.create(4).insert(Integer.MAX_VALUE);
    });
  }

  @Test
  void insertingIntoTreeNegativeElementThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(4).insert(-1));
  }

  @Test
  void insertingIntoTreeZeroDoesNotThrowExceptions() {
    assertDoesNotThrow(() -> VanEmdeBoasTree.create(4).insert(0));
  }

  @Test
  void insertingValidIntegerIntoTreeDoesNotThrowException() {
    assertDoesNotThrow(() -> VanEmdeBoasTree.create(4).insert(3));
  }
}