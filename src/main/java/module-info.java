module eu.hansolo.sectools {
    requires java.net.http;

    requires transitive com.google.gson;

    opens eu.hansolo.sectools;
    opens eu.hansolo.sectools.metrics;
    opens eu.hansolo.sectools.severity;
    opens eu.hansolo.sectools.tools;

    exports eu.hansolo.sectools;
    exports eu.hansolo.sectools.metrics;
    exports eu.hansolo.sectools.severity;
    exports eu.hansolo.sectools.tools;
}