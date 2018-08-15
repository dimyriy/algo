package org.dimyriy.datastructures.tree;

/**
 * @author Dmitrii Bogdanov
 * Created at 11.08.18
 */
public interface SuccessorAware<K, T> {
  T successor(K x);
}
