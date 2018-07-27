package org.dimyriy.algorithms.neuralnets;

import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 27.07.18
 */
@SuppressWarnings({"WeakerAccess", "UnnecessaryLocalVariable"})
public class TrivialNetwork {
  public static final int MEANINGFUL_MAX_ITERATION_NUMBER = 1000_000;
  private final double[] weights;
  private int numberOfIterations;

  TrivialNetwork() {
    this.weights = new double[2];
    final Random random = new Random();
    this.weights[0] = 1. / (1. + Math.abs(random.nextDouble()));
    this.weights[1] = 1. / (1. + Math.abs(random.nextDouble()));
  }

  public void train(final double x, final double d, final double errorThreshold, final double rate) {
    for (numberOfIterations = 0; numberOfIterations < MEANINGFUL_MAX_ITERATION_NUMBER; numberOfIterations++) {
      final double y = y(x);
      final double z = z(y);
      if (-errorThreshold < error(d, z)) {
        break;
      } else {
        final double dp2dw2 = y;
        final double dp1dw1 = x;
        final double dydp1 = y * (1 - y);
        final double dp2dy = weights[1];
        final double dzdp2 = z * (1 - z);
        final double dpdz = d - z;
        final double dPdw1 = dp1dw1 * dydp1 * dp2dy * dzdp2 * dpdz;
        final double dPdw2 = dp2dw2 * dzdp2 * dpdz;
        weights[0] += weights[0] * dPdw1 * rate;
        weights[1] += weights[1] * dPdw2 * rate;
      }
    }
  }

  public double solve(final double x) {
    return z(y(x));
  }

  private double error(final double d, final double z) {
    final double dMinusZ = d - z;
    return -0.5 * dMinusZ * dMinusZ;
  }

  private double sigmaFunction(final double alpha) {
    return 1. / (1. + Math.exp(-alpha));
  }

  private double z(final double y) {
    return weights[1] * sigmaFunction(y);
  }

  private double y(final double x) {
    return weights[0] * sigmaFunction(x);
  }

  public int getNumberOfIterations() {
    return numberOfIterations;
  }
}
