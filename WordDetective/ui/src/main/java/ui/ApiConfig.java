package ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import types.LoginStatus;
import types.RegistrationStatus;

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
   * Type of json parsing result.
   */
  private static Type listOfStringsType = new TypeToken<Set<String>>() {
  }.getType();

  /**
   * No instantiation of utility class.
   */
  private ApiConfig() {
    throw new AssertionError("Utility class - do not instantiate.");
  }

  /**
   * Boilerplate method for sending GET requests.
   *
   * @param url The URL to send the request to.
   * @return HttpResponse<String> containing the result.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  private static HttpResponse<String> performGetRequest(final String url) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
    return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Boilerplate method for sending POST requests.
   *
   * @param url  The URL to send the request to.
   * @param type Indicating the content of the request.
   * @param body Parameters supplied to the request.
   * @return HttpResponse<String> containing the result.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
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
   * @param password The provided password.
   * @return Boolean indicating if username and password is a match
   * @throws InterruptedException
   * @throws IOException
   */
  protected static LoginStatus performLogin(
      final String username, final String password) throws IOException, InterruptedException {
    String url = BASEURL + "LoginController/performLogin";
    String type = "text/plain";
    BodyPublisher body = HttpRequest.BodyPublishers.ofString("username=" + username + "&password=" + password);
    HttpResponse<String> response = performPostRequest(url, type, body);
    return GSON.fromJson(response.body(), LoginStatus.class);
  }

  /**
   * Attempts to registrate a new user.
   *
   * @param username The new username supplied by the user.
   * @param password The new password supplied by the user.
   * @return SUCCESS, USERNAME_TAKEN, USERNAME_NOT_MATCH_REGEX,
   *         PASSWORD_NOT_MATCH_REGEX, or UPLOAD_ERROR, respectively.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  protected static RegistrationStatus registrationResult(
      final String username, final String password) throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
    String param2 = URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "RegistrationController/registrationResult" + "?username=" + param1 + "&password=" + param2;
    HttpResponse<String> response = performGetRequest(url);
    return GSON.fromJson(response.body(), RegistrationStatus.class);
  }

  /**
   * Fetches the categories available to the given user.
   *
   * @param username The username of the user to fetch the categories of.
   * @return Set<String> containing all categories available to the given user.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  protected static Set<String> getCategories(final String username)
      throws IOException, InterruptedException {
    String param1 = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
    String url = BASEURL + "CategoryController/getCategories" + "?username=" + param1;
    HttpResponse<String> response = performGetRequest(url);
    return GSON.fromJson(response.body(), listOfStringsType);
  }

  /**
   * Adds a custom category to the user's custom categories.
   *
   * @param categoryName The name of the new custom category.
   * @param wordList     The wordlist correlating to the new custom category.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  // TODO kanskje returnere en indikasjon på om opplastningen fungerte eller ikke
  protected static void addCustomCategory(final String categoryName, final String[] wordList)
      throws IOException, InterruptedException {
    String url = BASEURL + "CategoryController/addCustomCategory";
    String type = "application/json";
    String jsonBody = String.format("{\"categoryName\":\"%s\",\"wordList\":%s}",
        categoryName, Arrays.toString(wordList));
    BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonBody);
    performPostRequest(url, type, body);
  }

  /**
   * Instantiates the game for the given user.
   *
   * @param username The username of the user to instantiate the game for.
   * @param category The categoy chosen by the user.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  protected static void newGame(final String username, final String category)
      throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/newGame";
    String type = "text/plain";
    BodyPublisher body = HttpRequest.BodyPublishers.ofString("username=" + username + "&category=" + category);
    performPostRequest(url, type, body);
  }

  /**
   * Fetches a word pulled randomly from the current game's wordlist.
   *
   * @return A random word from the game's wordlist.
   * @throws IOException          If any issues are encountered during interaction
   *                              with the files.
   * @throws InterruptedException If thread is interrupted.
   */
  protected static String getSubstring()
      throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getSubstring";
    HttpResponse<String> response = performGetRequest(url);
    return response.body();
  }

  protected static boolean checkValidWord(final String substring, final String guess)
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

  // Lage en funskjon som caller på endpointet laget i api
  protected static int getHighScore() throws IOException, InterruptedException {
    String url = BASEURL + "GamePageController/getPlayerHighscore";
    HttpResponse<String> response = performGetRequest(url);
    return Integer.parseInt(response.body());
  }

}
