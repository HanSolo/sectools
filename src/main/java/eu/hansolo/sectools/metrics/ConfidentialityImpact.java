package eu.hansolo.sectools.metrics;


public enum ConfidentialityImpact implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    PARTIAL("Partial", "partial", "P"),
    COMPLETE("Complete", "complete", "C"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "C";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    ConfidentialityImpact(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final ConfidentialityImpact fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H"          -> { return ConfidentialityImpact.HIGH; }
            case "LOW", "Low", "low", "L"             -> { return ConfidentialityImpact.LOW; }
            case "PARTIAL", "Partial", "partial", "P" -> { return ConfidentialityImpact.PARTIAL; }
            case "COMPLETE", "Complete", "C"          -> { return ConfidentialityImpact.COMPLETE; }
            case "NONE", "None", "none", "N"          -> { return ConfidentialityImpact.NONE; }
            default                                   -> { return ConfidentialityImpact.NOT_FOUND; }
        }
    }

    public static final ConfidentialityImpact fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return ConfidentialityImpact.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(ConfidentialityImpact.METRIC_SHORT)) { return ConfidentialityImpact.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
