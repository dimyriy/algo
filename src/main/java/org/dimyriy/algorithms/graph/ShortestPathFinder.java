package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.08.18
 */
@SuppressWarnings("PackageVisibleField")
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

  List<AdjGraph.Vertex<T>> reconstructPath(Node<AdjGraph.Vertex<T>> currentNode) {
    final List<AdjGraph.Vertex<T>> path = new ArrayList<>();
    path.add(currentNode.getVertex());
    while (currentNode.getPredecessor() != null) {
      path.add(currentNode.getPredecessor().getVertex());
      currentNode = currentNode.getPredecessor();
    }
    Collections.reverse(path);
    return path;
  }
}
