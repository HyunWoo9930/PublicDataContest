package org.example.publicdatacontest.domain.mentor;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Certificate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "mentor_id", nullable = false)
	private Mentor mentor;

	@Column(columnDefinition = "mediumblob")
	private byte[] image;
	private String imageName;
	private LocalDateTime uploadTime;

	@PrePersist
	protected void onCreate() {
		uploadTime = LocalDateTime.now();
	}
}
