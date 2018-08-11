package org.dimyriy.datastructures.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class AvlTreeTest {
  private static final int PROBLEM_SIZE = 100;
  private static final Random RANDOM = new Random();

  @Test
  void checkBuiltTreeIsBst() {
    final AvlTree tree = buildRandomTree();
    Assertions.assertTrue(tree.isBst());
  }

  @Test
  void checkTreeIsBalanced() {
    final AvlTree tree = buildRandomTree();
    Assertions.assertTrue(tree.isBalanced());
  }

  private AvlTree buildRandomTree() {
    final AvlTree tree = new AvlTree();
    for (int i = 0; i < PROBLEM_SIZE; i++) {
      tree.add(RANDOM.nextInt(PROBLEM_SIZE / 2));
    }
    return tree;
  }
}