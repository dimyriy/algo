package org.dimyriy.javapuzzles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
class DeadLockerTest {

  @Test
  void testDeadlockOnLocksBlocksThreadsOnWaiting() {
    final DeadLocker deadLocker = new DeadLocker();
    new Thread(deadLocker::deadlockOnLocks).start();
    while (!deadLocker.isFirstLockLocked() && !deadLocker.isSecondLockLocked()) {
      sleepUninterruptedly(1L);
    }
    sleepAndAssertThreadsState(deadLocker, Thread.State.WAITING);
  }

  @Test
  void testDeadlockOnMonitorsBlocksThreadsOnBlocked() {
    final DeadLocker deadLocker = new DeadLocker();
    new Thread(deadLocker::deadlockOnMonitors).start();
    while (!deadLocker.isFirstLockLocked() && !deadLocker.isSecondLockLocked()) {
      sleepUninterruptedly(1L);
    }
    sleepAndAssertThreadsState(deadLocker, Thread.State.BLOCKED);
  }

  private void sleepAndAssertThreadsState(final DeadLocker deadLocker, final Thread.State threadState) {
    sleepUninterruptedly(1000L);
    Assertions.assertEquals(threadState, deadLocker.getFirstThreadState());
    Assertions.assertEquals(threadState, deadLocker.getSecondThreadState());
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void sleepUninterruptedly(final long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.interrupted();
    }
  }
}