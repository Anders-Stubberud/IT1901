package ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import com.google.gson.Gson;

import core.Game;
import types.User;

public final class ApiConfig {

  /**
   * HttpClient used for requests.
   */
  private static final HttpClient CLIENT = HttpClient.newHttpClient();

  /**
   * Configuration of portnumber. Externally listed to easily change all
   * portnumbers.
   */
  protected static final int PORTNUMBER = 8080;

  /**
   * Configuration of base URL to use with the API.
   */
  protected static final String BASEURL = "http://localhost:" + String.valueOf(PORTNUMBER) + "/";

  private static final Gson gson = new Gson();

  /**
   * No instantiation of utility class.
   */
  private ApiConfig() {
    throw new AssertionError("Utility class - do not instantiate.");
  }

  private static HttpResponse<String> performRequest(final String url) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
    return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Checks if username and password is a match.
   *
   * @param username The provided username.
   * @param password The provided password.
   * @return Boolean indicating if username and password is a match
   * @throws InterruptedException
   * @throws IOException
   */
  protected static User loginControllerPerformLogin(final String username)
      throws IOException, InterruptedException {
    String url = BASEURL + "LoginController/performLogin/" + username;
    HttpResponse<String> response = performRequest(url);
    return gson.fromJson(response.body(), User.class);
  }

  protected static boolean registrationControllerFireSignUp(final String username)
      throws IOException, InterruptedException {
    String url = BASEURL + "RegistrationController/fireSignUp/username";
    HttpResponse<String> response = performRequest(url);
    return Boolean.parseBoolean(response.body());
  }

  protected static void gamePageControllerNewGameLogic(String username)
      throws IOException, InterruptedException {
    performRequest(BASEURL + "GamePageController/newGameLogic/" + username);
  }

  protected static void gamePageControllerSetCategory(String category)
      throws IOException, InterruptedException {
    performRequest(BASEURL + "GamePageController/setCategory/" + category);
  }

  protected static String gamePageControllerGetRandomWord()
      throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getRandomWord";
    HttpResponse<String> response = performRequest(url);
    return response.body();
  }

  public static void main(String[] args) {
    try {
      gamePageControllerNewGameLogic("Anders");
      gamePageControllerSetCategory("custom");
      System.out.println(gamePageControllerGetRandomWord());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
