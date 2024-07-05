package org.example.publicdatacontest.domain.signinup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	private String gender;

	@NotBlank
	@Size(max = 10)
	private String birth;

	@NotBlank
	@Email
	@Size(max = 100)
	private String email;

	@NotBlank
	@Size(max = 15)
	private String phoneNumber;

	@NotBlank
	@Size(max = 200)
	private String address;

	private Boolean employmentIdea;
	private Boolean isEmailAlarmAgreed;
}
