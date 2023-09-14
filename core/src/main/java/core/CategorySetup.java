package core;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategorySetup {

    private Collection<String> defaultCategories;
    private Collection<String> customCategories = null;
    private String username;
    private Category category;

    /**
     * Constructor setting up the available cateogories
     * @param username The username of the player if the player is registered, else username will be null
     */
    public CategorySetup(String username) {
        this.defaultCategories = getDefaultCategories();
        this.username = username;
        if (username != null)
        {
            this.customCategories = getCustomCategories(username);
        }
    }

    /**
     * Queries and returns all default categories
     * @return All default categories
     */
    public static Collection<String> getDefaultCategories() 
    {
        File[] defaultCategories = new File("./core/src/main/resources/default_categories").listFiles();
        return Arrays.asList(defaultCategories).stream().map(File::getName)
        .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
    }

    /**
     * Queries and returns custom categories if the user is a registered user
     * @param username The name of the user to provide custom categories for
     * @return All custom categories of given user
     */
    public static Collection<String> getCustomCategories(String username)
    {
        File[] customCategories = new File(("./core/src/main/resources/users/" + username)).listFiles();
        return Arrays.asList(customCategories).stream().map(File::getName)
        .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
    }

    /**
     * Combines the default and custom categories
     * @return A collection consisting of all default and custom categories
     */
    public Collection<String> getAllCategories()
    {
        return Stream.of(defaultCategories, customCategories).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Sets up a reference to the desired category. Will throw exception if user chooses a non-existent category.
     * @param category The desired category
     */
    public void setCategory(String category)
    {
        if (!getAllCategories().contains(category))
        {
            throw new IllegalArgumentException("The chosen category is not a part of the available categories.");
        }
        this.category = new Category(category, defaultCategories.contains(category), username);
    }

    /**
     * Provides a reference to the selected category
     * 
     * @return A reference to a category based on the category chosen by the user
     */
    public Category getCategory() {
        return category;
    }

}
