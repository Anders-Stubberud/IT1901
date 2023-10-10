package core;

public final class User {

    /**
     * The highscore of the given player; initalized to 0 in the constructor.
     */
    private int highscore;

    /**
     * The username of the given player; initialized in the constructor.
     */
    private String username;

    /**
     * The password of the given player; initialized in the constructor.
     */
    private String password;

    /**
     * Constructor setting up a new UserInfo object to be injected into correlating
     * json file.
     *
     * @param usernameParameter The username of the new user.
     * @param passwordParameter The password of the new user.
     */
    public User(final String usernameParameter, final String passwordParameter) {
        this.highscore = 0;
        this.username = usernameParameter;
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
     * Returns the username of the given user.
     *
     * @return The username of the give user.
     */
    public String getUsername() {
        return username;
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
