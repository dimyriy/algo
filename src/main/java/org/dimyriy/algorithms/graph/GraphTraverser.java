package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import java.util.List;

/**
 * @author Dmitrii Bogdanov
 *
 * Created on 16.08.18
 *
 * Copyright (c) 2018, Check24 Vergleichsportal GmbH
 */
public interface GraphTraverser<T> {
  List<AdjGraph.Vertex<T>> traverse(final AdjGraph.Vertex<T> s);
}
