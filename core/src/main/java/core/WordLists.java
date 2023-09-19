package core;

import java.util.List;
import java.util.Set;

public class WordLists 
{

    Set<String> wordListForSearch;
    List<String> wordListFOrSelection;

    public WordLists(Set<String> wordListForSearch, List<String> wordListFOrSelection) 
    {
        this.wordListForSearch = wordListForSearch;
        this.wordListFOrSelection = wordListFOrSelection;
    }

    public Set<String> getWordListForSearch()
    {
        return wordListForSearch;
    }

    public List<String> getWordListFOrSelection()
    {
        return wordListFOrSelection;
    }

}
