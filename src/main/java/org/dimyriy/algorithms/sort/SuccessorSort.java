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
abstract class SuccessorSort<K, R, T extends InsertionAware & SuccessorAware<K, R>> extends AbstractSort<Integer> {
  private final Function<Integer, T> successorAwareStructureCreator;

  SuccessorSort(@Nonnull final Function<Integer, T> successorAndInsertionAwareStructureCreatorByProblemSize) {
    this.successorAwareStructureCreator = successorAndInsertionAwareStructureCreatorByProblemSize;
  }

  @Override
  public boolean isDuplicatesAllowed() {
    return false;
  }

  T createDataStructureAndInsertAllElements(@Nonnull final Integer[] arr) {
    final T successorAndInsertionAwareStructure = successorAwareStructureCreator.apply(arr.length);
    for (final Integer anArr : arr) {
      successorAndInsertionAwareStructure.insert(anArr);
    }
    return successorAndInsertionAwareStructure;
  }
}
