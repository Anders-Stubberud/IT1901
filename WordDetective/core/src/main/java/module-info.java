module project.core {
    exports core;

    requires transitive com.google.gson;
    requires project.persistence;

    opens core to com.google.gson;
}
