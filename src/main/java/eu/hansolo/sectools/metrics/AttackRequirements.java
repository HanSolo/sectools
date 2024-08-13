package eu.hansolo.sectools.metrics;


public enum AttackRequirements implements Metric {
    NONE("None", "none", "N"),
    PRESENT("Present", "present", "P"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "AT";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AttackRequirements(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AttackRequirements fromText(final String text) {
        switch (text) {
            case "NONE", "None", "none", "N"          -> { return AttackRequirements.NONE; }
            case "PRESENT", "Present", "present", "P" -> { return AttackRequirements.PRESENT; }
            default                                   -> { return AttackRequirements.NOT_FOUND; }
        }
    }

    public static final AttackRequirements fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AttackRequirements.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AttackRequirements.METRIC_SHORT)) { return AttackRequirements.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
