package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategorySetupTest 
{

    private CategorySetup guestUser;
    private CategorySetup registeredUser;
    
    /**
     * Sets up two instances of the CategorySetup class to be used in the tests.
     */
    @BeforeEach
    public void setUp()
    {
        guestUser = new CategorySetup("guest");
        registeredUser = new CategorySetup("example_user");
    }

    @Test
    @DisplayName("Check correct query of default categories")
    public void testAquireDefaultCategories()
    {
        Collection<String> defaultCategories = Arrays.asList("default_category1", "default_category2");
        assertEquals(defaultCategories, guestUser.getDefaultCategories());
        assertEquals(defaultCategories, registeredUser.getDefaultCategories());
    }

    @Test
    @DisplayName("Check query of custom categories")
    public void testAquireCustomCategories()
    {
        
    }

}
