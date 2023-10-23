package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryLogicTest {

    /**
     * A Game where the user is a guest.
     */
    private Game guest;
    /**
     * A Game where the user is a registered user.
     */
    private Game registeredUser;

    /**
     * Sets up two instances of the CategorySetup class to be used in the tests.
     */
    @BeforeEach
    public void setUp() {
        guest = new Game("guest");
        registeredUser = new Game("registeredUser");
    }

    /**
     * Test whether the aquired default categories are equal to the actual default
     * categories.
     * Tests based on set equality: A ⊆ B and B ⊆ A ⇔ A = B
     */
    @Test
    @DisplayName("Check correct query of default categories")
    public void testAquireDefaultCategories() {
        Collection<String> defaultCategories = Arrays.asList(
                "100 most common carbrands", "chemical elements", "colors",
                "countries", "fruits", "major oceans and lakes", "programming languages", "us states");

        assertTrue(defaultCategories.containsAll(guest.getCategoryLogic().getAvailableDefaultCategories()));
        assertTrue(guest.getCategoryLogic().getAvailableDefaultCategories().containsAll(defaultCategories));
        assertTrue(defaultCategories.containsAll(registeredUser.getCategoryLogic().getAvailableDefaultCategories()));
        assertTrue(registeredUser.getCategoryLogic().getAvailableDefaultCategories().containsAll(defaultCategories));
    }

    /**
     * Test whether the aquired custom categories are equal to the actual custom
     * categories.
     * Tests based on set equality: A ⊆ B and B ⊆ A ⇔ A = B
     */
    @Test
    @DisplayName("Check query of custom categories")
    public void testAquireCustomCategories() {
        Collection<String> customCategoriesRegisteredUser = Arrays.asList("example category1", "example category3",
                "example category2");

        assertNull(guest.getCategoryLogic().getAvailableCustomCategories());
        assertTrue(customCategoriesRegisteredUser
                .containsAll(registeredUser.getCategoryLogic().getAvailableCustomCategories()));
        assertTrue(registeredUser.getCategoryLogic().getAvailableCustomCategories()
                .containsAll(customCategoriesRegisteredUser));
    }

    /**
     * Test whether the merge of default and custom categories is the proper merge.
     * Tests based on set equality: A ⊆ B and B ⊆ A ⇔ A = B
     */
    @Test
    @DisplayName("Check correct merge of default and custom categories")
    public void testMergeDefaultAndCustomCategories() {
        Collection<String> defaultCategories = Arrays.asList("100 most common carbrands", "chemical elements", "colors",
                "countries", "fruits", "major oceans and lakes", "programming languages", "us states");
        Collection<String> customCategoriesRegisteredUser = Arrays.asList("example category1", "example category3",
                "example category2");
        Collection<String> allCategoriesGuestSolution = new ArrayList<>(defaultCategories);
        Collection<String> allCategoriesRegisteredUserSolution = new ArrayList<>(defaultCategories);
        allCategoriesRegisteredUserSolution.addAll(customCategoriesRegisteredUser);
        Collection<String> allCategoriesGuest = guest.getCategoryLogic().getAllAvailableCategories();
        Collection<String> allCategoriesRegisteredUser = registeredUser.getCategoryLogic().getAllAvailableCategories();

        assertTrue(allCategoriesGuestSolution.containsAll(allCategoriesGuest));
        assertTrue(allCategoriesGuest.containsAll(allCategoriesGuestSolution));
        assertTrue(allCategoriesRegisteredUserSolution.containsAll(allCategoriesRegisteredUser));
        assertTrue(allCategoriesRegisteredUser.containsAll(allCategoriesRegisteredUserSolution));
    }

    /**
     * Test for setting current game category.
     */
    @Test
    @DisplayName("Check correct set and get of category")
    public void testSetCategoryAndGetCategory() {
        // tests that the chosen category gets set and retreived correctly
        guest.setCategory("fruits");
        assertEquals("fruits", guest.getChosenCategory());
        assertNotEquals("something_else_than_default_category1", guest.getChosenCategory());
        registeredUser.setCategory("colors");
        assertEquals("colors", registeredUser.getChosenCategory());
        assertNotEquals("something_else_than_colors", registeredUser.getChosenCategory());

        // test that the guest user can not choose any custom categories
        assertThrows(IllegalArgumentException.class, () -> {
            guest.setCategory("example_category1");
        });

        // test that the registered user can choose a custom category
        registeredUser.setCategory("example category3");
        assertEquals("example category3", registeredUser.getChosenCategory());
    }

}
