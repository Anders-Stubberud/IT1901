package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public final class UserIO {

    /**
     * Private defalt constructor, indicating that this utility class should not be
     * instantiated.
     */
    private UserIO() {
        throw new AssertionError("Utility class - do not instantiate.");
    }

    /**
     * Indicates the root folder of the project, used for file navigation.
     */
    private static final String WORKING_DIRECTORY = "gr2325";

    /**
     * Provides absolute path to current working directory.
     *
     * @return absolute path to current working directory.
     */
    public static String getPath() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
            if (path == null) {
                throw new IllegalStateException("Working directory not found.");
            }
        }
        return path.toString() + "/WordDetective/core/src/main/resources/users";
    }

    /**
     * Provides all registered usernames.
     *
     * @return A collection of the usernames of all registered users.
     */
    public static Collection<String> getAllUsernames() {
        File[] userDirectories = new File(getPath()).listFiles();

        if (userDirectories != null) {
            return Arrays.stream(userDirectories)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Provides a reference to a specific json file.
     *
     * @param path the absolute path of the requested json file
     * @return JsonObject representing the requested json file.
     */
    public static JsonObject getJsonObject(final String path) {
        JsonObject jsonObject = null;
        // Try-with-resources closes file automatically, thus no need to manually close.
        try (FileReader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            Gson gsonParser = new Gson();
            jsonObject = gsonParser.fromJson(reader, JsonObject.class);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Checks if the provided username if registered, and if so, if the correlating
     * password is correct.
     *
     * @param username the username provided by the user
     * @param password the password provided by the user.
     * @return a boolean indicating if the username and password is a match.
     */
    public static boolean correctUsernameAndPassword(final String username, final String password) {
        if (getAllUsernames().contains(username)) {
            String path = getPath() + "/" + username + "/stats/stats.json";
            JsonObject jsonObject = getJsonObject(path);
            String actualPassword = jsonObject.get("password").toString();
            actualPassword = actualPassword.substring(1, actualPassword.length() - 1);
            if (password.equals(actualPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new user.
     *
     * @param username the username of the new user.
     * @param password the password of the new user.
     */
    public static void insertNewUser(final String username, final String password) {
        String path = getPath() + "/" + username;
        File userDirectory = new File(path);
        if (!userDirectory.mkdirs()) {
            throw new RuntimeException("Failed to create the user directory: " + path);
        }
        File categoryDirectory = new File(path + "/categories");
        if (!categoryDirectory.mkdirs()) {
            throw new RuntimeException("Failed to create the categories directory.");
        }
        File gitkeepFile = new File(categoryDirectory, ".gitkeep");
        try {
            gitkeepFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (new File(path + "/stats").mkdirs()) {
            User user = new User(username, password);
            try (FileWriter writer = new FileWriter(path + "/stats/stats.json", StandardCharsets.UTF_8)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(user, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Failed to create the user directory: " + path + "/stats");
        }
    }

    /**
     * Deletes a user.
     *
     * @param folder the folder of the user to be deleted.
     */
    public static void deleteUser(final File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteUser(file);
                }
            }
        }
        boolean deleted = folder.delete();
        if (!deleted) {
            System.err.println("Failed to delete: " + folder.getAbsolutePath());
        }
    }

    /**
     * Upload a json file containing a custom wordlist to the custom-categories of
     * the given user.
     *
     * @param absolutePath the absolute path to the file containing the custom
     *                     wordlist.
     * @param username     the username of the user requesting to upload a custom
     *                     wordlist.
     * @param filename     the name of the file.
     */
    public static void uploadFile(final String absolutePath, final String username, final String filename) {
        String destinationPath = getPath() + "/" + username + "/categories/" + filename;
        JsonObject jsonObject = getJsonObject(absolutePath);
        try (FileWriter writer = new FileWriter(destinationPath, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
