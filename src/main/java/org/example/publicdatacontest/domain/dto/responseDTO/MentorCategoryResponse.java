package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentor.MentorCategory;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class MentorCategoryResponse {

    private Set<String[]> mentorCategories;

    public MentorCategoryResponse(Set<MentorCategory> mentorCategory) {
        Set<String[]> arr = new HashSet<>();
        for (MentorCategory mc : mentorCategory) {
            String[] category = new String[2];
            category[0] = mc.getSubCategory().getCategory().getName();
            category[1] = mc.getSubCategory().getName();
            arr.add(category);
        }
        this.mentorCategories = arr;
    }
}
