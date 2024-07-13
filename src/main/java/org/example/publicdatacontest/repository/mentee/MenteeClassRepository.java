package org.example.publicdatacontest.repository.mentee;


import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.mentee.MenteeClassId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeClassRepository extends JpaRepository<MenteeClass, MenteeClassId> {
}
