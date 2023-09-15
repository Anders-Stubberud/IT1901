package core;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategorySetup 
{

    private Collection<String> defaultCategories;
    private Collection<String> customCategories;
    private String username;
    private Category category;

    /**
     * Constructor setting up the available cateogories. Calls the respective functions in order to instanciate
     * the different collections of categories.
     * @param username The username of the player if the player is registered, else username will be null
     */
    public CategorySetup(String username) 
    {
        this.defaultCategories = createDefaultCategories();
        this.username = username;
        if (!username.equals("guest"))
        {
            this.customCategories = createCustomCategories();
        }
    }

    /**
     * Queries and returns all default categories
     * @return All default categories
     */
    public Collection<String> createDefaultCategories() 
    {
        // File[] defaultCategoriesArray = new File("./core/src/main/resources/default_categories").listFiles();
        // return Arrays.asList(defaultCategoriesArray).stream().map(File::getName)
        // .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
        File[] defaultCategoriesArray = new File("/gr2325/core/src/main/resources/default_categories").listFiles();
        return Arrays.asList(defaultCategoriesArray).stream().map(File::getName)
        .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
    }

    /**
     * Queries and returns custom categories if the user is a registered user
     * @param username The name of the user to provide custom categories for
     * @return All custom categories of given user
     */
    public Collection<String> createCustomCategories()
    {
        // File[] customCategories = new File(("./core/src/main/resources/users/" + username)).listFiles();
        // return Arrays.asList(customCategories).stream().map(File::getName)
        // .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
        File[] customCategories = new File(("/gr2325/core/src/main/resources/users/" + username)).listFiles();
        return Arrays.asList(customCategories).stream().map(File::getName)
        .map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());
    }

    public Collection<String> getDefaultCategories()
    {
        return defaultCategories;
    }

    public Collection<String> getCustomCategories()
    {
        return customCategories;
    }

    /**
     * Combines the default and custom categories
     * @return A collection consisting of all default and custom categories
     */
    public Collection<String> getAllCategories()
    {
        return customCategories != null ? Stream.of(defaultCategories, customCategories)
        .flatMap(Collection::stream).collect(Collectors.toList()) : defaultCategories;
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
    public Category getCategory() 
    {
        return category;
    }

    public String getUserName()
    {
        return username;
    }

}
