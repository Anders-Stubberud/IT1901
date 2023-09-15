package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
     * Sets up two instances of the Category class to be used in the tests.
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
        Collection<String> correctWordlistForGuest = Arrays.asList("This", "Is", "For", "Testing", "Purposes", "SomeThingWith_sti_AsSubstring");
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

    @Test
    @DisplayName("Check valid pull of random word")
    public void testGetRandomWord()
    {
        for (int i=0; i<1000; i++)
        {
            String randomWordFromGuest = guest.getCategory().getRandomWord();
            String randomWordFromRegisteredUser = registeredUser.getCategory().getRandomWord();
            assertTrue(guest.getCategory().getWordListForSearch().contains(randomWordFromGuest));
            assertTrue(registeredUser.getCategory().getWordListForSearch().contains(randomWordFromRegisteredUser));
        }
        String wordWhichIsNotInEitherCategory = "thisWordIsNotInEitherCategory";
        assertFalse(guest.getCategory().getWordListForSearch().contains(wordWhichIsNotInEitherCategory));
        assertFalse(registeredUser.getCategory().getWordListForSearch().contains(wordWhichIsNotInEitherCategory));
    }

    @Test
    @DisplayName("Check valid substring of word")
    public void testGetRandomSubstring()
    {
        for (int i=0; i<1000; i++)
        {
            String randomWordFromGuest = guest.getCategory().getRandomWord();
            String substringFromWordFromGuest = Category.getRandomSubstring(randomWordFromGuest);
            String randomWordFromRegisteredUser = registeredUser.getCategory().getRandomWord();
            String substringFromWordRegisteredUser = Category.getRandomSubstring(randomWordFromRegisteredUser);

            assertTrue(randomWordFromGuest.matches(".*" + substringFromWordFromGuest + ".*"));
            assertFalse(randomWordFromGuest.matches(".*" + "thisSubstringIsNotValidAnywhere" + ".*"));
            assertTrue(randomWordFromRegisteredUser.matches(".*" + substringFromWordRegisteredUser + ".*"));
            assertFalse(randomWordFromRegisteredUser.matches(".*" + "thisSubstringIsNotValidAnywhere" + ".*"));
        }
    }

    @Test
    @DisplayName("Check valid guess")
    public void testCheckValidWord()
    {
        String wordFromGuest = "Testing";
        String subtringFromGuest = "sti";

        assertTrue(guest.getCategory().checkValidWord(subtringFromGuest, "Testing"));
        assertTrue(guest.getCategory().checkValidWord(subtringFromGuest, "SomeThingWith_sti_AsSubstring"));
        assertFalse(guest.getCategory().checkValidWord(subtringFromGuest, "notInListButContains_sti"));
    }

}
