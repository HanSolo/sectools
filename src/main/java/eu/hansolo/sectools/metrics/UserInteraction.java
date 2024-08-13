package eu.hansolo.sectools.metrics;


public enum UserInteraction implements Metric {
    NONE("None", "none", "N"),
    REQUIRED("Required", "required", "R"),
    PASSIVE("Passive", "passive", "P"),
    ACTIVE("Active", "active", "A"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "UI";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    UserInteraction(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final UserInteraction fromText(final String text) {
        switch (text) {
            case "REQUIRED", "Required", "required", "R" -> { return UserInteraction.REQUIRED; }
            case "NONE", "None", "none", "N"             -> { return UserInteraction.NONE; }
            case "PASSIVE", "Passive", "passive", "P"    -> { return UserInteraction.PASSIVE; }
            case "ACTIVE", "Active", "active", "A"       -> { return UserInteraction.ACTIVE; }
            default                                      -> { return UserInteraction.NOT_FOUND; }
        }
    }

    public static final UserInteraction fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return UserInteraction.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(UserInteraction.METRIC_SHORT)) { return UserInteraction.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
