module chat_module {
    requires org.json;
    requires java.sql;
    requires java.desktop;
    requires java.prefs;
    exports client;
    exports container;
}