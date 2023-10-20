package types;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public final class User {
    /**
     * The highscore of the given player
     */
    @SerializedName("highscore")
    private int highscore;
    /**
     * The username of the given player
     */
    @SerializedName("username")
    private String username;

    /**
     * The password of the given player
     */
    @SerializedName("password")
    private String password;

    /**
     * The user's custom categories
     */
    private HashMap<String, List<String>> customCategories;

    /**
     * Constructor setting up a new User object to be injected into correlating
     * json file.
     *
     * @param usernameParameter The username of the new user.
     * @param passwordParameter The password of the new user.
     */
    public User(final String usernameParameter, final String passwordParameter) {
        this.customCategories = new HashMap<>();
        this.highscore = 0;
        this.username = usernameParameter;
        this.password = passwordParameter;
    }

    /**
     * Get user's highscore
     *
     * @return The highscore of the given user as an Integer.
     */
    public int getHighScore() {
        return highscore;
    }

    /**
     * Get user's username
     *
     * @return The username of the given user as String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get user's password.
     *
     * @return The password of the given user as a string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get user's custom categories
     *
     * @return a HashMap containing the user's custom categories
     */
    public HashMap<String, List<String>> getCustomCategories() {
        return customCategories;
    }

    /**
     * Set the user's highscore
     *
     * @param highscore - The highscore to set
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    /**
     * Set the user's custom categories
     *
     * @param customCategories - a HashMap with all the categories
     */
    public void setCustomCategories(HashMap<String, List<String>> customCategories) {
        this.customCategories = customCategories;
    }

    /**
     * Add a custom category to user
     *
     * @param name           - Name of category
     * @param customCategory - List of all answers in category
     */
    public void addCustomCategories(final String name, final List<String> customCategory) {
        this.customCategories.put(name, customCategory);
    }

    /**
     * Delete a custom category from user's custom categories
     * 
     * @param category - The category to delete
     */
    public void deleteCustomCategories(final String category) {
        this.customCategories.remove(category);
    }

}
