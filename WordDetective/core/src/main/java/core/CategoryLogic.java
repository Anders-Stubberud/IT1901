package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import persistence.JsonIO;

/**
 * A class containing the logic of getting and setting categories.
 */
public final class CategoryLogic {

    /**
     * Username of current player.
     */
    private String currentUser;
    /**
     * A collection of all default categories as strings.
     */
    private Collection<String> availableDefaultCategories;
    /**
     * A collection of all custom categories as strings.
     */
    private Collection<String> availableCustomCategories;

    /**
     * Initializes the logic regarding the categories.
     * Delegates the task of querying the available categories to FileIO.
     *
     * @param username The username of the user, which is used to access the
     *                 available wordlists of the user.
     */
    public CategoryLogic(final String username) {
        this.currentUser = username;
        availableDefaultCategories = JsonIO.loadDefaultCategories();
        if (!username.equals("guest")) {
            availableCustomCategories = JsonIO.loadCustomCategories(username);
        }
    }

    /**
     * @return Collection of default categories as strings
     */
    public Collection<String> getAvailableDefaultCategories() {
        return new ArrayList<>(availableDefaultCategories);
    }

    /**
     * @return Collection of custom categories as strings
     */
    public Collection<String> getAvailableCustomCategories() {
        if (availableCustomCategories == null) {
            return null;
        }
        return new ArrayList<>(availableCustomCategories);
    }

    /**
     * Combines the default and custom categories.
     *
     * @return A collection consisting of all default and custom categories.
     */
    public Collection<String> getAllAvailableCategories() {
        return (availableCustomCategories != null
                ? Stream.of(availableDefaultCategories, availableCustomCategories)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
                : availableDefaultCategories);
    }

    /**
     * Delegates the task of acquiring the wordlist for the chosen category to
     * FileIO.
     *
     * @param chosenCategory the category selected by the user.
     * @return A WorList object, which contains the wordlists for searching and
     *         selection.
     */

}
