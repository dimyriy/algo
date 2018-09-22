package org.dimyriy.javapuzzles;

import org.dimyriy.javapuzzles.aux.DeadLockerLogSupport;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Dmitrii Bogdanov
 * Created at 16.09.18
 */
public class DeadLocker {
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
    DeadLockerLogSupport.logStart();
    threadPair.initializeFirstThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(firstContext));
    threadPair.initializeSecondThreadWithRunnable(() -> lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(secondContext));
    threadPair.startThreads();
  }

  void deadlockOnMonitors() {
    DeadLockerLogSupport.logStart();
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
    DeadLockerLogSupport.logEnteringMonitorStarted(context.getFirst());
    synchronized (context.getFirst()) {
      DeadLockerLogSupport.logEnteringMonitorFinished(context.getFirst());
      DeadLockerLogSupport.logSettingEnteredConditionForMonitorStarted(context.getFirst());
      context.getSetCondition().set(true);
      DeadLockerLogSupport.logSettingEnteredConditionForMonitorFinished(context.getFirst());
      DeadLockerLogSupport.logWaitingOnConditionForMonitorStarted(context.getSecond());
      while (!context.getCheckCondition().get()) {
        noop();
      }
      DeadLockerLogSupport.logWaitingOnConditionForMonitorFinished(context.getSecond());
      DeadLockerLogSupport.logEnteringMonitorStarted(context.getSecond());
      synchronized (context.getSecond()) {
        DeadLockerLogSupport.logEnteringMonitorFinished(context.getSecond());
      }
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private void lockFirstLockSetConditionWaitForCheckConditionAndLockSecondLock(final Context context) {
    DeadLockerLogSupport.logAcquiringLockStarted(context.getFirst());
    context.getFirst().lock();
    try {
      DeadLockerLogSupport.logAcquiringLockFinished(context.getFirst());
      DeadLockerLogSupport.logSettingConditionForLockStarted(context.getFirst());
      context.setCondition.set(true);
      DeadLockerLogSupport.logSettingConditionForLockFinished(context.getFirst());
      DeadLockerLogSupport.logWaitingOnConditionForLockStarted(context.getSecond());
      while (!context.checkCondition.get()) {
        noop();
      }
      DeadLockerLogSupport.logWaitingOnConditionForLockFinished(context.getSecond());
      DeadLockerLogSupport.logAcquiringLockStarted(context.getSecond());
      context.getSecond().lock();
      DeadLockerLogSupport.logAcquiringLockFinished(context.getSecond());
    } finally {
      DeadLockerLogSupport.logUnlockingLockStarted(context.getFirst());
      context.getFirst().unlock();
      DeadLockerLogSupport.logUnlockingLockFinished(context.getFirst());
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
        DeadLockerLogSupport.logThreadStarted(name);
        runnable.run();
        DeadLockerLogSupport.logThreadFinished(name);
      }));
    }

    Thread.State firstThreadState() {
      return first.get().getState();
    }

    Thread.State secondThreadState() {
      return second.get().getState();
    }
  }

  public static class NamedReentrantLock extends ReentrantLock {
    private final String name;

    NamedReentrantLock(final String name) {
      this.name = name;
    }

    public String getName() {
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

}
