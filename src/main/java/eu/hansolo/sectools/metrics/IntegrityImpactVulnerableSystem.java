package eu.hansolo.sectools.metrics;


public enum IntegrityImpactVulnerableSystem implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "VI";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    IntegrityImpactVulnerableSystem(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final IntegrityImpactVulnerableSystem fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return IntegrityImpactVulnerableSystem.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return IntegrityImpactVulnerableSystem.LOW; }
            case "NONE", "None", "none", "N" -> { return IntegrityImpactVulnerableSystem.NONE; }
            default                          -> { return IntegrityImpactVulnerableSystem.NOT_FOUND; }
        }
    }

    public static final IntegrityImpactVulnerableSystem fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return IntegrityImpactVulnerableSystem.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(IntegrityImpactVulnerableSystem.METRIC_SHORT)) { return IntegrityImpactVulnerableSystem.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
