package com.educacaoweb.course.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educacaoweb.course.dto.CredentialsDTO;
import com.educacaoweb.course.dto.TokenDTO;
import com.educacaoweb.course.entities.User;
import com.educacaoweb.course.repositories.UserRepository;
import com.educacaoweb.course.security.JWTUtil;
import com.educacaoweb.course.services.exceptions.JWTAuthenticationException;
import com.educacaoweb.course.services.exceptions.JWTAuthorizationException;



@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenicationManager;

	@Autowired
	private  JWTUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public TokenDTO authenticate(CredentialsDTO dto) {

		try {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword());
		authenicationManager.authenticate(authToken);
		String token= jwtUtil.generateToken(dto.getEmail());
		return new TokenDTO(dto.getEmail(),token);
		}catch(AuthenticationException e) {
			throw new JWTAuthenticationException("Bad credentials");
		}

	}
	
	public User authenticated() {
		try{

		UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByEmail(userDetails.getUsername());

		}catch(Exception e) {
			throw new JWTAuthorizationException("Acess denied");
		}
	}

	public void validateSelfOrAdmin(Long userId) {
		User user= authenticated();
		if(user == null || (!user.getId().equals(userId)) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Acess denied");
		}
	}
}