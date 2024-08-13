package eu.hansolo.sectools;

import java.util.List;
import java.util.Locale;


public enum CVSS {
    CVSSV20("CVSS 2.0", "cvss_20", "cvssMetricV2", "", List.of("AV", "AC", "Au", "C", "I", "A")),
    CVSSV30("CVSS 3.0", "cvss_30", "cvssMetricV30", "CVSS:3.0", List.of("AV", "AC", "PR", "UI", "S", "C", "I", "A")),
    CVSSV31("CVSS 3.1", "cvss_31", "cvssMetricV31", "CVSS:3.1", List.of("AV", "AC", "PR", "UI", "S", "C", "I", "A")),
    CVSSV40("CVSS 4.0", "cvss_40", "cvssMetricV40", "CVSS:4.0", List.of("AV", "AC", "AT", "PR", "UI", "VC", "VI", "VA", "SC", "SI", "SA")),
    NOT_FOUND("", "", "", "", List.of(""));

    public static final List<CVSS>   VALUES = List.of(CVSSV20, CVSSV30, CVSSV31, CVSSV40);
    public final        String       uiString;
    public final        String       apiString;
    public final        String       metricString;
    public final        String       vectorStringPrefix;
    public final        List<String> mandatoryMetrics;


    CVSS(final String uiString, final String apiString, final String metricString, final String vectorStringPrefix, final List<String> mandatoryMetrics) {
        this.uiString           = uiString;
        this.apiString          = apiString;
        this.metricString       = metricString;
        this.vectorStringPrefix = vectorStringPrefix;
        this.mandatoryMetrics   = mandatoryMetrics;
    }


    public static final CVSS fromText(final String text) {
        if (null == text) { return NOT_FOUND; }
        switch (text) {
            case "cvss_20", "CVSSV2", "cvssV2", "cvssv2", "CVSSV20", "cvssV20", "cvssv20", "cvssMetricV2"  -> { return CVSSV20; }
            case "cvss_30", "CVSSV3", "cvssV3", "cvssv3", "CVSSV30", "cvssV30", "cvssv30", "cvssMetricV30" -> { return CVSSV30; }
            case "cvss_31", "CVSSV31", "cvssV31", "cvssv31", "cvssMetricV31"                               -> { return CVSSV31; }
            case "cvss_40", "CVSSV4", "cvssV4", "cvssv4", "CVSSV40", "cvssV40", "cvssv40", "cvssMetricV40" -> { return CVSSV40; }
            default                                                                                        -> { return NOT_FOUND; }
        }
    }

    public static final String getTextByScore(final double score) {
        final StringBuilder txtBuilder = new StringBuilder(String.format(Locale.US, "%.1f", score));
        if (score >= 0 && score < 1.0) {           // LOW
            return txtBuilder.append(" (LOW)").toString();
        } else if (score >= 1.0 && score < 2.0) {  // LOW
            return txtBuilder.append(" (LOW)").toString();
        } else if (score >= 2.0 && score < 3.0) {  // LOW
            return txtBuilder.append(" (LOW)").toString();
        } else if (score >= 3.0 && score < 4.0) {  // LOW
            return txtBuilder.append(" (LOW)").toString();
        } else if (score >= 4.0 && score < 5.0) {  // MEDIUM
            return txtBuilder.append(" (MEDIUM)").toString();
        } else if (score >= 5.0 && score < 6.0) {  // MEDIUM
            return txtBuilder.append(" (MEDIUM)").toString();
        } else if (score >= 6.0 && score < 7.0) {  // MEDIUM
            return txtBuilder.append(" (MEDIUM)").toString();
        } else if (score >= 7.0 && score < 8.0) {  // HIGH
            return txtBuilder.append(" (HIGH)").toString();
        } else if (score >= 8.0 && score < 9.0) {  // HIGH
            return txtBuilder.append(" (HIGH)").toString();
        } else if (score >= 9.0 && score <= 10.0) { // CRITICAL
            return txtBuilder.append(" (CRITICAL)").toString();
        } else {
            return txtBuilder.toString();
        }
    }
}
