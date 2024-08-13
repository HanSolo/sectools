package eu.hansolo.sectools.metrics;


public enum IntegrityImpact implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    PARTIAL("Partial", "partial", "P"),
    COMPLETE("Complete", "complete", "C"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "I";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    IntegrityImpact(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final IntegrityImpact fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H"          -> { return IntegrityImpact.HIGH; }
            case "LOW", "Low", "low", "L"             -> { return IntegrityImpact.LOW; }
            case "PARTIAL", "Partial", "partial", "P" -> { return IntegrityImpact.PARTIAL; }
            case "COMPLETE", "Complete", "C"          -> { return IntegrityImpact.COMPLETE; }
            case "NONE", "None", "none", "N"          -> { return IntegrityImpact.NONE; }
            default                                   -> { return IntegrityImpact.NOT_FOUND; }
        }
    }

    public static final IntegrityImpact fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return IntegrityImpact.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(IntegrityImpact.METRIC_SHORT)) { return IntegrityImpact.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
