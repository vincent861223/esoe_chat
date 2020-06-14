module chat_module {
    requires java.sql;
    requires java.desktop;
    requires java.prefs;
	requires com.google.gson;
    exports client;
    exports container;
}