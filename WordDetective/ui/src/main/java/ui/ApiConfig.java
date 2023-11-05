package ui;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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

  /**
   * Instance of Gson used to handle Json files.
   */
  private static final Gson GSON = new Gson();

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

  private static HttpResponse<String> performPostRequest(final String url, final String type, final BodyPublisher body)
      throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create(url))
    .header("Content-Type", type)
    .POST(body)
    .build();
    return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Checks if username and password is a match.
   *
   * @param username The provided username.
   * @return Boolean indicating if username and password is a match
   * @throws InterruptedException
   * @throws IOException
   */
  protected static User performLogin(final String username)
      throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "LoginController/performLogin" + "?username=" + param1;
    HttpResponse<String> response = performGetRequest(url);
    //Må her sende all brukerinfo (inkludert custom wordlists) gjennom API'et, samtidig som alt sendes tilbake
    //(uten at wordlists er berørt) i instansieringen av nytt Game-objekt.
    return GSON.fromJson(response.body(), User.class);
  }

  protected static boolean fireSignUp(final String username)
      throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "RegistrationController/fireSignUp" + "?username=" + param1;
    HttpResponse<String> response = performGetRequest(url);
    return Boolean.parseBoolean(response.body());
  }

  protected static void addUser(final User user) throws IOException, InterruptedException {
    String url = BASEURL + "registrationController/addUser";
    String type = "application/json";
    BodyPublisher body = HttpRequest.BodyPublishers.ofString(GSON.toJson(user));
    performPostRequest(url, type, body);
  }

  protected static void newGame(final User user)
      throws IOException, InterruptedException {
      //Sender all brukerinfo (inkludert uberørte custom lists) tilbake gjennom API'et.
      //Kunne ha instansiert det på serversiden uten å sende det til client først.
      String url = BASEURL + "GamePageController/newGame";
      String type = "application/json";
      BodyPublisher body = HttpRequest.BodyPublishers.ofString(GSON.toJson(user));
      performPostRequest(url, type, body);
  }

  protected static void setCategory(final String category)
      throws IOException, InterruptedException {
      String url = BASEURL + "GamePageController/setCategory";
      String type = "text/plain";
      BodyPublisher body = BodyPublishers.ofString(category);
      performPostRequest(url, type, body).body();
  }

  protected static String getRandomWord()
      throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getRandomWord";
    HttpResponse<String> response = performGetRequest(url);
    return response.body();
  }

  protected static String getSubstring(final String string) throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "GamePageController/getSubstring" + "?string=" + param1;
    return performGetRequest(url).body();
  }

  protected static boolean checkValidWord(final String substring, final  String guess)
      throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(substring, StandardCharsets.UTF_8.toString());
    String param2 = URLEncoder.encode(guess, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "GamePageController/checkValidWord" + "?substring=" + param1 + "&guess=" + param2;
    HttpResponse<String> response = performGetRequest(url);
    return Boolean.parseBoolean(response.body());
  }

  protected static void savePlayerHighscore(final String highscore) throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/savePlayerHighscore";
    String type = "text/plain";
    BodyPublisher body = BodyPublishers.ofString(highscore);
    performPostRequest(url, type, body);
  }

  protected static void updateUser(User user) throws IOException, InterruptedException {
    JsonIO jsonIO = new JsonIO(); //contact persistence
    jsonIO.updateUser(user); //run method in JsonIO
}

}
