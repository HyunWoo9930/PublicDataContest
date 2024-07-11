package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.example.publicdatacontest.domain.mentor.MentorCategory;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class MentorCategoryResponse {

    private String categoryName;
    private String subCategoryName;

    public MentorCategoryResponse(MentorCategory mentorCategory) {

        this.categoryName = mentorCategory.getSubCategory().getCategory().getName();
        this.subCategoryName = mentorCategory.getSubCategory().getName();
    }
}
