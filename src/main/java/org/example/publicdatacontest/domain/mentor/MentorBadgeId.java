package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentee.MenteeClassId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MentorBadgeId implements Serializable {
	private Long mentorId;
	private Long badgeId;

	public MentorBadgeId() {
	}

	public MentorBadgeId(Long mentorId, Long badgeId) {
		this.mentorId = mentorId;
		this.badgeId = badgeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MentorBadgeId that = (MentorBadgeId)o;
		return Objects.equals(mentorId, that.mentorId) &&
			Objects.equals(badgeId, that.badgeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mentorId, badgeId);
	}
}
