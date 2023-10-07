package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class UserInfoIO {

    private static final String WORKING_DIRECTORY = "gr2325";

    public static Path getPath() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        return path;
    }

    public static Collection<String> getAllUsernames() {
        return Arrays.asList(
                new File(getPath().toString() + "/WordDetective/core/src/main/resources/users")
                        .listFiles())
                .stream().map(File::getName).collect(Collectors.toList());
    }

    public static boolean correctUsernameAndPassword(final String username, final String password) {
        if (getAllUsernames().contains(username)) {
            try {
                Path path = Paths.get(getPath().toString() +
                        "/WordDetective/core/src/main/resources/users/" + username
                        + "/stats/stats.json");
                String content = new String(Files.readAllBytes(path));
                Gson gsonParser = new Gson();
                JsonObject jsonObject = gsonParser.fromJson(content, JsonObject.class);
                String actualPassword = jsonObject.get("password").toString();
                actualPassword = actualPassword.substring(1, actualPassword.length() - 1);
                if (password.equals(actualPassword)) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertNewUser(String username, String password) {
        String path = getPath().toString() + "/WordDetective/core/src/main/resources/users/" + username;
        new File(path).mkdirs();
        new File(path + "/categories").mkdirs();
        new File(path + "/categories/.gitkeep");
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
        String destinationPath = getPath().toString() + "/WordDetective/core/src/main/resources/users/" + username
                + "/categories/" + filename;
        try (FileReader reader = new FileReader(absolutePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            FileWriter writer = new FileWriter(destinationPath);
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        uploadFile(
                "C:\\gr2325\\WordDetective\\core\\src\\main\\resources\\eksempel.json",
                "Anders",
                "eksempel.json");
    }

}
