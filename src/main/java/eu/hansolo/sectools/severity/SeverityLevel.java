package eu.hansolo.sectools.severity;

import eu.hansolo.sectools.CVSS;
import eu.hansolo.sectools.OutputFormat;

import java.util.List;
import java.util.Locale;

import static eu.hansolo.sectools.Constants.COMMA;
import static eu.hansolo.sectools.Constants.COMMA_NEW_LINE;
import static eu.hansolo.sectools.Constants.CURLY_BRACKET_CLOSE;
import static eu.hansolo.sectools.Constants.CURLY_BRACKET_OPEN;
import static eu.hansolo.sectools.Constants.INDENTED_QUOTES;
import static eu.hansolo.sectools.Constants.NEW_LINE;
import static eu.hansolo.sectools.Constants.QUOTES;
import static eu.hansolo.sectools.Constants.QUOTES_COLON_QUOTES;


public enum SeverityLevel {
    LOW(SeverityName.LOW.name(), SeverityName.LOW.name().toLowerCase(Locale.ENGLISH), 0.0, 3.9, 0.1, 3.9, 2),
    MEDIUM(SeverityName.MEDIUM.name(), SeverityName.MEDIUM.name().toLowerCase(Locale.ENGLISH), 4.0, 6.9, 4.0, 6.9,3),
    HIGH(SeverityName.HIGH.name(), SeverityName.HIGH.name().toLowerCase(Locale.ENGLISH), 7.0, 10.0, 7.0, 8.9, 4),
    CRITICAL(SeverityName.CRITICAL.name(), SeverityName.CRITICAL.name().toLowerCase(Locale.ENGLISH), 10.0, 10.0, 9.0, 10.0, 5),
    NONE("-", "", 0, 0, 0, 0,1),
    NOT_FOUND("", "", 0, 0, 0, 0, 0);

    public static final List<SeverityLevel> VALUES = List.of(NONE, LOW, MEDIUM, HIGH, CRITICAL);
    private final String  uiString;
    private final String  apiString;
    private final double  minScoreV2;
    private final double  maxScoreV2;
    private final double  minScoreV3;
    private final double  maxScoreV3;
    private final Integer order;


    SeverityLevel(final String uiString, final String apiString, final double minScoreV2, final double maxScoreV2, final double minScoreV3, final double maxScoreV3, final Integer order) {
        this.uiString   = uiString;
        this.apiString  = apiString;
        this.minScoreV2 = minScoreV2;
        this.maxScoreV2 = maxScoreV2;
        this.minScoreV3 = minScoreV3;
        this.maxScoreV3 = maxScoreV3;
        this.order      = order;
    }

    public double getMinScoreV2() { return minScoreV2; }
    public double getMaxScoreV2() { return maxScoreV2; }

    public double getMinScoreV3() { return minScoreV3; }
    public double getMaxScoreV3() { return maxScoreV3; }


    public int getOrder() { return order; }


    public String getUiString() { return uiString; }

    public String getApiString() { return apiString; }


    public String toString(final OutputFormat outputFormat) {
        StringBuilder msgBuilder = new StringBuilder();
        switch(outputFormat) {
            case FULL, REDUCED, REDUCED_ENRICHED ->
            msgBuilder.append(CURLY_BRACKET_OPEN).append(NEW_LINE)
                      .append(INDENTED_QUOTES).append("name").append(QUOTES_COLON_QUOTES).append(name()).append(QUOTES).append(COMMA_NEW_LINE)
                      .append(INDENTED_QUOTES).append("ui_string").append(QUOTES_COLON_QUOTES).append(uiString).append(QUOTES).append(COMMA_NEW_LINE)
                      .append(INDENTED_QUOTES).append("api_string").append(QUOTES_COLON_QUOTES).append(apiString).append(QUOTES).append(NEW_LINE)
                      .append(CURLY_BRACKET_CLOSE);
            default ->
            msgBuilder.append(CURLY_BRACKET_OPEN)
                      .append(QUOTES).append("name").append(QUOTES_COLON_QUOTES).append(name()).append(QUOTES).append(COMMA)
                      .append(QUOTES).append("ui_string").append(QUOTES_COLON_QUOTES).append(uiString).append(QUOTES).append(COMMA)
                      .append(QUOTES).append("api_string").append(QUOTES_COLON_QUOTES).append(apiString).append(QUOTES)
                      .append(CURLY_BRACKET_CLOSE);
        }
        return msgBuilder.toString();
    }

    @Override public String toString() { return toString(OutputFormat.FULL_COMPRESSED); }

    public int compareToSeverityLevel(final SeverityLevel other) {
        return order.compareTo(other.order);
    }


    /**
     * Returns SeverityLevel parsed from a given text
     * @param text Name of the severity level to parse usually the api_string of a severity e.g. 'low'
     * @return Severity parsed from a given text
     */
    public static SeverityLevel fromText(final String text) {
        if (null == text) { return NOT_FOUND; }
        switch (text) {
            case "low", "LOW", "Low"                -> { return LOW; }
            case "medium", "MEDIUM", "Medium"       -> { return MEDIUM; }
            case "high", "HIGH", "High"             -> { return HIGH; }
            case "critical", "CRITICAL", "Critical" -> { return CRITICAL; }
            default                                 -> { return NOT_FOUND; }
        }
    }

    /**
     * Returns a Severity parsed from the given score and cvss version
     * @param score The CVSS score (0.0 - 10.0)
     * @param cvss  The CVSS version (CVSS20, CVSS30, CVSSV31, CVSS40)
     * @return Severity parsed from a given score and cvss version
     */
    public static SeverityLevel fromScore(final double score, final CVSS cvss) {
        switch (cvss) {
            case CVSSV20 -> {
                if (score >= 0 && score <= 3.9) {
                    return SeverityLevel.LOW;
                } else if (score > 3.9 && score <= 6.9) {
                    return SeverityLevel.MEDIUM;
                } else if (score > 6.9 && score <= 10.0) {
                    return SeverityLevel.HIGH;
                } else {
                    return SeverityLevel.NOT_FOUND;
                }
            }
            case CVSSV30, CVSSV31, CVSSV40 -> {
                if (score <= 0) {
                    return SeverityLevel.NONE;
                } else if (score > 0 && score <= 3.9) {
                    return SeverityLevel.LOW;
                } else if (score > 3.9 && score <= 6.9) {
                    return SeverityLevel.MEDIUM;
                } else if (score > 6.9 && score < 8.9) {
                    return SeverityLevel.HIGH;
                } else if (score > 8.9 && score <= 10.0) {
                    return SeverityLevel.CRITICAL;
                } else {
                    return SeverityLevel.NOT_FOUND;
                }
            }
        }
        return SeverityLevel.NOT_FOUND;
    }
}
