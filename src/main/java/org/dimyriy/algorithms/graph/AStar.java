package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.LabeledPoint2d;
import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.08.18
 */
public class AStar extends ShortestPathFinder<LabeledPoint2d> {
  private final Set<AdjGraph.Vertex<LabeledPoint2d>> closedSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  private final Set<AdjGraph.Vertex<LabeledPoint2d>> openSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  private final PriorityQueue<Node<AdjGraph.Vertex<LabeledPoint2d>>> shortestNodesQueue = new PriorityQueue<>();
  private final Map<Node<AdjGraph.Vertex<LabeledPoint2d>>, Double> gScore = new ConcurrentHashMap<>();
  private final Map<Node<AdjGraph.Vertex<LabeledPoint2d>>, Double> fScore = new ConcurrentHashMap<>();

  AStar(@Nonnull final AdjGraph<LabeledPoint2d> graph) {
    super(graph);
  }

  @Override
  public List<AdjGraph.Vertex<LabeledPoint2d>> findPath(final AdjGraph.Vertex<LabeledPoint2d> s, final AdjGraph.Vertex<LabeledPoint2d> to) {
    gScore.clear();
    fScore.clear();
    closedSet.clear();
    openSet.clear();
    shortestNodesQueue.clear();
    final Node<AdjGraph.Vertex<LabeledPoint2d>> source = new Node<>(s);
    gScore.put(source, 0d);
    fScore.put(source, heuristic(s, to));
    return new Dijkstra<>(graph).findPath(s, to);
  }

  private double heuristic(final AdjGraph.Vertex<LabeledPoint2d> current, final AdjGraph.Vertex<LabeledPoint2d> destination) {
    return current.getValue().distance(destination.getValue());
  }
}
