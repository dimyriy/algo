package org.dimyriy.datastructures;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
public class AdjGraph<T> {
  private final Map<Vertex<T>, List<Vertex<T>>> adjacencyList = new ConcurrentHashMap<>();

  public void addNeighbor(@Nonnull final Vertex<T> v1, @Nonnull final Vertex<T> v2) {
    final List<Vertex<T>> neighborsOfV1 = adjacencyList.computeIfAbsent(v1, vertex -> new ArrayList<>());
    final List<Vertex<T>> neighborsOfV2 = adjacencyList.computeIfAbsent(v2, vertex -> new ArrayList<>());
    neighborsOfV1.add(v2);
    neighborsOfV2.add(v1);
  }

  public List<Vertex<T>> getNeighbors(@Nonnull final Vertex<T> v) {
    return adjacencyList.get(v);
  }

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
