package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategorySetupTest 
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
        registeredUser = new CategorySetup("registeredUser");
    }

    /**
     * Test whether the aquired default categories are equal to the actual default categories.
     * Tests based on set equality: A ⊆ B and B ⊆ A ⇔ A = B
     */
    @Test
    @DisplayName("Check correct query of default categories")
    public void testAquireDefaultCategories()
    {
        Collection<String> defaultCategories = Arrays.asList("default_category1", "default_category2");
       
        assertTrue(defaultCategories.containsAll(guest.getDefaultCategories()));
        assertTrue(guest.getDefaultCategories().containsAll(defaultCategories));
        assertTrue(defaultCategories.containsAll(registeredUser.getDefaultCategories()));
        assertTrue(registeredUser.getDefaultCategories().containsAll(defaultCategories));
    }

    /**
     * Test whether the aquired custom categories are equal to the actual custom categories.
     * Tests based on set equality: A ⊆ B and B ⊆ A ⇔ A = B
     */
    @Test
    @DisplayName("Check query of custom categories")
    public void testAquireCustomCategories()
    {
        Collection<String> customCategoriesRegisteredUser 
        = Arrays.asList("example_category_1", "example_category_3", "example_category_2");
       
        assertNull(guest.getCustomCategories());   
        assertTrue(customCategoriesRegisteredUser.containsAll(registeredUser.getCustomCategories()));
        assertTrue(registeredUser.getCustomCategories().containsAll(customCategoriesRegisteredUser));
    }

    /**
     * Test whether the merge of default and custom categories is the proper merge.
     */
    @Test
    @DisplayName("Check correct merge of default and custom categories")
    public void testMergeDefaultAndCustomCategories()
    {   
        Collection<String> defaultCategories = Arrays.asList("default_category1", "default_category2");
        Collection<String> customCategoriesRegisteredUser 
        = Arrays.asList("example_category_1", "example_category_3", "example_category_2");
        Collection<String> allCategoriesGuestSolution = new ArrayList<>(defaultCategories);
        Collection<String> allCategoriesRegisteredUserSolution = new ArrayList<>(defaultCategories);
        allCategoriesRegisteredUserSolution.addAll(customCategoriesRegisteredUser);
        Collection<String> allCategoriesGuest = guest.getAllCategories(); 
        Collection<String> allCategoriesRegisteredUser = registeredUser.getAllCategories(); 

        assertTrue(allCategoriesGuestSolution.containsAll(allCategoriesGuest));
        assertTrue(allCategoriesGuest.containsAll(allCategoriesGuestSolution));
        assertTrue(allCategoriesRegisteredUserSolution.containsAll(allCategoriesRegisteredUser));
        assertTrue(allCategoriesRegisteredUser.containsAll(allCategoriesRegisteredUserSolution));
    }

}
