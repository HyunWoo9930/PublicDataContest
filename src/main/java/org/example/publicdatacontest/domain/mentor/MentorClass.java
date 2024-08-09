package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.util.Review;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
	private Long likeCount;

	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private SubCategory subCategory;

	private Boolean active;

	@OneToMany(mappedBy = "mentorClass")
	private Set<MenteeClass> menteeClasses;

	@OneToMany(mappedBy = "mentorClass")
	private Set<Review> reviews;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;


	public void addReview(Review review) {
		this.reviews.add(review);
		review.setMentorClass(this);
	}

	public void likeCountUp() {
		this.likeCount++;
	}

	public void likeCountDown() {
		this.likeCount--;
	}
}
