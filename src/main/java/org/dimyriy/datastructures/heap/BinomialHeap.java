package org.dimyriy.datastructures.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 07.08.18
 */
@SuppressWarnings("unused")
class BinomialHeap {
  private final List<Node> roots = new ArrayList<>();

  private static class Node {
    private int rank;
  }
}
