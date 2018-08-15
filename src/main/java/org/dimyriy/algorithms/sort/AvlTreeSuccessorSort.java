package org.dimyriy.algorithms.sort;

import org.dimyriy.datastructures.tree.AvlTree;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 15.08.18
 */
@SuppressWarnings("unused")
public class AvlTreeSuccessorSort extends SuccessorSort<AvlTree.Node, AvlTree.Node, AvlTree> {
  public AvlTreeSuccessorSort() {
    super(o -> new AvlTree());
  }

  @Override void sortImpl(@Nonnull final Integer[] arr) {
    final AvlTree successorAndInsertionAwareStructure = createDataStructureAndInsertAllElements(arr);
    AvlTree.Node successor = successorAndInsertionAwareStructure.findMinimum();
    int i = 0;
    while (successor != null) {
      arr[i] = successor.getKey();
      successor = successorAndInsertionAwareStructure.successor(successor);
      i++;
    }
  }
}
