package org.dimyriy.algorithms.sort;

import org.dimyriy.datastructures.tree.veb.VanEmdeBoasTree;
import org.dimyriy.util.NumberUtil;

/**
 * @author Dmitrii Bogdanov
 * Created at 15.08.18
 */
@SuppressWarnings("unused")
public class VanEmdeBoasTreeSuccessorSort extends SuccessorSort<VanEmdeBoasTree> {
  public VanEmdeBoasTreeSuccessorSort() {
    super(problemSize -> VanEmdeBoasTree.create(NumberUtil.nextPowerOfTwo(problemSize)));
  }
}
