package com.taskmaster.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmaster.entity.AppUser;
import com.taskmaster.exception.RestException;
import com.taskmaster.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class AppUserService implements UserDetailsService {

	private final static String USER_NOT_FOUND_MSG = "User with email %s not found.";
	private final static String USER_IS_DISABLED_MSG = "User with email %s is disabled.";

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser userDetails = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

		httpSession.setAttribute("role", userDetails.getUserRole());
		return userDetails;
	}

	public boolean signUpUser(AppUser appUser) {
		Optional<AppUser> user = userRepository.findByEmail(appUser.getEmail());

		if (user.isPresent()) {
			if (!user.get().isEnabled() && user.get().isAccountNonLocked())
				throw new RestException(String.format(USER_IS_DISABLED_MSG, user.get().getEmail()),
						HttpStatus.FORBIDDEN);
			throw new RestException("Email already exists!", HttpStatus.CONFLICT);
		}

		String encodedPassword = passwordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);

		userRepository.save(appUser);

		return true;
	}

}
