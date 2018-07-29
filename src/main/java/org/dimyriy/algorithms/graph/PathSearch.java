package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.AdjGraph;

import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
public interface PathSearch<T> {
  List<AdjGraph.Vertex<T>> searchPath(final AdjGraph.Vertex<T> s, final AdjGraph.Vertex<T> to);
  List<AdjGraph.Vertex<T>> traverse(final AdjGraph.Vertex<T> s);
}
