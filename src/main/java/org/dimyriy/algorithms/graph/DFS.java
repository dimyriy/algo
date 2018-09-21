package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@NotThreadSafe
public class DFS<T> implements GraphPathFinder<T>, GraphTraverser<T> {
  private final List<AdjGraph.Vertex<T>> visited = new LinkedList<>();
  private final AdjGraph<T> graph;

  DFS(final AdjGraph<T> graph) {
    this.graph = graph;
  }

  @Override
  public List<AdjGraph.Vertex<T>> findPath(final AdjGraph.Vertex<T> s, final AdjGraph.Vertex<T> to) {
    visited.add(s);
    return Collections.emptyList();
  }

  @Override
  public List<AdjGraph.Vertex<T>> traverse(final AdjGraph.Vertex<T> s) {
    visited.add(s);
    traverseRecursively(s);
    return visited;
  }

  private void traverseRecursively(final AdjGraph.Vertex<T> s) {
    for (final AdjGraph.Vertex<T> node : graph.getNeighbors(s)) {
      if (!visited.contains(node)) {
        visited.add(node);
        traverseRecursively(node);
      }
    }
  }
}
