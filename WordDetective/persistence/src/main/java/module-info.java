module project.persistence {
    exports persistence;

    requires transitive com.google.gson;

    opens persistence to com.google.gson;
}
