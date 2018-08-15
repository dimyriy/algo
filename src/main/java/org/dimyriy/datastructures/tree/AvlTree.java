package org.dimyriy.datastructures.tree;

import static java.lang.Math.max;

/**
 * @author Dmitrii Bogdanov
 * Created at 24.07.18
 */
public class AvlTree implements SuccessorAware, InsertionAware {
  private Node root;

  public AvlTree() {
    this.root = null;
  }

  public Node getRoot() {
    return root;
  }

  @Override public int successor(final int x) {
    return 0;
  }

  @Override public void insert(final int x) {
    add(x);
  }

  void add(final int value) {
    if (root == null) {
      this.root = new Node(value);
    } else {
      this.root = insert(root, value);
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
    if (node == null) {
      return new Node(key);
    }
    if (key < node.key) {
      node.left = insert(node.left, key);
    } else if (key > node.key) {
      node.right = insert(node.right, key);
    } else {
      return node;
    }

    node.height = 1 + max(height(node.left), height(node.right));

    final int balance = balance(node);
    if (isLeftHeavy(balance) && key < node.left.key) {
      return rightRotate(node);
    }

    if (isRightHeavy(balance) && key > node.right.key) {
      return leftRotate(node);
    }

    if (isLeftHeavy(balance) && key > node.left.key) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    if (isRightHeavy(balance) && key < node.right.key) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }

  private Node rightRotate(final Node node) {
    final Node newParent = node.left;
    final Node tmp = newParent.right;
    newParent.right = node;
    node.left = tmp;
    node.height = max(height(node.left), height(node.right)) + 1;
    newParent.height = max(height(newParent.left), height(newParent.right)) + 1;
    return newParent;
  }

  private Node leftRotate(final Node node) {
    final Node newParent = node.right;
    final Node tmp = newParent.left;
    newParent.left = node;
    node.right = tmp;
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
