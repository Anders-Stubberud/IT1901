package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseCategory_GetWordAndSubstring {

    private Collection<String> available_categories;

    public ChooseCategory_GetWordAndSubstring(String username) {
        this.available_categories = provideDefaultCategoryAlternatives(username);
    }

    /**
     * Combines the default categories with potential custom categories.
     * 
     * @param username the username used to fetch custom categories. Username is
     *                 null if player is guest.
     * @return Collection of strings which specify the different categories the user
     *         can choose between.
     */
    public static Collection<String> provideDefaultCategoryAlternatives(String username) {
        File[] defaultCategories = new File("./core/src/main/resources/default_categories").listFiles();
        // Collection of all the default categories after removing ".txt" suffix
        Collection<String> categoriesAsStrings = Arrays.asList(defaultCategories)
        .stream().map(File::getName).map(n -> n.substring(0, n.indexOf("."))).collect(Collectors.toList());

        // Adds potential custom categories if the user is registered
        if (username != null) {
            File[] customCategories = new File(("./core/src/main/resources/users/" + username)).listFiles();
            categoriesAsStrings.addAll(Arrays.asList(customCategories)
            .stream().map(File::getName).map(n -> n.substring(0, n.indexOf("."))).toList());
        }

        return categoriesAsStrings;
    }

    /**
     * Provides a reference to the selected category
     * 
     * @return A randomly generated substring from the parameter
     */
    public Category getCategory() {
        return null;
    }

    /**
     * Chooses a word randomly from the selected category
     * 
     * @return A randomly generated substring from the parameter
     */
    public String getRandomWord() {
        // TODO
        return null;
    }

    /**
     * Randomly generates a substring from the randomly chosen word
     * 
     * @param word The chosen word to generate a substring from
     * @return A randomly generated substring from the parameter
     */
    public static String getRandomSubstring(String word) {
        // TODO
        return null;
    }

}
