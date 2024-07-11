package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryResponse {

    private Long subCategoryId;
    private String subCategoryName;

    public SubCategoryResponse(Long id, String name) {
        this.subCategoryId = id;
        this.subCategoryName = name;
    }
}
