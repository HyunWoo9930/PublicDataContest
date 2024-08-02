package org.example.publicdatacontest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.dto.requestDTO.MentorClassRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorClassResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.mentee.MenteeClassId;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.repository.category.SubCategoryRepository;
import org.example.publicdatacontest.repository.mentee.MenteeClassRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
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

	private final MenteeRepository menteeRepository;

	private final MenteeClassRepository menteeClassRepository;

	public ClassService(MentorRepository mentorRepository, MentorClassRepository mentorClassRepository,
		SubCategoryRepository subCategoryRepository, MenteeRepository menteeRepository,
		MenteeClassRepository menteeClassRepository) {
		this.mentorRepository = mentorRepository;
		this.mentorClassRepository = mentorClassRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.menteeRepository = menteeRepository;
		this.menteeClassRepository = menteeClassRepository;
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

	public void updateMentoring(UserDetails userDetails, Long classId) {
		Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("멘티가 아니거나, 유저가 없습니다."));

		MentorClass mentorClass = mentorClassRepository.findById(classId).orElseThrow(
			() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));
		MenteeClassId menteeClassId = new MenteeClassId(mentorClass.getClassId(), mentee.getId());
		Optional<MenteeClass> menteeClass = menteeClassRepository.findById(menteeClassId);
		if (menteeClass.isEmpty()) {
			throw new RuntimeException("해당 클래스가 존재하지 않습니다.");
		}

		Long usedCount = menteeClass.get().getUsedCount();
		Long count = menteeClass.get().getCount();
		if (count <= ++usedCount) {
			throw new RuntimeException("수강 가능 횟수를 초과하였습니다.");
		}
		menteeClass.get().setUsedCount(usedCount);
		menteeClassRepository.save(menteeClass.get());
	}

	public void finalFinishMentoring(Long classId) {
		MentorClass mentorClass = mentorClassRepository.findById(classId)
			.orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

		Long studentCount = mentorClass.getMentor().getStudentCount();
		mentorClass.getMentor().setStudentCount(++studentCount);
		mentorClassRepository.save(mentorClass);
	}

	public List<MentorClassResponse> getMentorMentoring(UserDetails userDetails) {
		Mentor mentor = mentorRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("멘토가 아니거나, 유저가 없습니다."));

		List<MentorClass> mentorClasses = mentorClassRepository.findAll().stream()
			.filter(i -> i.getMentor().getId().equals(mentor.getId()))
			.toList();

		List<MentorClassResponse> mentorClassResponses = new ArrayList<>();

		mentorClasses.forEach(mentorClass -> {
			MentorClassResponse mentorClassResponse = new MentorClassResponse(mentorClass);
			mentorClassResponses.add(mentorClassResponse);
		});
		return mentorClassResponses;
	}

	public void toggleMentoring(UserDetails userDetails, Long classId) {
		Mentor mentor = mentorRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("멘토가 아니거나, 유저가 없습니다."));

		MentorClass mentorClass = mentorClassRepository.findById(classId)
			.orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

		if (mentorClass.getMentor().getId().equals(mentor.getId())) {
			mentorClass.setActive(!mentorClass.getActive());
			mentorClassRepository.save(mentorClass);
		} else {
			throw new RuntimeException("해당 클래스에 대한 권한이 없습니다.");
		}
	}

	@Transactional
	public void deleteMentoring(UserDetails userDetails, Long classId) {
		Mentor mentor = mentorRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("멘토가 아니거나, 유저가 없습니다."));

		MentorClass mentorClass = mentorClassRepository.findById(classId)
			.orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

		if (mentorClass.getMentor().getId().equals(mentor.getId())) {
			mentorClassRepository.delete(mentorClass);
		} else {
			throw new RuntimeException("해당 클래스에 대한 권한이 없습니다.");
		}
	}

	public void modifyMentoring(UserDetails userDetails, MentorClassRequest mentorClassRequest) {
		Mentor mentor = mentorRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("멘토가 아니거나, 유저가 없습니다."));

		MentorClass mentorClass = mentorClassRepository.findById(mentorClassRequest.getClassId())
			.orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

		if (mentorClass.getMentor().getId().equals(mentor.getId())) {
			mentorClass.setName(mentorClassRequest.getName().equals("string") ? mentorClass.getName()
				: mentorClassRequest.getName());
			mentorClass.setDescription(
				mentorClassRequest.getDescription().equals("string") ? mentorClass.getDescription()
					: mentorClassRequest.getDescription());
			mentorClass.setLocation(mentorClassRequest.getLocation().equals("string") ? mentorClass.getLocation()
				: mentorClassRequest.getLocation());
			mentorClass.setTime(
				mentorClassRequest.getTime() != 0 ? mentorClassRequest.getTime() : mentorClass.getTime());
			mentorClass.setPrice(
				mentorClassRequest.getPrice() != 0 ? mentorClassRequest.getPrice() : mentorClass.getPrice());
			mentorClass.setSubCategory(mentorClassRequest.getSubcategoryId() != 0 ?
				subCategoryRepository.findById(mentorClassRequest.getSubcategoryId())
					.orElseThrow(() -> new RuntimeException("해당 소분류가 존재하지 않습니다.")) : mentorClass.getSubCategory());
		}
	}
}
