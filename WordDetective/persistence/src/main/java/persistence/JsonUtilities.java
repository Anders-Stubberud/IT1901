// package persistence;

// import java.io.File;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Arrays;
// import java.util.Set;
// import java.util.stream.Collectors;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.stream.JsonReader;

// import types.User;

// public final class JsonUtilities {

// private JsonUtilities() {
// throw new AssertionError("Utility class - do not instantiate.");
// }

// /**
// * Gson instance for serialization/deserialization.
// */
// public static final Gson GSON = new
// GsonBuilder().setPrettyPrinting().create();

// /**
// * Constant used to reference the absolute path of the persistence module's
// resource directory.
// */
// public static final String PATH_TO_RESOURCES = getAbsolutePathAsString();

// /**
// * Provides absolute path to current working directory.
// * Implemented in this fashion due to path differences in working files and
// test files.
// *
// * @return absolute path to current working directory as {@link String}.
// */
// public static String getAbsolutePathAsString() {
// Path absolutePath = Paths.get("").toAbsolutePath();
// while (!absolutePath.endsWith("gr2325")) {
// absolutePath = absolutePath.getParent();
// if (absolutePath == null) {
// throw new IllegalStateException("Working directory not found.");
// }
// }
// return absolutePath + "/WordDetective/persistence/src/main/resources";
// }

// /**
// * Attempts to persistently add a new user.
// * @param user The user of which to persistently add.
// * @return Boolean indicating if the user was added successfully.
// */
// public static boolean successfullyAddedUserPersistently(final User user) {
// try (FileWriter fw = new FileWriter(PATH_TO_RESOURCES + "/users/" +
// user.getUsername() + ".json", StandardCharsets.UTF_8)) {
// GSON.toJson(user, fw);
// System.out.println("User " + user.getUsername() + " successfully created.");
// return true;
// } catch (IOException e) {
// System.out.println("Couldn't add user " + user.getUsername() + " because: " +
// e.getMessage());
// return false;
// }
// }

// /**
// * Checks if the provided username is registered with the provided password.
// * @param username The username of which to check the password of.
// * @param password The password of which to check against the sotred password
// of the username.
// * @return Boolean indicating if the username's stored password matches with
// the provided password.
// * @throws IOException If any issues are encountered during interaction with
// the files.
// */
// public static boolean usernameAndPasswordMatch(final String username, final
// String password) throws IOException {
// String storedPassword;
// try {
// storedPassword = getPersistentProperty("password", PATH_TO_RESOURCES +
// "/users/" + username + ".json");
// if (storedPassword.equals(password)) {
// return true;
// }
// return false;
// } catch (IOException e) {
// throw new IOException("Error reading property", e);
// }
// }

// /**
// * Turns user-friendly category format into the format used for the files.
// * @param category The category to access the file of.
// * @return The textual representation of the file's name.
// */
// public static String getCategoryFilename(final String category) {
// return "/" + category.replace(" ", "_") + ".json";
// }

// /**
// * Fetches a specific property from an specified file without the need to load
// in all the file's content.
// * @param propertyName The property to obtain the value of.
// * @param location The file's absolute path location.
// * @return String representation of the requested value.
// * @throws IOException If any issues are encountered during interaction with
// the files.
// */
// public static String getPersistentProperty(final String propertyName, final
// String location) throws IOException {
// try (JsonReader reader = new JsonReader(new FileReader(location,
// StandardCharsets.UTF_8))) {
// reader.beginObject();
// while (reader.hasNext()) {
// String name = reader.nextName();
// if (name.equals(propertyName)) {
// return name;
// } else {
// reader.skipValue();
// }
// }
// reader.endObject();
// } catch (IOException e) {
// throw new IOException("Error reading property", e);
// }
// throw new IOException("Property not found");
// }

// /**
// * Provides the names of all files in a given directory.
// * @param endpoint The final filepath to the directory of which to find the
// filenames of.
// * @return Set<String> with all filenames in the directory,
// * where the ".json" file extension removed, and all underscores converted to
// spaces.
// * @throws RuntimeException If the provided endpoint is not accessible.
// */
// public static Set<String> getPersistentFilenames(final String endpoint)
// throws RuntimeException {
// File[] nameFiles = new File(PATH_TO_RESOURCES + endpoint).listFiles();
// if (nameFiles != null) {
// Set<String> res = Arrays.stream(nameFiles).map(file -> {
// String name = file.getName();
// String stripJson = name.replace(".json", "");
// String formatSpace = stripJson.replace("_", " ");
// return formatSpace;
// }).collect(Collectors.toSet());
// return res;
// } else {
// throw new RuntimeException("Directory not present in " + PATH_TO_RESOURCES);
// }
// }

// }
