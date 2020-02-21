package org.dimyriy.failing;

import org.dimyriy.algorithms.tree.LowestCommonAncestor;
import org.dimyriy.datastructures.tree.AvlTree;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FailingTest {
    @Test
    public void failRightThere() {
        final AvlTree tree = new AvlTree();
        tree.insert(29);
        Assertions.assertNotEquals(tree.get(29), new LowestCommonAncestor(tree).find(29, 29));
    }
}
