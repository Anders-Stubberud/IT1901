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
  public void addUser(final User user);

  /**
   * Delete user from database.
   *
   * @param username - The user to delete
   */
  public void deleteUser(String username);

  /**
   * Get user as a {@link User} from database.
   *
   * @param username - The user to get
   * @return - A {@link User} object
   */
  public User getUser(String username);

  /**
   * Update user and store new data in database.
   *
   * @param username - The new user object to override the old
   */
  public void updateUser(User user);

  /**
   * Get a defaultCategory as a {@link List}.
   *
   * @param category - The category to get
   * @return - A {@link List} of answers from that category
   */
  public List<String> getDefaultCategory(String category);

  /**
   * Get all the current default categories.
   *
   * @return - A {@link HashMap} of category names and respective answers
   */
  public HashMap<String, List<String>> getAllDefaultCategories();
}
