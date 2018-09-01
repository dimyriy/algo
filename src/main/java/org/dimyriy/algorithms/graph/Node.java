package org.dimyriy.algorithms.graph;

import javax.annotation.Nonnull;

/**
 * @author Dmitrii Bogdanov
 * Created at 27.08.18
 */
class Node<T> implements Comparable<Node<T>> {
  private final T vertex;
  private double cost = Double.MAX_VALUE;
  private Node<T> predecessor;
  private boolean visited = false;

  Node(final T vertex) {
    this.vertex = vertex;
  }

  @Override
  public int compareTo(@Nonnull final Node<T> o) {
    return Double.compare(cost, o.cost);
  }

  @Override
  public int hashCode() {
    return vertex.hashCode();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Node)) return false;
    final Node<?> node = (Node<?>) o;
    return vertex.equals(node.vertex);
  }

  T getVertex() {
    return vertex;
  }

  double getCost() {
    return cost;
  }

  void setCost(final double cost) {
    this.cost = cost;
  }

  Node<T> getPredecessor() {
    return predecessor;
  }

  void setPredecessor(final Node<T> predecessor) {
    this.predecessor = predecessor;
  }

  boolean isVisited() {
    return visited;
  }

  void setVisited() {
    this.visited = true;
  }
}
