module dataprocessing {
    requires org.apache.httpcomponents.core5.httpcore5;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;
    requires org.slf4j;
    exports dataparsingutils;
    exports datarequesting;
}