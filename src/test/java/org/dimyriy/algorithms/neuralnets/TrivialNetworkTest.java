package org.dimyriy.algorithms.neuralnets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 27.07.18
 */
@SuppressWarnings("UnnecessaryLocalVariable")
class TrivialNetworkTest {
  private static final int MEANINGFUL_MAX_ITERATION_NUMBER = TrivialNetwork.MEANINGFUL_MAX_ITERATION_NUMBER;

  @DisplayName(" ╯°□°）╯`")
  @Test
  void testNeuralNetwork() {
    final double errorThreshold = 1E-12;
    final double resultErrorThreshold = 1E-5;
    final TrivialNetwork trivialNetwork = new TrivialNetwork();
    final double x = 1.0;
    final double d = x;
    trivialNetwork.train(x, d, errorThreshold, 2);
    final double z = trivialNetwork.solve(x);
    Assertions.assertEquals(d, z, resultErrorThreshold, "Error less than threshold" + resultErrorThreshold);
    Assertions.assertTrue(trivialNetwork.getNumberOfIterations() < MEANINGFUL_MAX_ITERATION_NUMBER - 1,
                          "Training takes less than meaningful number of iterations");
  }

}