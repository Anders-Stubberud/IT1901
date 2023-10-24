module project.core {
    exports core;

    requires transitive com.google.gson;
    requires project.persistence;
    requires transitive project.types;

    opens core to com.google.gson;
}
