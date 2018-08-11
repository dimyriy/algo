package org.dimyriy.datastructures.graph;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
public class AdjGraph<T> {
  private final Map<Vertex<T>, Map<Vertex<T>, Integer>> adjacencyList = new ConcurrentHashMap<>();

  public Set<Vertex<T>> allVertices() {
    return adjacencyList.keySet();
  }

  public void addNeighbor(@Nonnull final Vertex<T> v1, @Nonnull final Vertex<T> v2, final int weight) {
    final Map<Vertex<T>, Integer> neighborsOfV1 = adjacencyList.computeIfAbsent(v1, vertex -> new ConcurrentHashMap<>());
    final Map<Vertex<T>, Integer> neighborsOfV2 = adjacencyList.computeIfAbsent(v2, vertex -> new ConcurrentHashMap<>());
    neighborsOfV1.put(v2, weight);
    neighborsOfV2.put(v1, weight);
  }

  public Collection<Vertex<T>> getNeighbors(@Nonnull final Vertex<T> v) {
    final Map<Vertex<T>, Integer> vertexIntegerMap = adjacencyList.get(v);
    return vertexIntegerMap == null ? Collections.emptyList() : vertexIntegerMap.keySet();
  }

  public Collection<Map.Entry<Vertex<T>, Integer>> getNeighborsWithWeights(@Nonnull final Vertex<T> v) {
    final Map<Vertex<T>, Integer> vertexIntegerMap = adjacencyList.get(v);
    return vertexIntegerMap == null ? Collections.emptyList() : vertexIntegerMap.entrySet();
  }

  @Immutable
  public static class Vertex<T> {
    private final T value;

    public Vertex(final T value) {
      this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof Vertex)) return false;

      final Vertex<?> vertex = (Vertex<?>) o;

      return value.equals(vertex.value);
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }

    @Override
    public String toString() {
      return value.toString();
    }
  }
}
