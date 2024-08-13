package eu.hansolo.sectools.metrics;


public enum ConfidentialityImpactVulnerableSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "VC";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    ConfidentialityImpactVulnerableSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final ConfidentialityImpactVulnerableSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return ConfidentialityImpactVulnerableSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return ConfidentialityImpactVulnerableSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return ConfidentialityImpactVulnerableSystem.NONE; }
            default                          -> { return ConfidentialityImpactVulnerableSystem.NOT_FOUND; }
        }
    }

    public static final ConfidentialityImpactVulnerableSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return ConfidentialityImpactVulnerableSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(ConfidentialityImpactVulnerableSystem.METRIC_SHORT)) { return ConfidentialityImpactVulnerableSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
