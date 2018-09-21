package org.dimyriy.aux;

/**
 * @author Dmitrii Bogdanov
 * Created at 21.09.18
 */
public class Configuration {
  private static final Configuration INSTANCE = new Configuration(false);
  private boolean isLoggingEnabled;

  private Configuration(final boolean isLoggingEnabled) {
    this.isLoggingEnabled = isLoggingEnabled;
  }

  static boolean isLoggingEnabled() {
    return INSTANCE.isLoggingEnabled;
  }

  @SuppressWarnings("unused")
  static void enableLogging() {
    INSTANCE.isLoggingEnabled = true;
  }
}
