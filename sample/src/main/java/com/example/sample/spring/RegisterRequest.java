package com.example.sample.spring;


import javax.validation.constraints.*;

public class RegisterRequest {

	@NotBlank
	@Email
	private String email;

	@Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
	private String password;
	@NotEmpty
	private String confirmPassword;
	@NotEmpty
	private String name;

//	private String registerRequest;
//
//	// getter
//	public String getRegisterRequest() {
//		return registerRequest;
//	}
//
//	// setter
//	public void setRegisterRequest(String registerRequest) {
//		this.registerRequest = registerRequest;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}
}
