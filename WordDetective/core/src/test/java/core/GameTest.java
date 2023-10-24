package core;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.util.Collection;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import persistence.JsonIO;
// import types.User;

public class GameTest {

    // /**
    // * GameLogic object for guest user.
    // */
    // private Game guest;
    // /**
    // * GameLogic object for registered user.
    // */
    // private Game registeredUser;
    // /**
    // * Digit for number of tests.
    // */
    // private static final int NUMBER_OF_TESTS = 1000;

    // /**
    // * Sets up two instances of the Category class to be used in the tests.
    // */
    // @BeforeEach
    // public void setUp() {
    // User testUser = new User("TestName", "TestPassword");
    // guest = new Game();
    // guest.setCategory("countries");
    // registeredUser.setCategory("example category1");
    // }

    // /**
    // * Checks that the queryed wordlist is identical to the correct wordlist.
    // */
    // @Test
    // @DisplayName("Check correct initialization of search and selection
    // wordlists")
    // public void testSetUpOfWordlists() {

    // }

    // /**
    // * Checks that a randomly pulled word from the selection list is contained in
    // * the search list.
    // */
    // @Test
    // @DisplayName("Check valid pull of random word")
    // public void testGetRandomWord() {
    // for (int i = 0; i < NUMBER_OF_TESTS; i++) {
    // String randomWordFromGuest = guest.getRandomWord();
    // String randomWordFromRegisteredUser = registeredUser.getRandomWord();
    // // assertTrue(guest.getWordListForSearch().contains(randomWordFromGuest));
    // //
    // assertTrue(registeredUser.getWordListForSearch().contains(randomWordFromRegisteredUser));
    // }
    // String wordWhichIsNotInEitherCategory = "thisWordIsNotInEitherCategory";
    // // assertFalse(guest..contains(wordWhichIsNotInEitherCategory));
    // //
    // assertFalse(registeredUser.getWordListForSearch().contains(wordWhichIsNotInEitherCategory));
    // }

    // /**
    // * Checks that a randomly generated substring from a randomly
    // * pulled word is indeed recognized as a valid substring.
    // */
    // @Test
    // @DisplayName("Check valid substring of word")
    // public void testGetRandomSubstring() {
    // for (int i = 0; i < NUMBER_OF_TESTS; i++) {
    // String randomWordFromGuest = guest.getRandomWord();
    // // String substringFromWordFromGuest =
    // Game.getRandomSubstring(randomWordFromGuest);

    // // assertTrue(randomWordFromGuest.matches(".*" + substringFromWordFromGuest +
    // ".*"));
    // // assertFalse(randomWordFromGuest.matches(".*" +
    // "thisSubstringIsNotValidAnywhere" + ".*"));
    // }
    // }

    // /**
    // * Checks that valid words are recognized based on a valid substring.
    // */
    // @Test
    // @DisplayName("Check valid guess")
    // public void testCheckValidWord() {
    // assertTrue(guest.checkValidWord("ET", "ETHIOPIA"));
    // assertTrue(guest.checkValidWord("ET", "VIETNAM"));
    // assertFalse(guest.checkValidWord("NOR",
    // "THIS_CONTAINS_NOR_BUT_IS_NOT_IN_WORDLIST"));
    // }

}
