package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserInfoIO {

    private static final String WORKING_DIRECTORY = "gr2325";

    public static boolean correctUsernameAndPassword(final String username, final String password) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        Collection<String> users = Arrays
                .asList(new File(path.toString() + "/WordDetective/core/src/main/resources/users")
                        .listFiles())
                .stream().map(File::getName).collect(Collectors.toList());
        if (users.contains(username)) {
            try {
                path = Paths.get(path.toString() +
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

}
