package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
public interface GraphPathFinder<T> {
  List<AdjGraph.Vertex<T>> findPath(final AdjGraph.Vertex<T> s, final AdjGraph.Vertex<T> to);
}
