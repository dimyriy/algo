package org.dimyriy.datastructures.tree.veb;

import org.dimyriy.datastructures.tree.DeletionAware;
import org.dimyriy.datastructures.tree.InsertionAware;
import org.dimyriy.datastructures.tree.SuccessorAware;
import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * <p>
 * Created on 09.08.18
 */
public class VanEmdeBoasTree implements SuccessorAware<Integer, Integer>, InsertionAware, DeletionAware {
  static final Integer NULL = Integer.MIN_VALUE;
  private static final int MIN_UNIVERSE_SIZE = 1 << 1;
  private static final int MAX_UNIVERSE_SIZE = 1 << 30;
  private final int lowMask;
  private final int highShift;
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
    this.squareRootOfUniverseSize = size >> 1;
    this.lowMask = this.squareRootOfUniverseSize - 1;
    this.highShift = Integer.numberOfTrailingZeros(this.squareRootOfUniverseSize);
  }

  @Override
  public void insert(final int x) {
    guard(x);
    if (min == NULL) {
      insertIntoEmptyTree(x);
    } else {
      insertIntoNonEmptyTree(x);
    }
  }

  @Override
  public Integer successor(final Integer x) {
    if (min != NULL && x < min) {
      return min;
    } else {
      if (clusters == null || summary == null) {
        return NULL;
      }
      final int currentClusterIndex = high(x);
      final VanEmdeBoasTree currentCluster = clusters[currentClusterIndex];
      if (currentCluster != null && low(x) < currentCluster.max) {
        return index(currentClusterIndex, currentCluster.successor(low(x)));
      }
      final int successorClusterIndex = summary.successor(currentClusterIndex);
      if (successorClusterIndex == NULL || clusters[successorClusterIndex] == null) {
        return NULL;
      }
      return index(successorClusterIndex, clusters[successorClusterIndex].min);
    }
  }

  @Override public void delete(int x) {
    guard(x);
    if (min == max) {
      min = NULL;
      max = NULL;
      return;
    }

    if (min == x) {
      final int firstCluster = summary.min;
      x = index(firstCluster, clusters[firstCluster].min);
      min = x;
    }
    clusters[high(x)].delete(low(x));
    if (clusters[high(x)].min == NULL) {
      summary.delete(high(x));
      if (x == max) {
        final int summaryMax = summary.max;
        if (summaryMax == NULL) {
          max = min;
        } else {
          max = index(summaryMax, clusters[summaryMax].max);
        }
      }
    } else if (x == max) {
      max = index(high(x), clusters[high(x)].max);
    }
  }

  void insertIntoNonEmptyTree(int x) {
    if (x != min && x != max) {
      if (x < min) {
        final int tmp = x;
        x = min;
        min = tmp;
      }
      final int clusterIndex = high(x);
      insertIntoCluster(clusterIndex, low(x));
      if (x > max) {
        max = x;
      }
    }
  }

  void createSummaryIfNecessary() {
    if (summary == null) {
      summary = VanEmdeBoasTree.create(squareRootOfUniverseSize);
    }
  }

  void createClusterAndSummaryIfNecessary(final int clusterIndex) {
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

  private int index(final int x, final int y) {
    return (x << highShift) | (y & lowMask);
  }

  private void insertIntoEmptyTree(final int x) {
    this.min = x;
    this.max = x;
  }

  private void insertIntoCluster(final int clusterIndex, final int elementIndex) {
    createClusterAndSummaryIfNecessary(clusterIndex);
    final VanEmdeBoasTree cluster = clusters[clusterIndex];
    final int clusterMin = cluster.min;
    if (clusterMin == NULL) {
      cluster.insertIntoEmptyTree(elementIndex);
    } else {
      cluster.insert(elementIndex);
    }
  }

  public static VanEmdeBoasTree create(final int size) {
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

    @Override public Integer successor(final Integer x) {
      if (x < min) {
        return min;
      } else if (x < max) {
        return max;
      } else {
        return NULL;
      }
    }

    @Override public void delete(final int x) {
      if (max == min) {
        max = NULL;
        min = NULL;
      } else {
        if (x == 0) {
          min = 1;
        } else {
          max = 0;
        }
        max = min;
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
