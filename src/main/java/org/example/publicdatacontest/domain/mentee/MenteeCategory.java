package org.example.publicdatacontest.domain.mentee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeCategoryId;

@Entity
@Getter
@Setter
@IdClass(MenteeCategoryId.class)
public class MenteeCategory {
	@Id
	@Column(name = "mentee_id")
	private Long menteeId;

	@Id
	@Column(name = "subcategory_id")
	private Long subCategoryId;

	@ManyToOne
	@JoinColumn(name = "mentee_id", insertable = false, updatable = false)
	private Mentee mentee;

	@ManyToOne
	@JoinColumn(name = "subcategory_id", insertable = false, updatable = false)
	private SubCategory subCategory;
}
