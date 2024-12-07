module dataprocessing {
    requires org.apache.httpcomponents.core5.httpcore5;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
    requires jdk.httpserver;
    exports dataparsingutils;
    exports datarequesting;
}