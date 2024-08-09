package org.example.publicdatacontest.repository.util;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.util.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMenteeAndMentorClass(Mentee mentee, MentorClass mentorClass);
}
