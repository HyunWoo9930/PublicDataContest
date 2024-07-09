package org.example.publicdatacontest.domain.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubCategorySaveRequest {
    List<Long> subCategoryId;
}
