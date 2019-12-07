package com.educacaoweb.course.resourses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacaoweb.course.dto.CredentialsDTO;
import com.educacaoweb.course.dto.TokenDTO;
import com.educacaoweb.course.servises.AuthService;


@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private AuthService service;


	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO dto){
		TokenDTO tokenDto= service.authenticate(dto);

		return ResponseEntity.ok().body(tokenDto);
	}
}