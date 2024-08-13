package eu.hansolo.sectools.metrics;


public enum PrivilegesRequired implements Metric {
    NONE("None", "none", "N"),
    LOW("Low", "low", "L"),
    HIGH("High", "high", "H"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "PR";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    PrivilegesRequired(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }


    public static final PrivilegesRequired fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return PrivilegesRequired.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return PrivilegesRequired.LOW; }
            case "NONE", "None", "none", "N" -> { return PrivilegesRequired.NONE; }
            default                          -> { return PrivilegesRequired.NOT_FOUND; }
        }
    }

    public static final PrivilegesRequired fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return PrivilegesRequired.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(PrivilegesRequired.METRIC_SHORT)) { return PrivilegesRequired.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
