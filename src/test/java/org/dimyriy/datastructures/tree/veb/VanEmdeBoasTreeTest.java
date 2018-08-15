package org.dimyriy.datastructures.tree.veb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

import static org.dimyriy.datastructures.tree.veb.VanEmdeBoasTree.NULL;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 10.08.18
 */
class VanEmdeBoasTreeTest {
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
  private static final Random RANDOM = new Random();
  private static final int smallProblemSize = 1 << 3;
  private static final int mediumProblemSize = 1 << 8;
  private static final int largeProblemSize = 1 << 20;

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
    assertThrows(IllegalArgumentException.class, () -> VanEmdeBoasTree.create(4).insert(Integer.MAX_VALUE));
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

  @Test
  void successorOfTreeContainingOneElementCalledWithXLargerThanExistingElementReturnsNull() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(4);
    tree.insert(3);
    assertEquals(NULL, tree.successor(4));
  }

  @Test
  void successorOfTreeContainingOneElementCalledWithXSmallerThanExistingElementReturnsElement() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(4);
    tree.insert(3);
    assertEquals(new Integer(3), tree.successor(2));
  }

  @Test
  void successorOfElementInTreeContainingTwoElementsInDifferentClustersReturnsCorrectElement() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(16);
    tree.insert(3);
    tree.insert(14);
    assertEquals(new Integer(14), tree.successor(5));
  }

  @Test
  void deletionOfElementFromTreeContainingTwoElementsLeavesOneElement() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(16);
    tree.insert(3);
    tree.insert(14);
    tree.delete(14);
    assertEquals(NULL, tree.successor(5));
    assertEquals(new Integer(3), tree.successor(NULL));
  }

  @Test
  void successorOfElementInTreeContainingOneElementsInNonFirstClustersReturnsCorrectElement() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(16);
    tree.insert(14);
    assertEquals(new Integer(14), tree.successor(3));
  }

  @Test
  void successorOfNullElementIsFirstElementInTree() {
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(16);
    tree.insert(3);
    tree.insert(14);
    assertEquals(new Integer(3), tree.successor(NULL));
  }

  @Test
  void testTreeReturnsSuccessorsInSortedOrderForSmallProblemSize() {
    assertSuccessorOrderForProblemSize(smallProblemSize);
  }

  @Test
  void testTreeReturnsSuccessorsInSortedOrderForMediumProblemSize() {
    assertSuccessorOrderForProblemSize(mediumProblemSize);
  }

  @Test
  void testTreeReturnsSuccessorsInSortedOrderForLargeProblemSize() {
    assertSuccessorOrderForProblemSize(largeProblemSize);
  }

  private void assertSuccessorOrderForProblemSize(final int problemSize) {
    log("Creation of tree started...");
    final VanEmdeBoasTree tree = VanEmdeBoasTree.create(problemSize);
    log("...Creation of tree finished.");
    log("Generation of random array started...");
    final int[] problemSet = RANDOM.ints(1, problemSize).limit(problemSize * 2).distinct().limit(problemSize).toArray();
    log("...Generation of random array finished.");
    log("Insertion of elements into tree started...");
    Arrays.stream(problemSet).forEach(tree::insert);
    log("...Insertion of elements into tree finished.");
    final IntWrapper currentSuccessor = new IntWrapper(NULL);
    log("Filling resulting array with successors started...");
    final int[] result = IntStream.generate(() -> {
      currentSuccessor.x = tree.successor(currentSuccessor.x);
      return currentSuccessor.x;
    }).limit(problemSet.length).toArray();
    log("...Filling resulting array with successors finished.");
    log("Sorting initial array using standard sort started...");
    Arrays.sort(problemSet);
    log("...Sorting initial array using standard sort finished.");
    log("Assertion started...");
    Assertions.assertArrayEquals(problemSet, result);
    log("...Assertion finished.");
  }

  private void log(final String s) {
    System.out.println("[" + SIMPLE_DATE_FORMAT.format(new Date()) + "] -> " + s);
  }

  @SuppressWarnings("PackageVisibleField")
  private class IntWrapper {
    int x;

    IntWrapper(final int x) {
      this.x = x;
    }
  }
}