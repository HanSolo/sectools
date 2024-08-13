package eu.hansolo.sectools.metrics;


public enum IntegrityImpactSubsequentSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "SI";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    IntegrityImpactSubsequentSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final IntegrityImpactSubsequentSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return IntegrityImpactSubsequentSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return IntegrityImpactSubsequentSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return IntegrityImpactSubsequentSystem.NONE; }
            default                          -> { return IntegrityImpactSubsequentSystem.NOT_FOUND; }
        }
    }

    public static final IntegrityImpactSubsequentSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return IntegrityImpactSubsequentSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(IntegrityImpactSubsequentSystem.METRIC_SHORT)) { return IntegrityImpactSubsequentSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
