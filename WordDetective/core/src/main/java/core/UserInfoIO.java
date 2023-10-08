package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class UserInfoIO {

    private static final String WORKING_DIRECTORY = "gr2325";

    public static String getPath() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        return path.toString() + "/WordDetective/core/src/main/resources/users";
    }

    public static Collection<String> getAllUsernames() {
        return Arrays.asList(
                new File(getPath().toString())
                        .listFiles())
                .stream().map(File::getName).collect(Collectors.toList());
    }

    public static JsonObject getJsonObject(String path) {
        JsonObject jsonObject = null;
        // Try-with-resources closes file automatically, thus no need to manually close.
        try (FileReader reader = new FileReader(path)) {
            Gson gsonParser = new Gson();
            jsonObject = gsonParser.fromJson(reader, JsonObject.class);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

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

    public static void insertNewUser(String username, String password) {
        String path = getPath() + "/" + username;
        new File(path).mkdirs();
        File categoryDirectory = new File(path + "/categories");
        categoryDirectory.mkdirs();
        File gitkeepFile = new File(categoryDirectory, ".gitkeep");
        try {
            gitkeepFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new File(path + "/stats").mkdirs();
        UserInfo userInfo = new UserInfo(0, password);
        try (FileWriter writer = new FileWriter(path + "/stats/stats.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userInfo, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(final String absolutePath, final String username, String filename) {
        String destinationPath = getPath() + "/" + username + "/categories/" + filename;
        JsonObject jsonObject = getJsonObject(absolutePath);
        try (FileWriter writer = new FileWriter(destinationPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
