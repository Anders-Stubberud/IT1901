package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TimerTest {

  /**
   * The count.
   */
  private int testCount;

  /**
   * Test the running of timer.
   */
  @Test
  public void testTimerRun() {
    testCount = 2;

    Timer timer = new Timer();

    timer.setTimer(testCount);

    Thread thread = new Thread(timer);
    thread.start();

    // Wait for the thread to finish
    try {
      assertEquals(testCount, timer.getCount());
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Check if the timer has counted down properly
    assertEquals(0, timer.getCount());
  }
}
