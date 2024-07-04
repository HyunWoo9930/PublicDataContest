package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MentorCategoryId implements Serializable {
    private Long mentorId;
    private Long subCategoryId;

    public MentorCategoryId() {}

    public MentorCategoryId(Long mentorId, Long subCategoryId) {
        this.mentorId = mentorId;
        this.subCategoryId = subCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MentorCategoryId that = (MentorCategoryId) o;
        return Objects.equals(mentorId, that.mentorId) &&
                Objects.equals(subCategoryId, that.subCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mentorId, subCategoryId);
    }
}

