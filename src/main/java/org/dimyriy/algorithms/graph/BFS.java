package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.AdjGraph;

import java.util.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
class BFS<T> implements PathSearch<T> {
  private final AdjGraph<T> graph;

  BFS(final AdjGraph<T> graph) {
    this.graph = graph;
  }

  public List<AdjGraph.Vertex<T>> traverse(final AdjGraph.Vertex<T> s) {
    final List<AdjGraph.Vertex<T>> visited = new LinkedList<>();
    final Queue<AdjGraph.Vertex<T>> queue = new ArrayDeque<>();
    visited.add(s);
    queue.add(s);
    while (queue.size() != 0) {
      final AdjGraph.Vertex<T> node = queue.poll();
      for (final AdjGraph.Vertex<T> neighbor : graph.getNeighbors(node)) {
        if (!visited.contains(neighbor)) {
          queue.add(node);
          visited.add(neighbor);
        }
      }
    }
    return visited;
  }

  @Override
  public List<AdjGraph.Vertex<T>> searchPath(final AdjGraph.Vertex<T> s, final AdjGraph.Vertex<T> to) {
    final List<AdjGraph.Vertex<T>> visited = new LinkedList<>();
    final Queue<AdjGraph.Vertex<T>> queue = new ArrayDeque<>();
    visited.add(s);
    queue.add(s);
    while (queue.size() != 0) {
      final AdjGraph.Vertex<T> node = queue.poll();
      if (node.equals(to)) {
        if (!visited.contains(to)) {
          visited.add(node);
        }
        return visited;
      }
      for (final AdjGraph.Vertex<T> neighbor : graph.getNeighbors(node)) {
        if (!visited.contains(neighbor)) {
          queue.add(node);
          visited.add(neighbor);
        }
      }
    }
    return Collections.emptyList();
  }
}
