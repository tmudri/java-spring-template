package de.stuttgart.tmudri.templates.java.spring.extensions;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.doAnswer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.mockito.stubbing.Stubber;

public class MockitoExtensions {

  public static Stubber countDownAndExecute(CountDownLatch latch) {
    return doAnswer(
        invocationOnMock -> {
          try {
            return invocationOnMock.getMock();
          } finally {
            latch.countDown();
          }
        });
  }

  public static Stubber countDownAndThrow(CountDownLatch latch, Throwable throwable) {
    return doAnswer(
        invocationOnMock -> {
          try {
            throw throwable;
          } finally {
            latch.countDown();
          }
        });
  }

  public static void awaitAtMostWithLatch(int time, TimeUnit timeUnit, CountDownLatch latch) {
    await()
        .atMost(time, timeUnit)
        .dontCatchUncaughtExceptions()
        .until(
            () -> {
              latch.await();
              return true;
            });
  }
}
