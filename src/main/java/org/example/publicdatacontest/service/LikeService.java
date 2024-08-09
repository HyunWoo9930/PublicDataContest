package org.example.publicdatacontest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.publicdatacontest.domain.dto.responseDTO.LikeResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorClassResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.util.Like;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorClassRepository;
import org.example.publicdatacontest.repository.util.LikeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final MenteeRepository menteeRepository;
    private final MentorClassRepository mentorClassRepository;

    public LikeResponse likeClass(Long classId, UserDetails userDetails){
        Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("멘티가 아니거나, 유저가 없습니다."));

        MentorClass mentorClass = mentorClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));


        if (likeRepository.findByMenteeAndMentorClass(mentee, mentorClass).isPresent()) {
            throw new RuntimeException("이미 좋아요가 처리 되었습니다.");
        }

        Like like = new Like(mentee.getId(), mentorClass.getClassId(), mentee, mentorClass);
        likeRepository.save(like);

        mentee.getLikes().add(like);
        mentorClass.likeCountUp();

        return new LikeResponse(mentorClass);
    }

    public LikeResponse deleteLike(Long classId, UserDetails userDetails){
        Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("멘티가 아니거나, 유저가 없습니다."));

        MentorClass mentorClass = mentorClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("해당 클래스가 존재하지 않습니다."));

        Like like = likeRepository.findByMenteeAndMentorClass(mentee, mentorClass)
                .orElseThrow(() -> new RuntimeException("좋아요가 존재하지 않습니다."));

        likeRepository.delete(like);
        mentee.getLikes().remove(like);
        mentorClass.likeCountDown();

        return new LikeResponse(mentorClass);
    }

    public List<MentorClassResponse> getLikeClassList(UserDetails userDetails) {
        Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("멘티가 아니거나, 유저가 없습니다."));

        List<MentorClass> classList = mentee.getLikes().stream()
                .map(Like::getMentorClass)
                .toList();

        return classList.stream()
                .map(MentorClassResponse::new)
                .toList();
    }
}
