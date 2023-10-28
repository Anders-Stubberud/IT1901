package persistence;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import types.User;

public abstract class AbstractJsonIO {

  public static class JsonUtilities {

    /**
     * Provides absolute path to current working directory.
     *
     * @param directory - The directory to find the path to.
     * @return absolute path to current working directory as {@link String}.
     */
    private static Path getAbsolutePath(final String directory) {
      Path absolutePath = Paths.get("").toAbsolutePath();
      while (!absolutePath.endsWith(directory)) {
        absolutePath = absolutePath.getParent();
        if (absolutePath == null) {
          throw new IllegalStateException("Working directory not found.");
        }
      }
      return absolutePath.("/WordDetective/persistence/src/main/resources");
      // return absolutePath.toString() + "/WordDetective/persistence/src/main/resources";
    }

    public static Set<String> getAllUsernames(String username) {
      return Arrays.stream(new File(getAbsolutePath(username) + "/users").listFiles())
          .map(File::getName).collect(Collectors.toSet());
      Set<String> result = new HashSet<>();
      File[] nameFiles = new File(path + "/users").listFiles();
      if (nameFiles != null) {
        for (File file : nameFiles) {
          result.add(file.getName().replace(".json", ""));
        }
      } else {
        throw new RuntimeException("User directory not present in" + path);
      }
      return result;
    }

  }

  /**
   * Add a user to the database.
   *
   * @param user - The user to add
   */
  abstract boolean addedUserSuccessfully(User user);

  /**
   * Delete user from database.
   *
   * @param username - The user to delete
   */
  abstract void deleteUser(String username);

  /**
   * Get user as a {@link User} from database.
   *
   * @param username - The user to get
   * @return - A {@link User} object
   */
  abstract User getUser(String username);

  /**
   * get all the user's usernames in the database.
   *
   * @return - a {@link List} of strings containing all usernames
   */
  abstract List<String> getAllUsernames();

  /**
   * Update user and store new data in database.
   *
   * @param user - The new user object to override the old
   */
  abstract void updateUser(User user);

  /**
   * Get a defaultCategory as a {@link List}.
   *
   * @param category - The category to get
   * @return - A {@link List} of answers from that category
   */
  abstract List<String> getDefaultCategory(String category);

  /**
   * Get all the current default categories.
   *
   * @return - A {@link HashMap} of category names and respective answers
   */
  abstract HashMap<String, List<String>> getAllDefaultCategories();
}
