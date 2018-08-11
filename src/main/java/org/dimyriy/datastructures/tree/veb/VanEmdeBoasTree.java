package org.dimyriy.datastructures.tree.veb;

import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * <p>
 * Created on 09.08.18
 */
class VanEmdeBoasTree {
  private static final int MIN_UNIVERSE_SIZE = 1 << 1;
  private static final int MAX_UNIVERSE_SIZE = 1 << 30;
  private static final int NULL = Integer.MIN_VALUE;
  private final int lowMask;
  private final int highShift;
  private final int size;
  private final int squareRootOfUniverseSize;
  @SuppressWarnings("PackageVisibleField")
  int min = Integer.MIN_VALUE;
  @SuppressWarnings("PackageVisibleField")
  int max = NULL;
  private VanEmdeBoasTree[] clusters = null;
  private VanEmdeBoasTree summary = null;

  private VanEmdeBoasTree(final int size) {
    if (size < MIN_UNIVERSE_SIZE) {
      throw new IllegalArgumentException("Cannot create tree of universe size smaller then " + MIN_UNIVERSE_SIZE);
    }
    if (!NumberUtil.isPowerOfTwo(size)) {
      throw new IllegalArgumentException("Universe can only be a power of two size");
    }
    if (size > MAX_UNIVERSE_SIZE) {
      throw new IllegalArgumentException("Universe cannot be larger than " + MAX_UNIVERSE_SIZE);
    }
    this.size = size;
    this.squareRootOfUniverseSize = size >> 1;
    this.lowMask = this.squareRootOfUniverseSize - 1;
    this.highShift = Integer.numberOfTrailingZeros(this.squareRootOfUniverseSize);
  }

  public void insert(final int x) {
    guard(x);
    if (min == NULL) {
      insertIntoEmptyTree(x);
    } else {
      insertIntoNonEmptyTree(x);
    }
  }

  public int successor(final int x) {
    guard(x);
    return 0;
  }

  protected void insertIntoNonEmptyTree(int x) {
    if (x != min) {
      if (x < min) {
        final int tmp = x;
        x = min;
        min = tmp;
      }
      final int clusterIndex = high(x);
      insertIntoCluster(x, clusterIndex);
      if (x > max) {
        max = x;
      }
    }
  }

  protected void createSummaryIfNecessary() {
    if (summary == null) {
      summary = VanEmdeBoasTree.create(squareRootOfUniverseSize);
    }
  }

  protected void createClusterAndSummaryIfNecessary(final int clusterIndex) {
    if (clusters == null) {
      clusters = new VanEmdeBoasTree[squareRootOfUniverseSize];
    }
    if (clusters[clusterIndex] == null) {
      clusters[clusterIndex] = VanEmdeBoasTree.create(squareRootOfUniverseSize);
      createSummaryIfNecessary();
      summary.insert(clusterIndex);
    }
  }

  private int high(final int x) {
    return x >>> highShift;
  }

  private int low(final int x) {
    return x & lowMask;
  }

  private void insertIntoEmptyTree(final int x) {
    this.min = x;
    this.max = x;
  }

  private void insertIntoCluster(final int x, final int clusterIndex) {
    createClusterAndSummaryIfNecessary(clusterIndex);
    final VanEmdeBoasTree cluster = clusters[clusterIndex];
    final int clusterMin = cluster.min;
    if (clusterMin == NULL) {
      cluster.insertIntoEmptyTree(x);
    } else {
      cluster.insert(x);
    }
  }

  static VanEmdeBoasTree create(final int size) {
    if (size == MIN_UNIVERSE_SIZE) {
      return new VanEmdeBoasTreeOfMinUniverseSize();
    } else {
      return new VanEmdeBoasTree(size);
    }
  }

  private static void guard(final int x) {
    if (x < 0) {
      throw new IllegalArgumentException("Negative integers are not allowed");
    } else if (x > MAX_UNIVERSE_SIZE) {
      throw new IllegalArgumentException("Values larger than universe size are not allowed");
    }
  }

  static class VanEmdeBoasTreeOfMinUniverseSize extends VanEmdeBoasTree {
    VanEmdeBoasTreeOfMinUniverseSize() {
      super(2);
    }

    @Override public int successor(final int x) {
      if (x < min) {
        return min;
      } else if (x > max) {
        return NULL;
      } else {
        return max;
      }
    }

    @Override protected void insertIntoNonEmptyTree(final int x) {
      if (x < min) {
        max = min;
        min = x;
      } else {
        if (x != min) {
          max = x;
        }
      }
    }

    @Override protected void createSummaryIfNecessary() {
      //do nothing
    }

    @Override protected void createClusterAndSummaryIfNecessary(final int clusterIndex) {
      //do nothing
    }
  }
}
