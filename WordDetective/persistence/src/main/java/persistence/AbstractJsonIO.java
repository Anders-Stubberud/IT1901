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
  abstract void deleteCurrentUser();

  /**
   * Get user as a {@link User} from database.
   *
   * @param username - The user to get
   * @return - A {@link User} object
   */
  abstract User loadCurrentUser();

  // /**
  //  * get all the user's usernames in the database.
  //  *
  //  * @return - a {@link List} of strings containing all usernames
  //  */
  // abstract List<String> getAllUsernames();

  /**
   * Update user and store new data in database.
   *
   * @param user - The new user object to override the old
   */
  abstract void updateCurrentUser(final Predicate<User> consumer) throws IOException;

  /**
   * Get a defaultCategory as a {@link List}.
   *
   * @param category - The category to get
   * @return - A {@link List} of answers from that category
   */
  abstract List<String> getDefaultCategory(final String category) throws IOException;

  // /**
  //  * Get all the current default categories.
  //  *
  //  * @return - A {@link HashMap} of category names and respective answers
  //  */
  // abstract HashMap<String, List<String>> getAllDefaultCategories();
}
