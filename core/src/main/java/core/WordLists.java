package core;

import java.util.List;
import java.util.Set;

public class WordLists {

    /**
     * Set of words.
     * Used when searching for a word in Set
     * because of faster runtime.
     */
    private Set<String> wordListForSearch;
    /**
     * List of words.
     * Used when needing to extract
     * words because of functionality.
     */
    private List<String> wordListForSelection;

    /**
     * Constructor for WordLists.
     *
     * @param searchList    - A set of words used
     *                      for fast searching.
     * @param selectionList - A list of the same
     *                      words used for functionality.
     */
    public WordLists(final Set<String> searchList, final List<String> selectionList) {
        this.wordListForSearch = searchList;
        this.wordListForSelection = selectionList;
    }

    /**
     * @return Set of words
     */
    public Set<String> getWordListForSearch() {
        return wordListForSearch;
    }

    /**
     * @return List of words
     */
    public List<String> getWordListForSelection() {
        return wordListForSelection;
    }

}
