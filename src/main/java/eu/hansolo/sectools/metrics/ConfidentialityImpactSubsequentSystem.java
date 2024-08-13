package eu.hansolo.sectools.metrics;


public enum ConfidentialityImpactSubsequentSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "SC";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    ConfidentialityImpactSubsequentSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final ConfidentialityImpactSubsequentSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return ConfidentialityImpactSubsequentSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return ConfidentialityImpactSubsequentSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return ConfidentialityImpactSubsequentSystem.NONE; }
            default                          -> { return ConfidentialityImpactSubsequentSystem.NOT_FOUND; }
        }
    }

    public static final ConfidentialityImpactSubsequentSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return ConfidentialityImpactSubsequentSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(ConfidentialityImpactSubsequentSystem.METRIC_SHORT)) { return ConfidentialityImpactSubsequentSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
