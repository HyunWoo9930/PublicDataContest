package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Reports;

import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorId;
    private String id;
    private String password;
    private String mentorName;
    private String gender;
    private String birth;
    private String email;
    private String phoneNumber;
    private String address;
    private Long mentoringCount;
    private Long studentCount;
    private Boolean reemploymentIdea;
    private Boolean active;
    private Boolean isEmailAlarmAgreed;

    @OneToMany(mappedBy = "mentor")
    private Set<MentorCertificate> certificates;

    @OneToMany(mappedBy = "mentor")
    private Set<MentorBadge> badges;

    @OneToMany(mappedBy = "mentor")
    private Set<MentorClass> mentorClasses;

    @OneToMany(mappedBy = "mentor")
    private Set<Reports> reports;

    @OneToMany(mappedBy = "mentor")
    private Set<Conversation> conversations;

    @OneToMany(mappedBy = "mentor")
    private Set<MentorCategory> mentorCategories;
}
