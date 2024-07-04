package org.example.publicdatacontest.domain.mentee;

import java.io.Serializable;
import java.util.Objects;

public class MenteeClassId implements Serializable {
	private Long classId;
	private Long menteeId;

	public MenteeClassId() {
	}

	public MenteeClassId(Long classId, Long menteeId) {
		this.classId = classId;
		this.menteeId = menteeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MenteeClassId that = (MenteeClassId)o;
		return Objects.equals(classId, that.classId) &&
			Objects.equals(menteeId, that.menteeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(classId, menteeId);
	}
}
