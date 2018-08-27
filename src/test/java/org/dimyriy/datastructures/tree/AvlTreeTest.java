package org.dimyriy.datastructures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class AvlTreeTest {
  private static final int PROBLEM_SIZE = 100;
  private static final Random RANDOM = new Random();
  private int min;

  @BeforeEach
  void setUp() {
    min = Integer.MAX_VALUE;
  }

  @Test
  void testBuiltTreeIsBst() {
    final AvlTree tree = buildRandomTree();
    Assertions.assertTrue(tree.isBst());
  }

  @Test
  void testBuiltTreeMinimumIsCorrect() {
    final AvlTree tree = buildRandomTree();
    Assertions.assertEquals(min, tree.findMinimum().getKey());
  }

  @Test
  void testBuildTreeIsBalanced() {
    final AvlTree tree = buildRandomTree();
    Assertions.assertTrue(tree.isBalanced());
  }

  private AvlTree buildRandomTree() {
    final AvlTree tree = new AvlTree();
    for (int i = 0; i < PROBLEM_SIZE; i++) {
      final int value = RANDOM.nextInt(PROBLEM_SIZE / 2);
      if (value < min) {
        min = value;
      }
      tree.insert(value);
    }
    return tree;
  }
}