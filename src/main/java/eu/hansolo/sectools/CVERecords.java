package eu.hansolo.sectools;

public class CVERecords {
    public record CVERecord(String dataType, String dataVersion, CVEMetadata cveMetadata, Container containers) {}

    public record CVEMetadata(String state, String cveId, String assignerOrgId, String assignerShortName, String dateUpdated, String dateReserved, String datePublished) {}

    public record Container(CNA cna, ADP[] adp) {}

    public record CNA (ProviderMetadata providerMetadata, Description[] descriptions, Affected[] affected, Reference[] references, ProblemType[] problemTypes, Metric[] metrics, TimelineEntry[] timeline) {}

    public record ProviderMetadata(String orgId, String shortName, String dateUpdated) {}

    public record Description(String type, String cweId, String lang, String value, String description, SupportingMedia[] supportingMedia) {}

    public record SupportingMedia(String type, boolean base64, String value) {}

    public record Affected(String vendor, String product, String[] platforms, String collectionURL, String packageName, String repo, String[] modules, String[] programFiles, ProgramRoutine[] programRoutines, Version[] versions, String defaultStatus) {}

    public record ProgramRoutine(String name) {}

    public record Version(String version, String status, String lessThan, String versionType, Change[] changes) {}

    public record Change(String at, String status) {}

    public record Reference(String url) {}

    public record ProblemType(Description[] descriptions) {}

    public record Metric(String format, Scenario scenario, CVSSV40 cvssV4_0, CVSSV31 cvssV3_1, CVSSV30 cvssV3_0, CVSSV20 cvssV2_0) {}

    public record CVSSV20(String version, String accessVector, String accessComplexity, String authentication, String confidentialityImpact, String integrityImpact, String availabilityImpact, String exploitability, String vectorString, double baseScore, String baseSeverity) {}

    public record CVSSV30(String version, String attackVector, String attackComplexity, String privilegesRequired, String userInteraction, String scope, String confidentialityImpact, String integrityImpact, String availabilityImpact, String exploitMaturity, String vectorString, double baseScore, String baseSeverity) {}

    public record CVSSV31(String version, String attackVector, String attackComplexity, String privilegesRequired, String userInteraction, String scope, String confidentialityImpact, String integrityImpact, String availabilityImpact, String exploitMaturity, String vectorString, double baseScore, String baseSeverity) {}

    public record CVSSV40(String version, String attackVector, String attackComplexity, String attackRequirements, String privilegesRequired, String userInteraction, String vulnConfidentialityImpact, String vulnIntegrityImpact, String vulnAvailabilityImpact, String subConfidentialityImpact, String subIntegrityImpact, String subAvailabilityImpact, String exploitMaturity, String vectorString, double baseScore, String baseSeverity) {}

    public record TimelineEntry(String time, String lang, String value) {}

    public record Scenario(String lang, String value) {}

    public record ADP(ProviderMetadata providerMetadata, String title, Reference[] references) {}
}
