package org.example.publicdatacontest.service;

import java.util.Optional;

import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.mentor.MentorClassResponse;
import org.example.publicdatacontest.repository.mentor.MentorClassRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClassService {

	private final MentorRepository mentorRepository;

	private final MentorClassRepository mentorClassRepository;

	public ClassService(MentorRepository mentorRepository, MentorClassRepository mentorClassRepository) {
		this.mentorRepository = mentorRepository;
		this.mentorClassRepository = mentorClassRepository;
	}

	@Transactional
	public void makeMentoring(UserDetails userDetails, MentorClassResponse mentorClassResponse) {
		MentorClass mentorClass = new MentorClass();

		Optional<Mentor> mentor = mentorRepository.findByUserId(userDetails.getUsername());
		if (mentor.isEmpty()) {
			throw new RuntimeException("멘토가 아니거나, 유저가 없습니다.");
		}

		mentorClass.setMentor(mentor.get());
		mentorClass.setActive(true);
		mentorClass.setName(mentorClassResponse.getName());
		mentorClass.setDescription(mentorClassResponse.getDescription());
		mentorClass.setLocation(mentorClassResponse.getLocation());
		mentorClass.setTime(mentorClassResponse.getTime());
		mentorClass.setPrice(mentorClassResponse.getPrice());

		mentorClassRepository.save(mentorClass);
	}
}
