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

    private GameLogic guest;
    private GameLogic registeredUser;
    
    /**
     * Sets up two instances of the Category class to be used in the tests.
     */
    @BeforeEach
    public void setUp()
    {
        guest = new GameLogic("guest");
        guest.setCategory("default_category1");
        registeredUser = new GameLogic("registeredUser");
        registeredUser.setCategory("example_category1");
    }

    @Test
    @DisplayName("Check correct initialization of search and selection wordlists")
    public void testSetUpOfWordlists()
    {
        Collection<String> correctWordlistForGuest = Arrays.asList("This", "Is", "For", "Testing", "Purposes", "SomeThingWith_sti_AsSubstring");
        Collection<String> correctWordlistForRegisteredUser = Arrays.asList("This", "Is", "Example", "Category", "One");

        assertTrue(correctWordlistForGuest.containsAll(guest.getWordListForSearch()));
        assertTrue(guest.getWordListForSearch().containsAll(correctWordlistForGuest));
        assertTrue(correctWordlistForGuest.containsAll(guest.getWordlistForSelection()));
        assertTrue(guest.getWordlistForSelection().containsAll(correctWordlistForGuest));    
        
        assertTrue(correctWordlistForRegisteredUser.containsAll(registeredUser.getWordListForSearch()));
        assertTrue(registeredUser.getWordListForSearch().containsAll(correctWordlistForRegisteredUser));
        assertTrue(correctWordlistForRegisteredUser.containsAll(registeredUser.getWordlistForSelection()));
        assertTrue(registeredUser.getWordlistForSelection().containsAll(correctWordlistForRegisteredUser));     
    }

    @Test
    @DisplayName("Check valid pull of random word")
    public void testGetRandomWord()
    {
        for (int i=0; i<1000; i++)
        {
            String randomWordFromGuest = guest.getRandomWord();
            String randomWordFromRegisteredUser = registeredUser.getRandomWord();
            assertTrue(guest.getWordListForSearch().contains(randomWordFromGuest));
            assertTrue(registeredUser.getWordListForSearch().contains(randomWordFromRegisteredUser));
        }
        String wordWhichIsNotInEitherCategory = "thisWordIsNotInEitherCategory";
        assertFalse(guest.getWordListForSearch().contains(wordWhichIsNotInEitherCategory));
        assertFalse(registeredUser.getWordListForSearch().contains(wordWhichIsNotInEitherCategory));
    }

    @Test
    @DisplayName("Check valid substring of word")
    public void testGetRandomSubstring()
    {
        for (int i=0; i<1000; i++)
        {
            String randomWordFromGuest = guest.getRandomWord();
            String substringFromWordFromGuest = GameLogic.getRandomSubstring(randomWordFromGuest);
            String randomWordFromRegisteredUser = registeredUser.getRandomWord();
            String substringFromWordRegisteredUser = GameLogic.getRandomSubstring(randomWordFromRegisteredUser);

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

        assertTrue(guest.checkValidWord(subtringFromGuest, "Testing"));
        assertTrue(guest.checkValidWord(subtringFromGuest, "SomeThingWith_sti_AsSubstring"));
        assertFalse(guest.checkValidWord(subtringFromGuest, "notInListButContains_sti"));
    }

}
