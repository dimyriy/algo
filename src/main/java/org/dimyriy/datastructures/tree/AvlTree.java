package org.dimyriy.datastructures.tree;

import java.util.NoSuchElementException;

import static java.lang.Math.max;

/**
 * @author Dmitrii Bogdanov
 * Created at 24.07.18
 */
public class AvlTree implements SuccessorAware<AvlTree.Node, AvlTree.Node>, InsertionAware {
  private Node root;

  public AvlTree() {
    this.root = null;
  }

  public Node getRoot() {
    return root;
  }

  public Node findMinimum() {
    return root.findMinimum();
  }

  public Node get(final int key) {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.find(key);
  }

  @Override
  public Node successor(final Node x) {
    if (root == null || x == null) {
      return null;
    } else {
      return x.successor();
    }
  }

  @Override
  public void insert(final int x) {
    if (root == null) {
      this.root = new Node(x);
    } else {
      this.root = insert(root, x);
    }
  }

  boolean isBst() {
    if (root == null) {
      return true;
    } else {
      return root.isBst();
    }
  }

  boolean isBalanced() {
    if (root == null) {
      return true;
    } else {
      return root.isBalanced();
    }
  }

  private boolean isLeftHeavy(final int balance) {
    return balance > 1;
  }

  private boolean isRightHeavy(final int balance) {
    return balance < -1;
  }

  private Node insert(final Node node, final int key) {
    final Node x = insertActualNode(node, key);
    if (x != null) return x;
    return rotateIfUnbalanced(node, key);
  }

  private Node insertActualNode(final Node node, final int key) {
    if (node == null) {
      return new Node(key);
    }
    if (key < node.key) {
      final boolean setParent = node.left == null;
      node.left = insert(node.left, key);
      if (setParent) {
        node.left.parent = node;
      }
    } else if (key > node.key) {
      final boolean setParent = node.right == null;
      node.right = insert(node.right, key);
      if (setParent) {
        node.right.parent = node;
      }
    } else {
      return node;
    }

    node.height = 1 + max(height(node.left), height(node.right));
    return null;
  }

  private Node rotateIfUnbalanced(final Node node, final int key) {
    final int balance = balance(node);
    if (isLeftHeavy(balance) && key < node.left.key) {
      return rotateClockwise(node);
    }

    if (isRightHeavy(balance) && key > node.right.key) {
      return rotateCounterClockwise(node);
    }

    if (isLeftHeavy(balance) && key > node.left.key) {
      node.left = rotateCounterClockwise(node.left);
      return rotateClockwise(node);
    }

    if (isRightHeavy(balance) && key < node.right.key) {
      node.right = rotateClockwise(node.right);
      return rotateCounterClockwise(node);
    }

    return node;
  }

  private Node rotateClockwise(final Node node) {
    final Node newParent = node.left;
    newParent.parent = node.parent;
    final Node tmp = newParent.right;
    newParent.right = node;
    newParent.right.parent = newParent;
    node.left = tmp;
    if (node.left != null) {
      node.left.parent = node;
    }
    if (node.right != null) {
      node.right.parent = node;
    }
    node.height = max(height(node.left), height(node.right)) + 1;
    newParent.height = max(height(newParent.left), height(newParent.right)) + 1;
    return newParent;
  }

  private Node rotateCounterClockwise(final Node node) {
    final Node newParent = node.right;
    newParent.parent = node.parent;
    final Node tmp = newParent.left;
    newParent.left = node;
    newParent.left.parent = newParent;
    node.right = tmp;
    if (node.right != null) {
      node.right.parent = node;
    }
    if (node.left != null) {
      node.left.parent = node;
    }
    node.height = max(height(node.left), height(node.right)) + 1;
    newParent.height = max(height(newParent.left), height(newParent.right)) + 1;
    return newParent;
  }

  private static int height(final Node node) {
    if (node == null)
      return 0;
    return node.height;
  }

  private static int balance(final Node node) {
    if (node == null)
      return 0;
    return height(node.left) - height(node.right);
  }

  public static class Node {
    private final int key;
    private int height;
    private Node left;
    private Node right;
    private Node parent = null;

    Node(final int key) {
      this.key = key;
      this.height = 1;
      this.left = null;
      this.right = null;
    }

    public int getKey() {
      return key;
    }

    public Node getLeft() {
      return left;
    }

    public Node getRight() {
      return right;
    }

    Node find(final int key) {
      if (this.getKey() == key) {
        return this;
      } else if (key < this.getKey()) {
        if (this.left == null) {
          throw new NoSuchElementException();
        }
        return this.getLeft().find(key);
      } else {
        if (this.right == null) {
          throw new NoSuchElementException();
        }
        return this.getRight().find(key);
      }
    }

    Node findMinimum() {
      if (left == null) {
        return this;
      } else {
        return left.findMinimum();
      }
    }

    Node successor() {
      if (right != null) {
        return right.findMinimum();
      } else {
        Node currentParent = this.parent;
        Node current = this;
        while (currentParent != null && current == currentParent.right) {
          current = currentParent;
          currentParent = current.parent;
        }
        return currentParent;
      }
    }

    boolean isBst() {
      if (left == null && right == null) {
        return true;
      } else {
        if (right == null) {
          return left.key < key && left.isBst();
        }
        if (left == null) {
          return right.key > key && right.isBst();
        }
        return left.key < key && right.key > key && left.isBst() && right.isBst();
      }
    }

    boolean isBalanced() {
      final int balance = balance(this);
      if (balance > 1 || balance < -1) {
        return false;
      } else if (left == null && right == null) {
        return true;
      } else if (left == null) {
        return right.isBalanced();
      } else if (right == null) {
        return left.isBalanced();
      } else {
        return left.isBalanced() && right.isBalanced();
      }
    }
  }
}
