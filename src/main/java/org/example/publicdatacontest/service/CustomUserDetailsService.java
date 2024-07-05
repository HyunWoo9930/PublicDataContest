package org.example.publicdatacontest.service;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.jwt.CustomUserDetails;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MentorRepository mentorRepository;

	@Autowired
	private MenteeRepository menteeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Mentor mentor = mentorRepository.findByUserId(username).orElse(null);
		if (mentor != null) {
			return new CustomUserDetails(mentor);
		}
		Mentee mentee = menteeRepository.findByUserId(username).orElse(null);
		if (mentee != null) {
			return new CustomUserDetails(mentee);
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}
