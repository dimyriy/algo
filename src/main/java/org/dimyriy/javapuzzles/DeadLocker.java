package org.dimyriy.javapuzzles;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
@SuppressWarnings("PackageVisibleField")
class DeadLocker {
  private final ThreadPair threadPair;
  private final Context firstContext;
  private final Context secondContext;


  DeadLocker(final boolean enableLogging) {
    Configuration.INSTANCE = new Configuration(enableLogging);
    final AtomicBoolean isSecondLockLocked = new AtomicBoolean(false);
    final AtomicBoolean isFirstLockLocked = new AtomicBoolean(false);
    final NamedReentrantLock firstLock = new NamedReentrantLock("A");
    final NamedReentrantLock secondLock = new NamedReentrantLock("B");
    firstContext = new Context(firstLock, secondLock, isFirstLockLocked, isSecondLockLocked);
    secondContext = new Context(secondLock, firstLock, isSecondLockLocked, isFirstLockLocked);
    threadPair = new ThreadPair();
  }

  void deadlockOnLocks() {
    log("Start...");
    threadPair.initializeFirstThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(firstContext));
    threadPair.initializeSecondThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(secondContext));
    threadPair.startThreads();
  }

  void deadlockOnMonitors() {
    log("Start...");
    threadPair.initializeFirstThreadWithRunnable(() -> blockFirstBLockSetConditionWaitForCheckConditionAndBLockSecondBLock(firstContext));
    threadPair.initializeSecondThreadWithRunnable(() -> blockFirstBLockSetConditionWaitForCheckConditionAndBLockSecondBLock(secondContext));
    threadPair.startThreads();
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  boolean isFirstLockLocked() {
    return firstContext.setCondition.get();
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  boolean isSecondLockLocked() {
    return secondContext.setCondition.get();
  }

  Thread.State getFirstThreadState() {
    return threadPair.firstThreadState();
  }

  Thread.State getSecondThreadState() {
    return threadPair.secondThreadState();
  }

  @SuppressWarnings({"StatementWithEmptyBody", "SynchronizationOnLocalVariableOrMethodParameter"})
  private void blockFirstBLockSetConditionWaitForCheckConditionAndBLockSecondBLock(final Context context) {
    log("...Entering monitor ", context.getFirst().getName(), " started...");
    synchronized (context.getFirst()) {
      log("...Entering monitor ", context.getFirst().getName(), " finished...");
      log("...Setting the entered condition for monitor ", context.getFirst().getName(), " is started...");
      context.getSetCondition().set(true);
      log("...Setting the entered condition for monitor ", context.getFirst().getName(), " is finished...");
      log("...Waiting on condition for monitor ", context.getSecond().getName(), " to be entered...");
      while (!context.getCheckCondition().get()) {
        noop();
      }
      log("...Waiting on condition for monitor ", context.getSecond().getName(), " to be entered...");
      log("...Entering monitor ", context.getSecond().getName(), " started...");
      synchronized (context.getSecond()) {
        log("...Entering monitor ", context.getSecond().getName(), " finished...");
      }
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private void lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(final Context context) {
    log("...Acquiring lock ", context.first.getName(), " started...");
    context.first.lock();
    try {
      log("...Acquiring lock ", context.first.getName(), " finished...");
      log("...Setting the condition for lock ", context.first.getName(), " is started...");
      context.setCondition.set(true);
      log("...Setting the condition for lock ", context.first.getName(), " is finished...");
      log("...Waiting on condition for lock ", context.second.getName(), " to be acquired...");
      while (!context.checkCondition.get()) {
        noop();
      }
      log("...Waiting on condition for lock ", context.second.getName(), " to be acquired finished...");
      log("...Acquiring lock ", context.second.getName(), " started...");
      context.second.lock();
      log("...Acquiring lock ", context.second.getName(), " finished...");
    } finally {
      log("...Unlocking lock ", context.first.getName(), " started...");
      context.first.unlock();
      log("...Unlocking lock ", context.first.getName(), " finished...");
    }
  }

  private static void log(final String... strings) {
    if (Configuration.isLoggingEnabled()) {
      System.out.println(String.join("", strings));
    }
  }

  private static void noop() {
  }

  private static class ThreadPair {
    private final AtomicReference<Thread> first = new AtomicReference<>();
    private final AtomicReference<Thread> second = new AtomicReference<>();

    void startThreads() {
      first.get().start();
      second.get().start();
    }

    void initializeFirstThreadWithRunnable(final Runnable runnable) {
      initializeThreadWithRunnable(first, runnable, "first");
    }

    void initializeSecondThreadWithRunnable(final Runnable runnable) {
      initializeThreadWithRunnable(second, runnable, "second");
    }

    void initializeThreadWithRunnable(final AtomicReference<Thread> threadReference, final Runnable runnable, final String name) {
      threadReference.set(new Thread(() -> {
        log("...thread for ", name, " started...");
        runnable.run();
        log("...thread for ", name, " finished...");
      }));
    }

    Thread.State firstThreadState() {
      return first.get().getState();
    }

    Thread.State secondThreadState() {
      return second.get().getState();
    }
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

  private static class Configuration {
    private static Configuration INSTANCE = new Configuration(false);
    private final boolean isLoggingEnabled;

    Configuration(final boolean isLoggingEnabled) {
      this.isLoggingEnabled = isLoggingEnabled;
    }

    static boolean isLoggingEnabled() {
      return INSTANCE.isLoggingEnabled;
    }
  }

  private static class Context {
    private final NamedReentrantLock first;
    private final NamedReentrantLock second;
    private final AtomicBoolean setCondition;
    private final AtomicBoolean checkCondition;

    Context(final NamedReentrantLock first,
            final NamedReentrantLock second,
            final AtomicBoolean setCondition,
            final AtomicBoolean checkCondition) {
      this.first = first;
      this.second = second;
      this.setCondition = setCondition;
      this.checkCondition = checkCondition;
    }

    NamedReentrantLock getFirst() {
      return first;
    }

    NamedReentrantLock getSecond() {
      return second;
    }

    AtomicBoolean getSetCondition() {
      return setCondition;
    }

    AtomicBoolean getCheckCondition() {
      return checkCondition;
    }
  }
}
