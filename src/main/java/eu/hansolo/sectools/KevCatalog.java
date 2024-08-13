package eu.hansolo.sectools;

import java.time.LocalDateTime;
import java.util.List;


public record KevCatalog(String title, String catalogVersion, LocalDateTime dateReleased, long count, List<Vulnerability> vulnerabilities) { }
