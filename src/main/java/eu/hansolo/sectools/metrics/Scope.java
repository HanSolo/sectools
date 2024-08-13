package eu.hansolo.sectools.metrics;


public enum Scope implements Metric {
    UNCHANGED("Unchanged", "unchanged", "U"),
    CHANGED("Changed", "changed", "C"),
    PARTIAL("Partial", "partial", "P"),
    COMPLETE("Complete", "complete", "C"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "S";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    Scope(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final Scope fromText(final String text) {
        switch (text) {
            case "UNCHANGED", "Unchanged", "unchanged", "U" -> { return Scope.UNCHANGED; }
            case "CHANGED", "Changed", "changed", "C"       -> { return Scope.CHANGED; }
            case "PARTIAL", "Partial", "partial", "P"       -> { return Scope.PARTIAL; }
            case "COMPLETE", "Complete"                     -> { return Scope.COMPLETE; }
            default                                         -> { return Scope.NOT_FOUND; }
        }
    }

    public static final Scope fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return Scope.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().equals(IntegrityImpact.METRIC_SHORT)) { return Scope.NOT_FOUND; }

        System.out.println("Metric: " + parts[0] + " -> " + parts[1]);

        return fromText(parts[1].strip());
    }
}
