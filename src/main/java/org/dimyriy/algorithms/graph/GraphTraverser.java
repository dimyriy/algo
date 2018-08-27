package org.dimyriy.algorithms.graph;

import org.dimyriy.datastructures.graph.AdjGraph;

import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * <p>
 * Created on 16.08.18
 * <p>
 * Copyright (c) 2018, Check24 Vergleichsportal GmbH
 */
interface GraphTraverser<T> {
  List<AdjGraph.Vertex<T>> traverse(final AdjGraph.Vertex<T> s);
}
