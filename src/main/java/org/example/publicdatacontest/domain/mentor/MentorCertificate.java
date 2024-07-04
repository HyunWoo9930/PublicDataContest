package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentor.Mentor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
public class MentorCertificate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long certificateId;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	private String filePath;
	private LocalDateTime uploadDate;
}