package org.example.publicdatacontest.repository.util;

import org.example.publicdatacontest.domain.util.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
