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
  private final PriorityQueue<Dijkstra.Node<AdjGraph.Vertex<LabeledPoint2d>>> shortestNodesQueue = new PriorityQueue<>();
  private final Map<Dijkstra.Node<AdjGraph.Vertex<LabeledPoint2d>>, Double> gScore = new ConcurrentHashMap<>();
  private final Map<Dijkstra.Node<AdjGraph.Vertex<LabeledPoint2d>>, Double> fScore = new ConcurrentHashMap<>();

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
    final Dijkstra.Node<AdjGraph.Vertex<LabeledPoint2d>> source = new Dijkstra.Node<>(s);
    gScore.put(source, 0d);
    fScore.put(source, heuristic(s, to));
    while (true) {
      return null;
    }
  }

  private double heuristic(final AdjGraph.Vertex<LabeledPoint2d> current, final AdjGraph.Vertex<LabeledPoint2d> destination) {
    return current.getValue().distance(destination.getValue());
  }

  private List<AdjGraph.Vertex<LabeledPoint2d>> reconstructPath(Dijkstra.Node<AdjGraph.Vertex<LabeledPoint2d>> currentNode) {
    final List<AdjGraph.Vertex<LabeledPoint2d>> path = new ArrayList<>();
    path.add(currentNode.getVertex());
    while (currentNode.getPredecessor() != null) {
      path.add(currentNode.getPredecessor().getVertex());
      currentNode = currentNode.getPredecessor();
    }
    Collections.reverse(path);
    return path;
  }
}
