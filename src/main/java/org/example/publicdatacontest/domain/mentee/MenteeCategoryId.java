package org.example.publicdatacontest.domain.mentee;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MenteeCategoryId implements Serializable {
    private Long menteeId;
    private Long subCategoryId;

    public MenteeCategoryId() {}

    public MenteeCategoryId(Long menteeId, Long subCategoryId) {
        this.menteeId = menteeId;
        this.subCategoryId = subCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenteeCategoryId that = (MenteeCategoryId) o;
        return Objects.equals(menteeId, that.menteeId) &&
                Objects.equals(subCategoryId, that.subCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menteeId, subCategoryId);
    }
}
