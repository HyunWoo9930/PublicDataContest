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

    private Set<String[]> menteeCategories;

    public MenteeCategoryResponse(Set<MenteeCategory> menteeCategory) {
        Set<String[]> arr = new HashSet<>();
        for (MenteeCategory mc : menteeCategory) {
            String[] category = new String[2];
            category[0] = mc.getSubCategory().getCategory().getName();
            category[1] = mc.getSubCategory().getName();
            arr.add(category);
        }
        this.menteeCategories = arr;
    }
}
