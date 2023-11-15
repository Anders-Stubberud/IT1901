package api;

import api.controllers.CategoryController;
import api.controllers.GamePageController;
import api.controllers.RegistrationController;
import core.UserAccess;
import persistence.JsonIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;

import types.LoginStatus;
import types.RegistrationStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GamePageControllerTest {

  /**
   * Integer used to specify the last executed test.
   */
  private static final int FINALTEST = 4;

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

  private boolean testTemplate(String username, String password) throws Exception {
    return true;
  }

  @Test
  @Order(1)
  public void testNewGame() throws Exception {
    String requestBody = "username=TestUser&category=colors";
    mockMvc.perform(post("/GamePageController/newGame")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @Order(2)
  public void testGetSubString() throws Exception {
    String requestBody = "username=TestUser&category=colors";
    mockMvc.perform(post("/GamePageController/newGame")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    String response = mockMvc.perform(get("/GamePageController/getSubstring")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(new MediaType("text", "plain", StandardCharsets.UTF_8)))
        .andReturn()
        .getResponse()
        .getContentAsString();
    assertNotNull(response);
  }

  @Test
  @Order(3)
  public void testGetHighscore() throws Exception {
    String requestBody = "username=TestUser&category=colors";
    mockMvc.perform(post("/GamePageController/newGame")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    String response = mockMvc.perform(get("/GamePageController/getPlayerHighscore")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString();
    Integer highscore = Integer.parseInt(response);
    Integer actualHighscore = 0;
    assertEquals(actualHighscore, highscore);
  }

  @Test
  @Order(FINALTEST)
  public void testSetHighscore() throws Exception {
    String requestBody = "username=TestUser&category=colors";
    mockMvc.perform(post("/GamePageController/newGame")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc.perform(post("/GamePageController/savePlayerHighscore")
        .content("100")
        .contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk());

    String response = mockMvc.perform(get("/GamePageController/getPlayerHighscore")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString();
    Integer highscore = Integer.parseInt(response);
    Integer actualHighscore = 100;
    assertEquals(actualHighscore, highscore);

    mockMvc.perform(post("/GamePageController/savePlayerHighscore")
        .content("0")
        .contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk());

    response = mockMvc.perform(get("/GamePageController/getPlayerHighscore")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString();
    highscore = Integer.parseInt(response);
    // Only write if highscore is higher
    actualHighscore = 100;
    assertEquals(actualHighscore, highscore);

    // Foreløpig bruteforce
    JsonIO jsonIO = new JsonIO("TestUser");
    jsonIO.updateCurrentUser(
        (user) -> {
          user.setHighscore(0);
          return true;
        });

  }

}
