package core;


/**
 * This class is used to create a timer for the game.
 */
public class Timer implements Runnable {

    /**
     * The amount of seconds the timer will count down from.
     */
    private static final int SECOND_TIMER = 30;
    /**
     * The amount of milliseconds the timer will count down from.
     */
    private static final int MILLISECONDS_IN_SECOND = 1000;

    /**
     * The constructor for the timer.
     */
    public void run() {
        for (int i = SECOND_TIMER; i >= 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(MILLISECONDS_IN_SECOND);
                //countdown by 1000 ms every second
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
