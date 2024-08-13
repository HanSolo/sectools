package eu.hansolo.sectools.metrics;


public enum AvailabilityImpact implements Metric {
    HIGH("High", "high", "H"),
    LOW("Low", "low", "L"),
    PARTIAL("Partial", "partial", "P"),
    COMPLETE("Complete", "complete", "C"),
    NONE("None", "none", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "A";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AvailabilityImpact(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AvailabilityImpact fromText(final String text) {
        switch (text) {
            case "HIGH", "High", "high", "H"          -> { return AvailabilityImpact.HIGH; }
            case "LOW", "Low", "low", "L"             -> { return AvailabilityImpact.LOW; }
            case "PARTIAL", "Partial", "partial", "P" -> { return AvailabilityImpact.PARTIAL; }
            case "COMPLETE", "Complete", "C"          -> { return AvailabilityImpact.COMPLETE; }
            case "NONE", "None", "none", "N"          -> { return AvailabilityImpact.NONE; }
            default                                   -> { return AvailabilityImpact.NOT_FOUND; }
        }
    }

    public static final AvailabilityImpact fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AvailabilityImpact.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AvailabilityImpact.METRIC_SHORT)) { return AvailabilityImpact.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}

