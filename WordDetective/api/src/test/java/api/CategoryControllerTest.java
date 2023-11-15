package api;

import api.controllers.CategoryController;
import persistence.JsonIO;

import com.google.gson.Gson;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
import org.mockito.InjectMocks;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

  /**
   * Integer used to specify the last executed test.
   */
  private static final int FINALTEST = 3;

  /**
   * Classrule used for testing purposes.
   */
  @ClassRule
  public static final SpringClassRule SPRINGCLASSRULE = new SpringClassRule();

  /**
   * Rule definition used for testing purposes.
   */
  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();

  /**
   * Rule definition used for testing purposes.
   */
  @Rule
  public TestWatcher watchman = TestWatchman.watchman;

  /**
   * Setup of the Springboot server.
   */
  @BeforeClass
  public static void setUpClass() {
    TestWatchman.watchman.registerClass(LoginControllerTest.class);
  }

  /**
   * Shutdown of the Springboot server.
   */
  @AfterClass
  public static void tearDownClass() {
    TestWatchman.watchman.createReport(LoginControllerTest.class);
  }

  @Autowired
  private MockMvc mockMvc;

  private Map<String, Set<String>> testTemplate() throws Exception {
    String response = mockMvc.perform(get("/CategoryController/getCategories")
        .param("username", "TestUser")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString();

    Type type = new TypeToken<Map<String, Set<String>>>() {
    }.getType();
    Map<String, Set<String>> responseMap = new Gson().fromJson(response, type);
    return responseMap;
  }

  @Test
  @Order(1)
  public void testGetCategories() throws Exception {
    Set<String> categories = new HashSet<>(Set.of(
        "chemical elements", "fruits", "major oceans and lakes",
        "us states", "countries", "programming languages",
        "colors", "100 most common carbrands"));

    Map<String, Set<String>> responseMap = testTemplate();

    Set<String> default_categories = new HashSet<String>(responseMap.get("default"));
    Set<String> custom_categories = new HashSet<String>(responseMap.get("custom"));
    Set<String> results = new HashSet<>(default_categories);
    results.addAll(custom_categories);

    // Tests based on set equality. A ⊆ B and B ⊆ A <=> A = B.
    assertTrue(categories.containsAll(results));
    assertTrue(results.containsAll(categories));
  }

  @Test
  @Order(2)
  public void testAddCustomCategory() throws Exception {
    Set<String> categoriesBeforeAdd = testTemplate().get("custom");

    assertFalse(categoriesBeforeAdd.contains("TestCategory"));

    String requestBody = "{\"categoryName\":\"TestCategory\",\"wordList\":[word1,word2]}";
    mockMvc.perform(post("/CategoryController/addCustomCategory")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    Set<String> categoriesAfterAdd = testTemplate().get("custom");

    assertTrue(categoriesAfterAdd.contains("TestCategory"));
  }

  @Test
  @Order(FINALTEST)
  public void testDeleteCustomCategory() throws Exception {
    String categoryName = "TestCategory";

    Set<String> categoriesBeforeDelete = testTemplate().get("custom");
    System.out.println("\n\n\n" + categoriesBeforeDelete + "\n\n\n");

    // Bruteforce løsning før deleteUser fra dev er på plass
    JsonIO jsonIO = new JsonIO("TestUser");
    try {
      jsonIO.updateCurrentUser(
          (user) -> {
            user.deleteCustomCategories(categoryName);
            return true;
          });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Set<String> categoriesAfterDelete = testTemplate().get("custom");
    assertFalse(categoriesAfterDelete.contains(categoryName));
  }

}
