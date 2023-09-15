package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategorySetupTest 
{
    
    /**
     * Sets up two instances of the CategorySetup class to be used in the tests.
     */
    @BeforeEach
    public void setUp()
    {
        CategorySetup guestUser = new CategorySetup(null);
        CategorySetup registeredUser = new CategorySetup("example_user");
    }

    // @Test
    // @DisplayName
    // public void testAquireDefaultCategories()
    // {
        
    // }

}
