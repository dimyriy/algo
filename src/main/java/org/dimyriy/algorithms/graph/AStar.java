package org.dimyriy.algorithms.graph;

import org.dimyriy.common.IterationsNumber;
import org.dimyriy.datastructures.LabeledPoint2d;
import org.dimyriy.datastructures.graph.AdjGraph;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.08.18
 */
public class AStar extends ShortestPathFinder<LabeledPoint2d> implements IterationsNumber {
  private int numberOfEnqueues = 0;

  AStar(@Nonnull final AdjGraph<LabeledPoint2d> graph) {
    super(graph);
  }

  @Override
  public List<AdjGraph.Vertex<LabeledPoint2d>> findPath(final AdjGraph.Vertex<LabeledPoint2d> source, final AdjGraph.Vertex<LabeledPoint2d> target) {
    final ConcurrentMap<AdjGraph.Vertex<LabeledPoint2d>, Double> distanceFromSource = new ConcurrentHashMap<>();
    final PriorityQueue<Node<AdjGraph.Vertex<LabeledPoint2d>>> openQueue = new PriorityQueue<>();
    final Set<Node<AdjGraph.Vertex<LabeledPoint2d>>> closedSet = Collections.newSetFromMap(new HashMap<>());
    final Node<AdjGraph.Vertex<LabeledPoint2d>> sourceNode = new Node<>(source);
    openQueue.add(sourceNode);
    distanceFromSource.put(source, 0d);
    sourceNode.setCost(heuristic(source, target));
    while (!openQueue.isEmpty()) {
      numberOfEnqueues++;
      final Node<AdjGraph.Vertex<LabeledPoint2d>> current = openQueue.poll();
      if (current.getVertex().equals(target)) {
        return reconstructPath(current);
      }
      closedSet.add(current);
      for (final Map.Entry<AdjGraph.Vertex<LabeledPoint2d>, Integer> neighborWithEdge : graph.getNeighborsWithWeights(current.getVertex())) {
        visitNeighbor(target, distanceFromSource, openQueue, closedSet, current, neighborWithEdge);
      }
    }
    return Collections.emptyList();
  }

  @Override
  public int getNumberOfIterations() {
    return numberOfEnqueues;
  }

  private void visitNeighbor(final AdjGraph.Vertex<LabeledPoint2d> target,
                             final ConcurrentMap<AdjGraph.Vertex<LabeledPoint2d>, Double> distanceFromSource,
                             final PriorityQueue<Node<AdjGraph.Vertex<LabeledPoint2d>>> openQueue,
                             final Set<Node<AdjGraph.Vertex<LabeledPoint2d>>> closedSet,
                             final Node<AdjGraph.Vertex<LabeledPoint2d>> current, final Map.Entry<AdjGraph.Vertex<LabeledPoint2d>, Integer> neighborWithEdge) {
    final Node<AdjGraph.Vertex<LabeledPoint2d>> neighbor = new Node<>(neighborWithEdge.getKey());
    if (!closedSet.contains(neighbor)) {
      final int weight = neighborWithEdge.getValue();
      final double tentativeDistanceFromSource = distanceFromSource.get(current.getVertex()) + weight;
      if (tentativeDistanceFromSource < distanceFromSource.getOrDefault(neighbor.getVertex(), Double.MAX_VALUE)) {
        neighbor.setPredecessor(current);
        distanceFromSource.put(neighbor.getVertex(), tentativeDistanceFromSource);
        neighbor.setCost(tentativeDistanceFromSource + heuristic(neighbor.getVertex(), target));
      }
      if (!openQueue.contains(neighbor)) {
        openQueue.remove(neighbor);
        openQueue.add(neighbor);
      }
    }
  }

  private double heuristic(final AdjGraph.Vertex<LabeledPoint2d> current, final AdjGraph.Vertex<LabeledPoint2d> destination) {
    return current.getValue().distance(destination.getValue());
  }
}
