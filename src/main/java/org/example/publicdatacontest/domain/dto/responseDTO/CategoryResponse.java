package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.publicdatacontest.domain.category.Category;
import org.example.publicdatacontest.domain.category.SubCategory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {

    private Long categoryId;
    private String categoryName;

    private List<SubCategoryResponse> subCategories;

    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getName();

        List<SubCategoryResponse> arr = new ArrayList<>();

        for (SubCategory sc : category.getSubCategories()) {
            arr.add(new SubCategoryResponse(sc.getSubCategoryId(), sc.getName()));
        }

        this.subCategories = arr;

    }
}
