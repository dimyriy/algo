package org.dimyriy.javapuzzles;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
@SuppressWarnings("PackageVisibleField")
class DeadLocker {
  final AtomicBoolean isFirstLockLocked = new AtomicBoolean(false);
  final AtomicBoolean isSecondLockLocked = new AtomicBoolean(false);
  final Thread firstThread;
  final Thread secondThread;
  private final boolean loggingEnabled;
  private final NamedReentrantLock firstLock = new NamedReentrantLock("A");
  private final NamedReentrantLock secondLock = new NamedReentrantLock("B");

  DeadLocker(final boolean enableLogging) {
    this.loggingEnabled = enableLogging;
    firstThread = new Thread(() -> {
      log("...First thread started...");
      lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(firstLock,
                                                                      secondLock,
                                                                      isFirstLockLocked,
                                                                      isSecondLockLocked);
      log("...First thread finished.");
    });
    secondThread = new Thread(() -> {
      log("...First thread started...");
      lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(secondLock,
                                                                      firstLock,
                                                                      isSecondLockLocked,
                                                                      isFirstLockLocked);
      log("...Second thread finished.");
    });
  }

  void deadlock() {
    log("Start...");
    firstThread.start();
    secondThread.start();
  }

  private void log(final String... strings) {
    if (loggingEnabled) {
      System.out.println(String.join("", strings));
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private void lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(final NamedReentrantLock first,
                                                                               final NamedReentrantLock second,
                                                                               final AtomicBoolean setCondition,
                                                                               final AtomicBoolean checkCondition) {
    log("...Acquiring lock ", first.getName(), " started...");
    first.lock();
    try {
      log("...Acquiring lock ", first.getName(), " finished...");
      log("...Setting the condition for lock ", first.getName(), " is started...");
      setCondition.set(true);
      log("...Setting the condition for lock ", first.getName(), " is finished...");
      log("...Waiting on condition for lock ", second.getName(), " to be acquired...");
      while (!checkCondition.get()) {
        noop();
      }
      log("...Waiting on condition for lock ", second.getName(), " to be acquired finished...");
      log("...Acquiring lock ", second.getName(), " started...");
      second.lock();
      log("...Acquiring lock ", second.getName(), " finished...");
    } finally {
      log("...Unlocking lock ", first.getName(), " started...");
      first.unlock();
      log("...Unlocking lock ", first.getName(), " finished...");
    }
  }

  private static void noop() {
  }

  private static class NamedReentrantLock extends ReentrantLock {
    private final String name;

    NamedReentrantLock(final String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }
}
