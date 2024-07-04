package org.example.publicdatacontest.domain.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentor.MentorBadge;

import java.util.Set;

@Entity
@Getter
@Setter
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long badgeId;
	private Long level;

	@OneToMany(mappedBy = "badge")
	private Set<MentorBadge> mentorBadges;
}
