package eu.hansolo.sectools.metrics;


public enum AvailabilityImpactSubsequentSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "SA";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AvailabilityImpactSubsequentSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AvailabilityImpactSubsequentSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return AvailabilityImpactSubsequentSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return AvailabilityImpactSubsequentSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return AvailabilityImpactSubsequentSystem.NONE; }
            default                          -> { return AvailabilityImpactSubsequentSystem.NOT_FOUND; }
        }
    }

    public static final AvailabilityImpactSubsequentSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AvailabilityImpactSubsequentSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AvailabilityImpactSubsequentSystem.METRIC_SHORT)) { return AvailabilityImpactSubsequentSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
