package persistence;

import java.io.File;
import java.io.FileReader;
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
import com.google.gson.stream.JsonReader;

import types.User;


public final class JsonUtilities { 

  private JsonUtilities() {
    throw new AssertionError("Utility class - do not instantiate.");
  }

  /**
   * Gson instance for serialization/deserialization.
   */
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static final String pathToResources = getAbsolutePathAsString();

  /**
   * Provides absolute path to current working directory. Implemented in this fashion due to path differences in working files and test files. 
   *
   * @param directory - The directory to find the path to.
   * @return absolute path to current working directory as {@link String}.
   */
  public static String getAbsolutePathAsString() {
    Path absolutePath = Paths.get("").toAbsolutePath();
    while (!absolutePath.endsWith("gr2325")) {
      absolutePath = absolutePath.getParent();
      if (absolutePath == null) {
        throw new IllegalStateException("Working directory not found.");
      }
    }
    return absolutePath + "/WordDetective/persistence/src/main/resources";
  }

  public static boolean SuccessfullyAddedUserPersistently(final User user) {
    try (FileWriter fw = new FileWriter(pathToResources + "/users/" + user.getUsername() + ".json", StandardCharsets.UTF_8)) {
        GSON.toJson(user, fw);
        System.out.println("User " + user.getUsername() + " successfully created.");
        return true;
    } catch (IOException e) {
        System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getMessage());
        return false;
    }
  }

  public static boolean usernameAndPasswordMatch(final String username, final String password) throws IOException {
    String storedPassword;
    try {
      storedPassword = getPersistentProperty("password", pathToResources + "/users/" + username + ".json");
      if (storedPassword.equals(password)) {
      return true;
      }
      return false;
    } catch (IOException e) {
      throw new IOException("Error reading property", e); 
    }
  }

  public static String getCategoryFilename(final String category) {
    return "/" + category.replace(" ", "_") + ".json";
  }

  public static String getPersistentProperty(final String propertyName, final String location) throws IOException {
    try (JsonReader reader = new JsonReader(new FileReader(location))) {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(propertyName)) {
              return name;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    } catch (IOException e) {
      throw new IOException("Error reading property", e);
    }
    throw new IOException("Property not found");
  }

  public static Set<String> getPersistentFilenames(final String endpoint) throws RuntimeException {
    File[] nameFiles = new File(pathToResources + endpoint).listFiles();
    if (nameFiles != null) {
        Set<String> res = Arrays.stream(nameFiles).map(file -> {
          String name = file.getName();
          String stripJson = name.replace(".json", "");
          String formatSpace = stripJson.replace("_", " ");
          return formatSpace;
        }).collect(Collectors.toSet());
        return res;
    } else {
      throw new RuntimeException("Directory not present in " + pathToResources);
    }
  }

}
