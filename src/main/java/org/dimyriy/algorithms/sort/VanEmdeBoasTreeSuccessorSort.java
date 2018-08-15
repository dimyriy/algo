package org.dimyriy.algorithms.sort;

import org.dimyriy.datastructures.tree.veb.VanEmdeBoasTree;
import org.dimyriy.util.NumberUtil;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 15.08.18
 */
@SuppressWarnings("unused")
public class VanEmdeBoasTreeSuccessorSort extends SuccessorSort<Integer, Integer, VanEmdeBoasTree> {
  public VanEmdeBoasTreeSuccessorSort() {
    super(problemSize -> VanEmdeBoasTree.create(NumberUtil.nextPowerOfTwo(problemSize)));
  }

  @Override
  void sortImpl(@Nonnull final Integer[] arr) {
    final VanEmdeBoasTree successorAndInsertionAwareStructure = createDataStructureAndInsertAllElements(arr);
    arr[0] = successorAndInsertionAwareStructure.successor(Integer.MIN_VALUE);
    for (int i = 1; i < arr.length; i++) {
      arr[i] = successorAndInsertionAwareStructure.successor(arr[i - 1]);
    }
  }
}
