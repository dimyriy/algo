package org.dimyriy.algorithms.tree;

import org.dimyriy.datastructures.tree.AvlTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class LowestCommonAncestorTest {

  @Test
  void findLCAInEmptyTreeThrowsException() {
    Assertions.assertThrows(NoSuchElementException.class, () -> new LowestCommonAncestor(new AvlTree()).find(1, 2));
  }

  @Test
  void findLCAInAOneNodeTreeReturnsRoot() {
    final AvlTree tree = new AvlTree();
    tree.insert(29);
    Assertions.assertEquals(tree.get(29), new LowestCommonAncestor(tree).find(29, 29));
  }

  @Test
  void findLCAForMissingFirstNodeInATreeThrowsException() {
    final AvlTree tree = createThreeNodeTree();
    Assertions.assertThrows(NoSuchElementException.class, () -> new LowestCommonAncestor(tree).find(27, 17));
  }

  @Test
  void findLCAForMissingSecondNodeInATreeThrowsException() {
    final AvlTree tree = createThreeNodeTree();
    Assertions.assertThrows(NoSuchElementException.class, () -> new LowestCommonAncestor(tree).find(17, 27));
  }

  @Test
  void findLCAForBothMissingNodesInATreeThrowsException() {
    final AvlTree tree = createThreeNodeTree();
    Assertions.assertThrows(NoSuchElementException.class, () -> new LowestCommonAncestor(tree).find(12, 27));
  }

  @Test
  void findLCAInATreeNodeTreeWithOnlySearchedChildrenReturnsRoot() {
    final AvlTree tree = createThreeNodeTree();
    Assertions.assertEquals(tree.get(29), new LowestCommonAncestor(tree).find(17, 35));
  }

  @Test
  void findLCAInAMultiNodeTreeContainingSearchedChildrenReturnsCorrectLCA() {
    final AvlTree tree = new AvlTree();
    tree.insert(29);
    tree.insert(21);
    tree.insert(25);
    tree.insert(11);
    tree.insert(9);
    tree.insert(17);
    tree.insert(35);
    tree.insert(39);
    tree.insert(31);
    Assertions.assertEquals(tree.get(11).getKey(), new LowestCommonAncestor(tree).find(9, 11).getKey());
    Assertions.assertEquals(tree.get(11).getKey(), new LowestCommonAncestor(tree).find(9, 17).getKey());
    Assertions.assertEquals(tree.get(29).getKey(), new LowestCommonAncestor(tree).find(25, 39).getKey());
    Assertions.assertEquals(tree.get(29).getKey(), new LowestCommonAncestor(tree).find(39, 25).getKey());
    Assertions.assertEquals(tree.get(21).getKey(), new LowestCommonAncestor(tree).find(17, 31).getKey());
  }

  private AvlTree createThreeNodeTree() {
    final AvlTree tree = new AvlTree();
    tree.insert(29);
    tree.insert(17);
    tree.insert(35);
    return tree;
  }
}