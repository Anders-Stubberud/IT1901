module project.core {
    exports core;

    // requires com.google.gson;
    requires transitive com.google.gson;

    opens core to com.google.gson;
}
