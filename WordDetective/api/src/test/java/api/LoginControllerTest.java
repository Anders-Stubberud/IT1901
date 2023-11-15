package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
import types.LoginStatus;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.lang.reflect.Type;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

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

  private LoginStatus testTemplate(String username, String password) throws Exception {
    String requestBody = "username=" + username + "&password=" + password;
    String response = mockMvc.perform(post("/LoginController/performLogin")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString();
    Type type = new TypeToken<LoginStatus>() {
    }.getType();
    return new Gson().fromJson(response, type);
  }

  @Test
  @Order(1)
  public void testLoginSuccessful() throws Exception {
    LoginStatus result = testTemplate("TestUser", "Password");
    assertSame(LoginStatus.SUCCESS, result);
  }

  @Test
  @Order(2)
  public void testLoginIncorrectPassword() throws Exception {
    LoginStatus result = testTemplate("TestUser", "incorrectPassword");
    assertSame(LoginStatus.INCORRECT_PASSWORD, result);
  }

  @Test
  @Order(FINALTEST)
  public void testLoginInvalidUsername() throws Exception {
    LoginStatus result = testTemplate("unvalidUserName", "Password");
    assertSame(LoginStatus.USERNAME_DOES_NOT_EXIST, result);
  }

}
