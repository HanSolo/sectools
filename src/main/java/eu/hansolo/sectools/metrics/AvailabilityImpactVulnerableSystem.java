package eu.hansolo.sectools.metrics;


public enum AvailabilityImpactVulnerableSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "VA";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AvailabilityImpactVulnerableSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AvailabilityImpactVulnerableSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return AvailabilityImpactVulnerableSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return AvailabilityImpactVulnerableSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return AvailabilityImpactVulnerableSystem.NONE; }
            default                          -> { return AvailabilityImpactVulnerableSystem.NOT_FOUND; }
        }
    }

    public static final AvailabilityImpactVulnerableSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AvailabilityImpactVulnerableSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AvailabilityImpactVulnerableSystem.METRIC_SHORT)) { return AvailabilityImpactVulnerableSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
