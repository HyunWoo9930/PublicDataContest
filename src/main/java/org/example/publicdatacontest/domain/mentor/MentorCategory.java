package org.example.publicdatacontest.domain.mentor;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.category.SubCategory;

@Entity
@Getter
@Setter
@IdClass(MentorCategoryId.class)
public class MentorCategory {
    @Id
    @Column(name = "mentor_id")
    private Long mentorId;
    @Id
    @Column(name = "subcategory_id")
    private Long subCategoryId;

    @ManyToOne
    @JoinColumn(name = "mentor_id", insertable = false, updatable = false)
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", insertable = false, updatable = false)
    private SubCategory subCategory;
}
