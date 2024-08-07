package org.example.publicdatacontest.domain.mentor;

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

	private MentorCategory(Long mentorId, Long subCategoryId, Mentor mentor, SubCategory subCategory) {
		if(mentor == null) throw new InvalidParameterException();
		if(subCategory == null) throw new InvalidParameterException();

		this.mentorId = mentorId;
		this.subCategoryId = subCategoryId;
		mentor.addMentorCategory(this);
		subCategory.addMentorCategory(this);
	}

	public static MentorCategory of(Long mId, Long sId, Mentor mentor, SubCategory subCategory) {
		return new MentorCategory(mId, sId, mentor, subCategory);
	}
}
