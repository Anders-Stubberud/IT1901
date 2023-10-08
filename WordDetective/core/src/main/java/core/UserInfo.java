package core;

public final class UserInfo {

    /**
     * The highscore of the given player; initalized to 0 in the constructor.
     */
    private int highscore;

    /**
     * The password of the given player; initialized in the constructor.
     */
    private String password;

    /**
     * Constructor setting up a new UserInfo object to be injected into correlating
     * json file.
     *
     * @param passwordParameter
     */
    public UserInfo(final String passwordParameter) {
        this.highscore = 0;
        this.password = passwordParameter;
    }

    /**
     * Returns the highscore of the given user.
     *
     * @return The highscore of the given user.
     */
    public int getHighScore() {
        return highscore;
    }

    /**
     * Returns the password of the given user.
     *
     * @return The password of the given user.
     */
    public String getPassword() {
        return password;
    }

}
