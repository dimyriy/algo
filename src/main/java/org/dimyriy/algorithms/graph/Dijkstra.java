package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@NotThreadSafe
class Dijkstra<T> extends ShortestPathFinder<T> {
  private final PriorityQueue<Node<AdjGraph.Vertex<T>>> shortestNodesQueue = new PriorityQueue<>();
  private final Map<AdjGraph.Vertex<T>, Node<AdjGraph.Vertex<T>>> allNodes = new HashMap<>();

  Dijkstra(@Nonnull final AdjGraph<T> graph) {
    super(graph);
  }

  @Override
  public List<AdjGraph.Vertex<T>> findPath(final AdjGraph.Vertex<T> s, final AdjGraph.Vertex<T> to) {
    return findShortestPath(s, to);
  }

  private List<AdjGraph.Vertex<T>> findShortestPath(@Nonnull final AdjGraph.Vertex<T> source, @Nonnull final AdjGraph.Vertex<T> target) {
    guard(source, target);
    if (source.equals(target)) {
      return Collections.singletonList(source);
    }
    findAllPaths(source);
    final Node<AdjGraph.Vertex<T>> targetNode = allNodes.get(target);
    if (targetNode == null) {
      return null;
    } else {
      return reconstructPath(targetNode);
    }
  }

  private void findAllPaths(@Nonnull final AdjGraph.Vertex<T> source) {
    initialize(source);
    while (!shortestNodesQueue.isEmpty()) {
      final Node<AdjGraph.Vertex<T>> currentShortestNode = shortestNodesQueue.poll();
      graph.getNeighborsWithWeights(currentShortestNode.getVertex()).forEach(vertexWithEdge -> processNeighbor(currentShortestNode, vertexWithEdge));
      currentShortestNode.setVisited();
    }
  }

  private void initialize(final AdjGraph.Vertex<T> source) {
    shortestNodesQueue.clear();
    allNodes.clear();
    final Node<AdjGraph.Vertex<T>> sourceNode = new Node<>(source);
    sourceNode.setCost(0);
    shortestNodesQueue.add(sourceNode);
    allNodes.put(source, sourceNode);
    graph.allVertices().stream().filter(s -> !s.equals(source)).map(Node::new).forEach(s -> {
      shortestNodesQueue.add(s);
      allNodes.put(s.getVertex(), s);
    });
  }

  private void processNeighbor(final Node<AdjGraph.Vertex<T>> currentShortestNode, final Map.Entry<AdjGraph.Vertex<T>, Integer> vertexWithEdge) {
    final Node<AdjGraph.Vertex<T>> node = allNodes.get(vertexWithEdge.getKey());
    if (!node.isVisited()) {
      visitNode(currentShortestNode, vertexWithEdge, node);
    }
  }

  private void visitNode(final Node<AdjGraph.Vertex<T>> currentShortestNode,
                         final Map.Entry<AdjGraph.Vertex<T>, Integer> vertexWithEdge,
                         final Node<AdjGraph.Vertex<T>> node) {
    final int newCost = currentShortestNode.getCost() + vertexWithEdge.getValue();
    if (node.getCost() > newCost) {
      updateNodeCostAndPredecessor(currentShortestNode, node, newCost);
    }
  }

  private void updateNodeCostAndPredecessor(final Node<AdjGraph.Vertex<T>> currentShortestNode,
                                            final Node<AdjGraph.Vertex<T>> node,
                                            final int newCost) {
    shortestNodesQueue.remove(node);
    node.setCost(newCost);
    node.setPredecessor(currentShortestNode);
    shortestNodesQueue.add(node);
  }
}
