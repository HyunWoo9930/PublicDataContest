package org.example.publicdatacontest.domain.util;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.MenteeCategoryId;
import org.example.publicdatacontest.domain.mentor.MentorCategoryId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    private Long menteeId;
    private Long classId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LikeId that = (LikeId)o;
        return Objects.equals(menteeId, that.menteeId) &&
                Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menteeId, classId);
    }
}
