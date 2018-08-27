package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.08.18
 */
abstract class ShortestPathFinder<T> implements GraphPathFinder<T> {
  final AdjGraph<T> graph;

  ShortestPathFinder(@Nonnull final AdjGraph<T> graph) {
    this.graph = graph;
  }

  void guard(@Nonnull final AdjGraph.Vertex<T> source, @Nonnull final AdjGraph.Vertex<T> target) {
    if (graph.getNeighborsWithWeights(source).isEmpty() || graph.getNeighborsWithWeights(target).isEmpty()) {
      throw new IllegalStateException("Graph is malformed");
    }
  }
}
