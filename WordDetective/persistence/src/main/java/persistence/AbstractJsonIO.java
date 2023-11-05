package persistence;

import java.util.HashMap;
import java.util.List;

import types.User;

public interface AbstractJsonIO {
  /**
   * Add a user to the database.
   *
   * @param user - The user to add
   */
  void addUser(User user);

  /**
   * Delete user from database.
   *
   * @param username - The user to delete
   */
  void deleteUser(String username);

  /**
   * Get user as a {@link User} from database.
   *
   * @param username - The user to get
   * @return - A {@link User} object
   */
  User getUser(String username);

  /**
   * Get user as a Json String.
   * @param username - The user to get
   * @return - The user as a String
   */
  String getUserAsJson(String username);

  /**
   * get all the user's usernames in the database.
   *
   * @return - a {@link List} of strings containing all usernames
   */
  List<String> getAllUsernames();

  /**
   * Update user and store new data in database.
   *
   * @param user - The new user object to override the old
   */
  void updateUser(User user);

  /**
   * Get a defaultCategory as a {@link List}.
   *
   * @param category - The category to get
   * @return - A {@link List} of answers from that category
   */
  List<String> getDefaultCategory(String category);

  /**
   * Get all the current default categories.
   *
   * @return - A {@link HashMap} of category names and respective answers
   */
  HashMap<String, List<String>> getAllDefaultCategories();
}
