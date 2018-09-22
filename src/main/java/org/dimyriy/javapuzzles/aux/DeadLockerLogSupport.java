package org.dimyriy.javapuzzles.aux;

import org.dimyriy.aux.SystemOutLogger;
import org.dimyriy.javapuzzles.DeadLocker;

/**
 * @author Dmitrii Bogdanov
 * Created at 22.09.18
 */
public class DeadLockerLogSupport {
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

  private DeadLockerLogSupport() {
  }

  public static void logThreadStarted(final String name) {
    SystemOutLogger.log("...thread for ", name, STARTED);
  }

  public static void logThreadFinished(final String name) {
    SystemOutLogger.log("...thread for ", name, FINISHED);
  }

  public static void logStart() {
    SystemOutLogger.log(START);
  }

  public static void logWaitingOnConditionForLockFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(WAITING_ON_CONDITION_FOR_LOCK, lock.getName(), TO_BE_ACQUIRED, FINISHED);
  }

  public static void logSettingConditionForLockStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(SETTING_THE_CONDITION_FOR_LOCK, lock.getName(), STARTED);
  }

  public static void logSettingConditionForLockFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(SETTING_THE_CONDITION_FOR_LOCK, lock.getName(), FINISHED);
  }

  public static void logAcquiringLockStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(ACQUIRING_LOCK, lock.getName(), STARTED);
  }

  public static void logAcquiringLockFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(ACQUIRING_LOCK, lock.getName(), FINISHED);
  }

  public static void logUnlockingLockStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(UNLOCKING_LOCK, lock.getName(), STARTED);
  }

  public static void logUnlockingLockFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(UNLOCKING_LOCK, lock.getName(), FINISHED);
  }

  public static void logEnteringMonitorStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(ENTERING_MONITOR, lock.getName(), STARTED);
  }

  public static void logEnteringMonitorFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(ENTERING_MONITOR, lock.getName(), FINISHED);
  }

  public static void logSettingEnteredConditionForMonitorStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(SETTING_THE_ENTERED_CONDITION_FOR_MONITOR, lock.getName(), STARTED);
  }

  public static void logSettingEnteredConditionForMonitorFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(SETTING_THE_ENTERED_CONDITION_FOR_MONITOR, lock.getName(), FINISHED);
  }

  public static void logWaitingOnConditionForMonitorStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(WAITING_ON_CONDITION_FOR_MONITOR, lock.getName(), TO_BE_ENTERED, STARTED);
  }

  public static void logWaitingOnConditionForMonitorFinished(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(WAITING_ON_CONDITION_FOR_MONITOR, lock.getName(), TO_BE_ENTERED, FINISHED);
  }

  public static void logWaitingOnConditionForLockStarted(final DeadLocker.NamedReentrantLock lock) {
    SystemOutLogger.log(WAITING_ON_CONDITION_FOR_LOCK, lock.getName(), TO_BE_ACQUIRED, STARTED);
  }
}
