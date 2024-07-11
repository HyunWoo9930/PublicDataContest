package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.MenteeCategory;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MenteeCategoryResponse {

    private String categoryName;
    private String subCategoryName;

    public MenteeCategoryResponse(MenteeCategory menteeCategory) {

        this.categoryName = menteeCategory.getSubCategory().getCategory().getName();
        this.subCategoryName = menteeCategory.getSubCategory().getName();
    }
}
