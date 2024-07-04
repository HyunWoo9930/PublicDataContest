package org.example.publicdatacontest.domain.mentor;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.util.Badge;

@Entity
@Getter
@Setter
@IdClass(MentorBadgeId.class)
public class MentorBadge {
    @Id
    @Column(name = "mentor_id")
    private Long mentorId;
    @Id
    @Column(name = "badge_id")
    private Long badgeId;

    @ManyToOne
    @JoinColumn(name = "mentor_id", insertable = false, updatable = false)
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "badge_id", insertable = false, updatable = false)
    private Badge badge;
}
