package org.example.publicdatacontest.repository.util;

import org.example.publicdatacontest.domain.util.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
