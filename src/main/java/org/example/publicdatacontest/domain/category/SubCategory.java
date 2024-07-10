package org.example.publicdatacontest.domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.example.publicdatacontest.domain.mentor.MentorCategory;
import org.example.publicdatacontest.domain.mentor.MentorClass;

import java.util.Set;

@Entity
@Getter
@Setter
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subCategoryId;
	private String name;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "subCategory")
	private Set<MentorClass> mentorClasses;

	@OneToMany(mappedBy = "subCategory")
	private Set<MentorCategory> mentorCategories;

	@OneToMany(mappedBy = "subCategory")
	private Set<MenteeCategory> menteeCategories;



	// 연관관계 메서드
	// 1. MentorCategory를 받아와서 set에 저장
	// 2. SubCategory 객체를 MentorCategory의 SubCategory에 저장
	public void addMentorCategory(MentorCategory mentorCategory) {
		this.mentorCategories.add(mentorCategory);
		mentorCategory.setSubCategory(this);
	}

	public void addMenteeCategory(MenteeCategory menteeCategory) {
		this.menteeCategories.add(menteeCategory);
		menteeCategory.setSubCategory(this);
	}
}
