package org.dimyriy.aux;

/**
 * @author Dmitrii Bogdanov
 * Created at 21.09.18
 */
public class SystemOutLogger {
  private SystemOutLogger() {
  }

  public static void log(final String... strings) {
    if (Configuration.isLoggingEnabled()) {
      System.out.println(String.join("", strings));
    }
  }
}
