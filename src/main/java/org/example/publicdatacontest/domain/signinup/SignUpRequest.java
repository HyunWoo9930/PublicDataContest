package org.example.publicdatacontest.domain.signinup;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

	@NotBlank
	@Size(min = 3, max = 50)
	private String userId;

	@NotBlank
	@Size(min = 6, max = 100)
	private String password;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(max = 10)
	@Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
	private String gender;

	@NotBlank
	@Size(max = 10)
	@Past(message = "Birthdate must be in the past")
	private LocalDate birth;

	@NotBlank
	@Email(message = "Email should be valid")
	@Size(max = 100)
	private String email;

	@NotBlank
	@Size(max = 15)
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be between 10 and 15 digits")
	private String phoneNumber;

	@NotBlank
	@Size(max = 200)
	private String address;

	private Boolean employmentIdea;
	private Boolean isEmailAlarmAgreed;
}
