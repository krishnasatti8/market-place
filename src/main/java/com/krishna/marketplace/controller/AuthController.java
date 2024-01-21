package com.krishna.marketplace.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.marketplace.dto.AuthenticationRequest;
import com.krishna.marketplace.dto.SignupRequest;
import com.krishna.marketplace.dto.UserDto;
import com.krishna.marketplace.model.User;
import com.krishna.marketplace.repository.UserRepository;
import com.krishna.marketplace.services.auth.AuthService;
import com.krishna.marketplace.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final UserRepository userRepository;

	private final JwtUtil jwtUtil;

	private static final String TOKEN_PREFIX = "Bearer ";

	private static final String HEADER_STRING = "Authorization";

	private final AuthService authService;

	public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			UserRepository userRepository, JwtUtil jwtUtil, AuthService authService) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws IOException, JSONException {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username or Password");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		Date expirationDate = jwtUtil.extractExpiration(jwt);

		if (optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId())
					.put("role", optionalUser.get().getRole())
					.put("expirationDate", expirationDate).toString());

			response.addHeader("Access-Control-Expose-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers",
					"Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}

	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
		if (authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("User already Exits", HttpStatus.NOT_ACCEPTABLE);
		}

		UserDto userDto = authService.createUser(signupRequest);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

}
