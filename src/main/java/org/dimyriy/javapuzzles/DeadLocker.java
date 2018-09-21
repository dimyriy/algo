package org.dimyriy.javapuzzles;

import org.dimyriy.aux.SystemOutLogger;

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


  DeadLocker() {
    final AtomicBoolean isSecondLockLocked = new AtomicBoolean(false);
    final AtomicBoolean isFirstLockLocked = new AtomicBoolean(false);
    final NamedReentrantLock firstLock = new NamedReentrantLock("A");
    final NamedReentrantLock secondLock = new NamedReentrantLock("B");
    firstContext = new Context(firstLock, secondLock, isFirstLockLocked, isSecondLockLocked);
    secondContext = new Context(secondLock, firstLock, isSecondLockLocked, isFirstLockLocked);
    threadPair = new ThreadPair();
  }

  void deadlockOnLocks() {
    LogSupport.logStart();
    threadPair.initializeFirstThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(firstContext));
    threadPair.initializeSecondThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(secondContext));
    threadPair.startThreads();
  }

  void deadlockOnMonitors() {
    LogSupport.logStart();
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
    LogSupport.logEnteringMonitorStarted(context.getFirst());
    synchronized (context.getFirst()) {
      LogSupport.logEnteringMonitorFinished(context.getFirst());
      LogSupport.logSettingEnteredConditionForMonitorStarted(context.getFirst());
      context.getSetCondition().set(true);
      LogSupport.logSettingEnteredConditionForMonitorFinished(context.getFirst());
      LogSupport.logWaitingOnConditionForMonitorStarted(context.getSecond());
      while (!context.getCheckCondition().get()) {
        noop();
      }
      LogSupport.logWaitingOnConditionForMonitorFinished(context.getSecond());
      LogSupport.logEnteringMonitorStarted(context.getSecond());
      synchronized (context.getSecond()) {
        LogSupport.logEnteringMonitorFinished(context.getSecond());
      }
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private void lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(final Context context) {
    LogSupport.logAcquiringLockStarted(context.getFirst());
    context.getFirst().lock();
    try {
      LogSupport.logAcquiringLockFinished(context.getFirst());
      LogSupport.logSettingConditionForLockStarted(context.getFirst());
      context.setCondition.set(true);
      LogSupport.logSettingConditionForLockFinished(context.getFirst());
      LogSupport.logWaitingOnConditionForLockStarted(context.getSecond());
      while (!context.checkCondition.get()) {
        noop();
      }
      LogSupport.logWaitingOnConditionForLockFinished(context.getSecond());
      LogSupport.logAcquiringLockStarted(context.getSecond());
      context.getSecond().lock();
      LogSupport.logAcquiringLockFinished(context.getSecond());
    } finally {
      LogSupport.logUnlockingLockStarted(context.getFirst());
      context.getFirst().unlock();
      LogSupport.logUnlockingLockFinished(context.getFirst());
    }
  }

  private static void noop() {
    // Do nothing is intended
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
        LogSupport.logThreadStarted(name);
        runnable.run();
        LogSupport.logThreadFinished(name);
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

  private static class LogSupport {
    private static final String ACQUIRING_LOCK = "...Acquiring lock ";
    private static final String ENTERING_MONITOR = "...Entering monitor ";
    private static final String SETTING_THE_CONDITION_FOR_LOCK = "...Setting the condition for lock ";
    private static final String WAITING_ON_CONDITION_FOR_LOCK = "...Waiting on condition for lock ";
    private static final String UNLOCKING_LOCK = "...Unlocking lock ";
    private static final String STARTED = " started...";
    private static final String FINISHED = " finished...";
    private static final String TO_BE_ACQUIRED = " to be acquired ";
    private static final String SETTING_THE_ENTERED_CONDITION_FOR_MONITOR = "...Setting the entered condition for monitor ";
    private static final String WAITING_ON_CONDITION_FOR_MONITOR = "...Waiting on condition for monitor ";
    private static final String TO_BE_ENTERED = " to be entered ";
    private static final String START = "Start...";

    private static void logThreadStarted(final String name) {
      SystemOutLogger.log("...thread for ", name, STARTED);
    }

    private static void logThreadFinished(final String name) {
      SystemOutLogger.log("...thread for ", name, FINISHED);
    }

    private static void logStart() {
      SystemOutLogger.log(START);
    }

    private static void logWaitingOnConditionForLockFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(WAITING_ON_CONDITION_FOR_LOCK, lock.getName(), TO_BE_ACQUIRED, FINISHED);
    }

    private static void logSettingConditionForLockStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(SETTING_THE_CONDITION_FOR_LOCK, lock.getName(), STARTED);
    }

    private static void logSettingConditionForLockFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(SETTING_THE_CONDITION_FOR_LOCK, lock.getName(), FINISHED);
    }

    private static void logAcquiringLockStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(ACQUIRING_LOCK, lock.getName(), STARTED);
    }

    private static void logAcquiringLockFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(ACQUIRING_LOCK, lock.getName(), FINISHED);
    }

    private static void logUnlockingLockStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(UNLOCKING_LOCK, lock.getName(), STARTED);
    }

    private static void logUnlockingLockFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(UNLOCKING_LOCK, lock.getName(), FINISHED);
    }

    private static void logEnteringMonitorStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(ENTERING_MONITOR, lock.getName(), STARTED);
    }

    private static void logEnteringMonitorFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(ENTERING_MONITOR, lock.getName(), FINISHED);
    }

    private static void logSettingEnteredConditionForMonitorStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(SETTING_THE_ENTERED_CONDITION_FOR_MONITOR, lock.getName(), STARTED);
    }

    private static void logSettingEnteredConditionForMonitorFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(SETTING_THE_ENTERED_CONDITION_FOR_MONITOR, lock.getName(), FINISHED);
    }

    private static void logWaitingOnConditionForMonitorStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(WAITING_ON_CONDITION_FOR_MONITOR, lock.getName(), TO_BE_ENTERED, STARTED);
    }

    private static void logWaitingOnConditionForMonitorFinished(final NamedReentrantLock lock) {
      SystemOutLogger.log(WAITING_ON_CONDITION_FOR_MONITOR, lock.getName(), TO_BE_ENTERED, FINISHED);
    }

    private static void logWaitingOnConditionForLockStarted(final NamedReentrantLock lock) {
      SystemOutLogger.log(WAITING_ON_CONDITION_FOR_LOCK, lock.getName(), TO_BE_ACQUIRED, STARTED);
    }
  }
}
