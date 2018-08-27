package org.dimyriy.datastructures.tree.aux;

import org.dimyriy.datastructures.tree.AvlTree;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dmitrii Bogdanov
 * Created at 29.07.18
 */
@SuppressWarnings("unused")
class AvlTreePrinter {
  static void print(@Nonnull final AvlTree tree, @Nonnull final PrintStream printStream) {
    final int maxLevel = maxLevel(tree.getRoot());
    printNodeInternal(Collections.singletonList(tree.getRoot()), 1, maxLevel, printStream);
  }

  private static void printNodeInternal(@Nonnull final List<AvlTree.Node> nodes, final int level, final int maxLevel, @Nonnull final PrintStream out) {
    if (nodes.isEmpty() || nodes.stream().noneMatch(Objects::nonNull))
      return;

    final int floor = maxLevel - level;
    final int edgeLines = 1 << (Math.max(floor - 1, 0));
    final int firstSpaces = 1 << floor - 1;
    final int betweenSpaces = 1 << (floor + 1) - 1;

    printWhitespaces(firstSpaces, out);

    final List<AvlTree.Node> newNodes = new ArrayList<>();
    for (final AvlTree.Node node : nodes) {
      if (node != null) {
        out.print(node.getKey());
        newNodes.add(node.getLeft());
        newNodes.add(node.getRight());
      } else {
        newNodes.add(null);
        newNodes.add(null);
        out.print(" ");
      }

      printWhitespaces(betweenSpaces, out);
    }
    out.println();

    for (int i = 1; i <= edgeLines; i++) {
      for (final AvlTree.Node node : nodes) {
        printWhitespaces(firstSpaces - i, out);
        if (node == null) {
          printWhitespaces(edgeLines + edgeLines + i + 1, out);
          continue;
        }

        if (node.getLeft() != null)
          out.print("/");
        else
          printWhitespaces(1, out);

        printWhitespaces(i + i - 1, out);

        if (node.getRight() != null)
          out.print("\\");
        else
          printWhitespaces(1, out);

        printWhitespaces(edgeLines + edgeLines - i, out);
      }

      out.println();
    }

    printNodeInternal(newNodes, level + 1, maxLevel, out);
  }

  private static void printWhitespaces(final int count, @Nonnull final PrintStream out) {
    for (int i = 0; i < count; i++)
      out.print(" ");
  }

  private static int maxLevel(final AvlTree.Node node) {
    if (node == null)
      return 0;

    return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
  }
}
