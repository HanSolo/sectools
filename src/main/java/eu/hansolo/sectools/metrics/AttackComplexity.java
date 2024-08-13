package eu.hansolo.sectools.metrics;


public enum AttackComplexity implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "AC";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AttackComplexity(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AttackComplexity fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H" -> { return AttackComplexity.HIGH; }
            case "LOW", "Low", "low", "L"    -> { return AttackComplexity.LOW; }
            default                          -> { return AttackComplexity.NOT_FOUND; }
        }
    }

    public static final AttackComplexity fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AttackComplexity.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AttackComplexity.METRIC_SHORT)) { return AttackComplexity.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
