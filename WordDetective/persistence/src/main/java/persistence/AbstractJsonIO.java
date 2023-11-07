package persistence;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import types.User;

public interface AbstractJsonIO {

  // /**
  //  * Add a user to the database.
  //  *
  //  * @param user - The user to add
  //  */
  // abstract boolean addedUserSuccessfully(User user);

  /**
   * Delete user from database.
   *
   */
  void deleteCurrentUser();

  /**
   * Get user as a {@link User} from database.
   *
   * @return - A {@link User} object
   */
  User loadCurrentUser();

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
   * @param predicate A predicate which potentially changes the instance of the user.
   *  The predicate returns a boolean indicating if changes were made, which is used to persistenty store the potential changes.
   */
  void updateCurrentUser(Predicate<User> predicate) throws IOException;

  /**
   * Get a defaultCategory as a {@link List}.
   *
   * @param category - The category to get
   * @return - A {@link List} of answers from that category
   */
  List<String> getDefaultCategory(String category) throws IOException;

  // /**
  //  * Get all the current default categories.
  //  *
  //  * @return - A {@link HashMap} of category names and respective answers
  //  */
  // abstract HashMap<String, List<String>> getAllDefaultCategories();
}
