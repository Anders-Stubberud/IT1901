package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryTest 
{

    private CategorySetup guest;
    private CategorySetup registeredUser;
    
    /**
     * Sets up two instances of the CategorySetup class to be used in the tests.
     */
    @BeforeEach
    public void setUp()
    {
        guest = new CategorySetup("guest");
        guest.setCategory("default_category1");
        registeredUser = new CategorySetup("registeredUser");
        registeredUser.setCategory("example_category_1");
    }

    @Test
    @DisplayName("Check correct initialization of search and selection wordlists")
    public void testSetUpOfWordlists()
    {
        Collection<String> correctWordlistForGuest = Arrays.asList("This", "Is", "For", "Testing", "Purposes");
        Collection<String> correctWordlistForRegisteredUser = Arrays.asList("This", "Is", "Example", "Category", "One");

        assertTrue(correctWordlistForGuest.containsAll(guest.getCategory().getWordListForSearch()));
        assertTrue(guest.getCategory().getWordListForSearch().containsAll(correctWordlistForGuest));
        assertTrue(correctWordlistForGuest.containsAll(guest.getCategory().getWordlistForSelection()));
        assertTrue(guest.getCategory().getWordlistForSelection().containsAll(correctWordlistForGuest));    
        
        assertTrue(correctWordlistForRegisteredUser.containsAll(registeredUser.getCategory().getWordListForSearch()));
        assertTrue(registeredUser.getCategory().getWordListForSearch().containsAll(correctWordlistForRegisteredUser));
        assertTrue(correctWordlistForRegisteredUser.containsAll(registeredUser.getCategory().getWordlistForSelection()));
        assertTrue(registeredUser.getCategory().getWordlistForSelection().containsAll(correctWordlistForRegisteredUser));     
    }

}
