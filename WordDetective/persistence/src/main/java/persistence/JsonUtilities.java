package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import types.User;


public class JsonUtilities { 

  /**
   * Gson instance for serialization/deserialization.
   */
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /**
   * Provides absolute path to current working directory. Implemented in this fashion due to path differences in working files and test files. 
   *
   * @param directory - The directory to find the path to.
   * @return absolute path to current working directory as {@link String}.
   */
  public static String getAbsolutePathAsString(String endpoint) {
    Path absolutePath = Paths.get("").toAbsolutePath();
    while (!absolutePath.endsWith("gr2325")) {
      absolutePath = absolutePath.getParent();
      if (absolutePath == null) {
        throw new IllegalStateException("Working directory not found.");
      }
    }
    return absolutePath.resolve("/WordDetective/persistence/src/main/resources").toString() + endpoint;
  }

  public boolean persistentlyAddedUser(final User user) {
    try (FileWriter fw = new FileWriter(getAbsolutePathAsString("/users/" + user.getUsername() + ".json"), StandardCharsets.UTF_8)) {
        GSON.toJson(user, fw);
        System.out.println("User " + user.getUsername() + " successfully created.");
        return true;
    } catch (IOException e) {
        System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getMessage());
        return false;
    }
  }

  public static Set<String> getAllUsernames() {
    String pathAsString = getAbsolutePathAsString("/users");
    File[] nameFiles = new File(pathAsString).listFiles();
    if (nameFiles != null) {
      return Arrays.stream(nameFiles).map(File::getName).collect(Collectors.toSet());
    } else {
      throw new RuntimeException("User directory not present in" + pathAsString);
    }
  }

}
