package org.example.publicdatacontest.domain.mentee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.category.SubCategory;

import java.security.InvalidParameterException;

@Entity
@Getter
@Setter
@NoArgsConstructor
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



	private MenteeCategory(Long menteeId, Long subCategoryId, Mentee mentee, SubCategory subCategory) {
		if(mentee == null) throw new InvalidParameterException();
		if(subCategory == null) throw new InvalidParameterException();

		this.menteeId = menteeId;
		this.subCategoryId = subCategoryId;
		mentee.addMenteeCategory(this);
		subCategory.addMenteeCategory(this);
	}

	public static MenteeCategory of(Long mId, Long sId, Mentee mentee, SubCategory subCategory) {
		return new MenteeCategory(mId, sId, mentee, subCategory);
	}
}
