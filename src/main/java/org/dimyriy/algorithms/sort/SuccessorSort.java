package org.dimyriy.algorithms.sort;

import org.dimyriy.datastructures.tree.InsertionAware;
import org.dimyriy.datastructures.tree.SuccessorAware;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author Dmitrii Bogdanov
 * Created at 15.08.18
 */
@SuppressWarnings("unused")
abstract class SuccessorSort<T extends InsertionAware & SuccessorAware> extends AbstractSort<Integer> {
  private final Function<Integer, T> successorAwareStructureCreator;

  SuccessorSort(@Nonnull final Function<Integer, T> successorAndInsertionAwareStructureCreatorByProblemSize) {
    this.successorAwareStructureCreator = successorAndInsertionAwareStructureCreatorByProblemSize;
  }

  @Override
  public boolean isDuplicatesAllowed() {
    return false;
  }

  @Override void sortImpl(@Nonnull final Integer[] arr) {
    final T successorAndInsertionAwareStructure = successorAwareStructureCreator.apply(arr.length);
    for (int i = 0; i < arr.length; i++) {
      successorAndInsertionAwareStructure.insert(arr[i]);
    }
    arr[0] = successorAndInsertionAwareStructure.successor(Integer.MIN_VALUE);
    for (int i = 1; i < arr.length; i++) {
      arr[i] = successorAndInsertionAwareStructure.successor(arr[i - 1]);
    }
  }
}
