package eu.hansolo.sectools.metrics;


public enum AttackVector implements Metric {
    NETWORK("Network", "network", "N"),
    ADJACENT_NETWORK("Adjacent network", "adjacent_network", "AN"),
    ADJACENT("Adjacent", "adjacent", "A"),
    LOCAL("Local", "local", "L"),
    PHYSICAL("Network", "network", "N"),
    NOT_FOUND("", "", "");

    public static final String METRIC_SHORT = "AV";
    public final String uiString;
    public final String apiString;
    public final String shortForm;


    AttackVector(final String uiString, final String apiString, final String shortForm) {
        this.uiString  = uiString;
        this.apiString = apiString;
        this.shortForm = shortForm;
    }


    @Override public String getMetricShort() { return METRIC_SHORT; }

    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    public static final AttackVector fromText(final String text) {
        switch (text) {
            case "NETWORK", "Network", "network", "N"         -> { return AttackVector.NETWORK; }
            case "ADJACENT", "Adjacent", "adjacent", "A"      -> { return AttackVector.ADJACENT; }
            case "ADJACENT_NETWORK", "adjacent_network", "AN" -> { return AttackVector.ADJACENT_NETWORK; }
            case "LOCAL", "Local", "local", "L"               -> { return AttackVector.LOCAL; }
            case "PHYSICAL" ,"Physical", "physical", "P"      -> { return AttackVector.PHYSICAL; }
            default                                           -> { return AttackVector.NOT_FOUND; }
        }
    }

    public static final AttackVector fromVectorString(final String text) {
        if (null == text || !text.contains(":")) { return AttackVector.NOT_FOUND; }
        final String[] parts = text.split(":");
        if (!parts[0].strip().toUpperCase().equals(AttackVector.METRIC_SHORT)) { return AttackVector.NOT_FOUND; }
        return fromText(parts[1].strip());
    }
}
