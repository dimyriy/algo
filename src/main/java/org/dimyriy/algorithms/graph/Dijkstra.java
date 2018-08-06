package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.AdjGraph;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@NotThreadSafe
class Dijkstra<T> {
  private final PriorityQueue<Node<AdjGraph.Vertex<T>>> shortestNodesQueue = new PriorityQueue<>();
  private final AdjGraph<T> graph;
  private final Map<AdjGraph.Vertex<T>, Node<AdjGraph.Vertex<T>>> allNodes = new HashMap<>();

  Dijkstra(@Nonnull final AdjGraph<T> graph) {
    this.graph = graph;
  }

  private void findAllPaths(@Nonnull final AdjGraph.Vertex<T> source) {
    initialize(source);
    while (!shortestNodesQueue.isEmpty()) {
      final Node<AdjGraph.Vertex<T>> currentShortestNode = shortestNodesQueue.poll();
      graph.getNeighborsWithWeights(currentShortestNode.vertex).forEach(vertexWithEdge -> processNeighbor(currentShortestNode, vertexWithEdge));
      currentShortestNode.visited = true;
    }
  }

  private void processNeighbor(final Node<AdjGraph.Vertex<T>> currentShortestNode, final Map.Entry<AdjGraph.Vertex<T>, Integer> vertexWithEdge) {
    final Node<AdjGraph.Vertex<T>> node = allNodes.get(vertexWithEdge.getKey());
    if (!node.visited) {
      visitNode(currentShortestNode, vertexWithEdge, node);
    }
  }

  private void visitNode(final Node<AdjGraph.Vertex<T>> currentShortestNode,
                         final Map.Entry<AdjGraph.Vertex<T>, Integer> vertexWithEdge,
                         final Node<AdjGraph.Vertex<T>> node) {
    final int newCost = currentShortestNode.cost + vertexWithEdge.getValue();
    if (node.cost > newCost) {
      updateNodeCostAndPredecessor(currentShortestNode, node, newCost);
    }
  }

  private void updateNodeCostAndPredecessor(final Node<AdjGraph.Vertex<T>> currentShortestNode,
                                            final Node<AdjGraph.Vertex<T>> node,
                                            final int newCost) {
    shortestNodesQueue.remove(node);
    node.cost = newCost;
    node.predecessor = currentShortestNode;
    shortestNodesQueue.add(node);
  }

  private void initialize(final AdjGraph.Vertex<T> source) {
    shortestNodesQueue.clear();
    allNodes.clear();
    final Node<AdjGraph.Vertex<T>> sourceNode = new Node<>(source);
    sourceNode.cost = 0;
    shortestNodesQueue.add(sourceNode);
    allNodes.put(source, sourceNode);
    graph.allVertices().stream().filter(s -> !s.equals(source)).map(Node::new).forEach(s -> {
      shortestNodesQueue.add(s);
      allNodes.put(s.vertex, s);
    });
  }

  List<AdjGraph.Vertex<T>> findShortestPath(@Nonnull final AdjGraph.Vertex<T> source, @Nonnull final AdjGraph.Vertex<T> target) {
    guard(source, target);
    if (source.equals(target)) {
      return Collections.singletonList(source);
    }
    findAllPaths(source);
    final Node<AdjGraph.Vertex<T>> targetNode = allNodes.get(target);
    if (targetNode == null) {
      return null;
    } else {
      return findPathToNode(targetNode);
    }
  }

  private List<AdjGraph.Vertex<T>> findPathToNode(Node<AdjGraph.Vertex<T>> currentLastNode) {
    final List<AdjGraph.Vertex<T>> path = new ArrayList<>();
    path.add(currentLastNode.vertex);
    while (currentLastNode.predecessor != null) {
      path.add(currentLastNode.predecessor.vertex);
      currentLastNode = currentLastNode.predecessor;
    }
    Collections.reverse(path);
    return path;
  }

  private void guard(@Nonnull final AdjGraph.Vertex<T> source, @Nonnull final AdjGraph.Vertex<T> target) {
    if (graph.getNeighborsWithWeights(source).isEmpty() || graph.getNeighborsWithWeights(target).isEmpty()) {
      throw new IllegalStateException("Graph is malformed");
    }
  }

  private static class Node<T> implements Comparable<Node<T>> {
    private final T vertex;
    private int cost = Integer.MAX_VALUE;
    private Node<T> predecessor;
    private boolean visited = false;

    private Node(final T vertex) {
      this.vertex = vertex;
    }

    @Override
    public int compareTo(@Nonnull final Node<T> o) {
      return Integer.compare(cost, o.cost);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;

      final Node<?> node = (Node<?>) o;

      return vertex.equals(node.vertex);
    }

    @Override
    public int hashCode() {
      return vertex.hashCode();
    }
  }
}
