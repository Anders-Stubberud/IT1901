package core;

/**
 * This class is used to create a timer for the game.
 */
public class Timer implements Runnable {

    /**
     * The current count.
     */
    private int currentCount = 30;
    /**
     * The amount of seconds the timer will count down from.
     */
    private int count = 30;
    /**
     * The amount of milliseconds the timer will count down from.
     */
    private static final int MILLISECONDS_IN_SECOND = 1000;

    /**
     * Run the timer from a spesific integer.
     *
     * @param countFrom - The {@link Integer} to count from
     */
    public void setTimer(final int countFrom) {
        count = countFrom;
        currentCount = countFrom;
    }

    /**
     * Get the current count.
     *
     * @return - The count as an {@link Integer}
     */
    public int getCount() {
        return currentCount;
    }

    /**
     * Run timer.
     * Default is 30 seconds. Use {@code setTimer(countFrom)}
     * to set where the timer should count from
     */
    public void run() {
        for (int i = count; i > 0; i--) {
            System.out.println(i);
            try {
                currentCount--;
                Thread.sleep(MILLISECONDS_IN_SECOND);
                // countdown by 1000 ms every second
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println(0);
    }
}
