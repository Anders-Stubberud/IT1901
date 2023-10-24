package ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

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

  private static HttpResponse<String> performGetRequest(final String url) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
    return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
  }

  private static HttpResponse<String> performPostRequest(String url, String type, BodyPublisher content) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create(url))
    .header("Content-Type", type) 
    .POST(content)
    .build();
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
    HttpResponse<String> response = performGetRequest(url);
    //Må her sende all brukerinfo (inkludert custom wordlists) gjennom API'et, samtidig som alt sendes tilbake (uten at wordlists er berørt) i instansieringen av nytt Game-objekt. 
    return gson.fromJson(response.body(), User.class);
  }

  protected static boolean registrationControllerFireSignUp(final String username)
      throws IOException, InterruptedException {
    String url = BASEURL + "RegistrationController/fireSignUp" + username;
    HttpResponse<String> response = performGetRequest(url);
    return Boolean.parseBoolean(response.body());
  }

  protected static void gamePageControllerNewGame(User user)
      throws IOException, InterruptedException {
      //Sender all brukerinfo (inkludert uberørte custom lists) tilbake gjennom API'et. Kunne ha instansiert det på serversiden uten å sende det til client først. 
      String url = BASEURL + "GamePageController/newGame";
      String type = "application/json";
      BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(user));
      performPostRequest(url, type, body);
  }

  protected static void gamePageControllerSetCategory(String category)
      throws IOException, InterruptedException {
      String url = BASEURL + "GamePageController/setCategory";
      String type = "text/plain";
      BodyPublisher body = BodyPublishers.ofString(category); 
      performPostRequest(url, type, body).body();
  }

  protected static String gamePageControllerGetRandomWord()
      throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getRandomWord";
    HttpResponse<String> response = performGetRequest(url);
    return response.body();
  }

  protected static String gamePageControllerGetSubstring(String string) throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getSubstring/" + string;
    return performGetRequest(url).body();
  }

  protected static boolean gamePageControllerCheckValidWord(String substring, String guess) throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/checkValidWord/" + substring + "/" + guess;
    HttpResponse<String> response = performGetRequest(url);
    return Boolean.parseBoolean(response.body());
  }

  protected static void registrationControllerAddUser(User user) throws IOException, InterruptedException {
    String url = BASEURL + "registrationController/addUser";
    String type = "application/json";
    BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(user));
    performPostRequest(url, type, body);
  }

  public static void main(String[] args) {
    // User guest = new User();
    // User username = new User("username", "password");
    // try {
    //   gamePageControllerNewGame(username);
    //   game
    //   gamePageControllerSetCategory("countries");
    //   System.out.println(gamePageControllerGetRandomWord());
    // } catch (IOException | InterruptedException e) {
    //   // TODO Auto-generated catch block
    //   e.printStackTrace();
    // }
  }

}
