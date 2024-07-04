package org.example.publicdatacontest.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    private LocalDateTime startDate;

    @OneToMany(mappedBy = "conversation")
    private Set<Chat> chats;
}
