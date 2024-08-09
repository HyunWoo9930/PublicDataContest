package org.example.publicdatacontest.domain.util;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.MentorClass;

@Entity(name = "likes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LikeId.class)
public class Like {

    @Id
    @Column(name = "mentee_id")
    private Long menteeId;

    @Id
    @Column(name = "class_id")
    private Long classId;

    @ManyToOne
    @JoinColumn(name = "mentee_id", insertable = false, updatable = false)
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private MentorClass mentorClass;
}
