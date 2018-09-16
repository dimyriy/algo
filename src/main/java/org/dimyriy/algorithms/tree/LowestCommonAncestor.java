package org.dimyriy.algorithms.tree;

import org.dimyriy.datastructures.tree.AvlTree;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class LowestCommonAncestor {
  private final AvlTree tree;

  LowestCommonAncestor(final AvlTree tree) {
    this.tree = tree;
  }

  AvlTree.Node find(final int n1, final int n2) {
    if (n1 == n2) {
      return tree.get(n1);
    }
    final AvlTree.Node node1 = tree.get(Math.min(n1, n2));
    final AvlTree.Node node2 = tree.get(Math.max(n1, n2));
    return find(node1, node2, tree.getRoot());
  }

  private AvlTree.Node find(final AvlTree.Node n1, final AvlTree.Node n2, final AvlTree.Node currentNode) {
    if (currentNode == null) {
      return null;
    }
    if ((n1.getKey() == currentNode.getKey() && n2.getKey() >= currentNode.getKey()) ||
        (n1.getKey() <= currentNode.getKey() && n2.getKey() == currentNode.getKey()) ||
        (n1.getKey() <= currentNode.getKey() && n2.getKey() >= currentNode.getKey())) {
      return currentNode;
    } else if (n2.getKey() < currentNode.getKey()) {
      return find(n1, n2, currentNode.getLeft());
    } else if (n1.getKey() > currentNode.getKey()) {
      return find(n1, n2, currentNode.getRight());
    } else {
      return null;
    }
  }
}
