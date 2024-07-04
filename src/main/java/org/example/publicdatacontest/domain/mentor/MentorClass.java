package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.util.Review;

import java.util.Set;

@Entity
public class MentorClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long classId;
	private String name;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	private String location;
	private Long time;
	private Long price;
	private String description;

	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private SubCategory subCategory;

	private Boolean active;

	@OneToMany(mappedBy = "mentorClass")
	private Set<MenteeClass> menteeClasses;

	@OneToMany(mappedBy = "mentorClass")
	private Set<Review> reviews;

	// Getters and setters
}
