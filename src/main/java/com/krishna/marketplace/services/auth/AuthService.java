package com.krishna.marketplace.services.auth;

import com.krishna.marketplace.dto.SignupRequest;
import com.krishna.marketplace.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest signupRequest);

	boolean hasUserWithEmail(String email);

}
