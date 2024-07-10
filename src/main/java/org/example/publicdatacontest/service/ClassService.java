package org.example.publicdatacontest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.dto.requestDTO.MentorClassRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorClassResponse;
import org.example.publicdatacontest.repository.category.SubCategoryRepository;
import org.example.publicdatacontest.repository.mentor.MentorClassRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClassService {

	private final MentorRepository mentorRepository;

	private final MentorClassRepository mentorClassRepository;

	private final SubCategoryRepository subCategoryRepository;

	public ClassService(MentorRepository mentorRepository, MentorClassRepository mentorClassRepository,
		SubCategoryRepository subCategoryRepository) {
		this.mentorRepository = mentorRepository;
		this.mentorClassRepository = mentorClassRepository;
		this.subCategoryRepository = subCategoryRepository;
	}

	@Transactional
	public void makeMentoring(UserDetails userDetails, MentorClassRequest mentorClassRequest) {
		MentorClass mentorClass = new MentorClass();

		Optional<Mentor> mentor = mentorRepository.findByUserId(userDetails.getUsername());
		if (mentor.isEmpty()) {
			throw new RuntimeException("멘토가 아니거나, 유저가 없습니다.");
		}

		mentorClass.setMentor(mentor.get());
		mentorClass.setActive(true);
		mentorClass.setName(mentorClassRequest.getName());
		mentorClass.setDescription(mentorClassRequest.getDescription());
		mentorClass.setLocation(mentorClassRequest.getLocation());
		mentorClass.setTime(mentorClassRequest.getTime());
		mentorClass.setPrice(mentorClassRequest.getPrice());

		System.out.println("mentor = " + mentor.get().getMentorCategories());

		SubCategory subCategory = mentor.get().getMentorCategories().stream()
			.filter(mentorCategory -> mentorCategory.getSubCategory()
				.getSubCategoryId()
				.equals(mentorClassRequest.getSubcategoryId()))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("멘토가 선택한 소분류에 대한 권한이 없습니다.")).getSubCategory();

		mentorClass.setSubCategory(subCategory);

		mentorClassRepository.save(mentorClass);
	}

	public List<MentorClassResponse> mentoringList() {
		List<MentorClass> classes = mentorClassRepository.findAll();
		List<MentorClassResponse> mentorClassResponses = new ArrayList<>();
		classes.forEach(mentorClass -> {
			MentorClassResponse mentorClassResponse = new MentorClassResponse(mentorClass);
			mentorClassResponses.add(mentorClassResponse);
		});
		return mentorClassResponses;
	}

	public MentorClassResponse mentoringDetail(Long classId) {
		MentorClass mentorClass = mentorClassRepository.findById(classId)
			.orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

		return new MentorClassResponse(mentorClass);
	}

	public List<MentorClassResponse> mentoringListByCategory(Long categoryId) {
		List<MentorClass> classes = mentorClassRepository.findAll().stream()
				.filter(i -> i.getSubCategory().getCategory().getCategoryId().equals(categoryId))
				.toList();

		List<MentorClassResponse> mentorClassResponses = new ArrayList<>();

		classes.forEach(mentorClass -> {
			MentorClassResponse mentorClassResponse = new MentorClassResponse(mentorClass);
			mentorClassResponses.add(mentorClassResponse);
		});

		return mentorClassResponses;
	}

	public List<MentorClassResponse> mentoringListBySubCategory(Long subCategoryId) {
		List<MentorClass> classes = mentorClassRepository.findAll().stream()
				.filter(i -> i.getSubCategory().getSubCategoryId().equals(subCategoryId))
				.toList();

		List<MentorClassResponse> mentorClassResponses = new ArrayList<>();

		classes.forEach(mentorClass -> {
			MentorClassResponse mentorClassResponse = new MentorClassResponse(mentorClass);
			mentorClassResponses.add(mentorClassResponse);
		});

		return mentorClassResponses;
	}
}
