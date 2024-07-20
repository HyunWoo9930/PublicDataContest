package org.example.publicdatacontest.domain.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userId;
	private String password;
	private String name;

	@NotBlank
	@Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
	private String gender;

	@Past(message = "Birthdate must be in the past")
	private LocalDate birth;

	@NotBlank
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be between 10 and 15 digits")
	private String phoneNumber;
	private String address;
	private Boolean active;
	private Boolean isEmailAlarmAgreed;
	private String paymentMethod;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	private byte[] profilePicture;
}
