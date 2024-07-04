package org.example.publicdatacontest.repository.util;

import org.example.publicdatacontest.domain.util.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
