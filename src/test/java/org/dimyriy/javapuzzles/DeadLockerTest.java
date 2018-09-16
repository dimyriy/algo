package org.dimyriy.javapuzzles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class DeadLockerTest {

  @Test
  void testDeadlockBlocksThreadsOnWaiting() throws InterruptedException {
    final DeadLocker deadLocker = new DeadLocker(false);
    new Thread(deadLocker::deadlock).start();
    while (!deadLocker.isFirstLockLocked.get() && !deadLocker.isSecondLockLocked.get()) {
      Thread.sleep(1L);
    }
    Thread.sleep(1000L);
    Assertions.assertEquals(Thread.State.WAITING, deadLocker.firstThread.getState());
    Assertions.assertEquals(Thread.State.WAITING, deadLocker.secondThread.getState());
  }
}