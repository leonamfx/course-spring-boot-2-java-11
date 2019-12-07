package com.educacaoweb.course.resourses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacaoweb.course.dto.CredentialsDTO;
import com.educacaoweb.course.dto.EmailDTO;
import com.educacaoweb.course.dto.TokenDTO;
import com.educacaoweb.course.servises.AuthService;


@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private AuthService service;


	@PostMapping(value="/login")
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO dto){
		TokenDTO tokenDto= service.authenticate(dto);

		return ResponseEntity.ok().body(tokenDto);
	}
	
	@PostMapping(value="/refresh")
	public ResponseEntity<TokenDTO> refresh(){
		TokenDTO tokenDto= service.refreshToken();

		return ResponseEntity.ok().body(tokenDto);
	}
	
	@PostMapping(value="/forgot")
	public ResponseEntity<Void> forgot(@RequestBody EmailDTO dto){
	   service.sendNewPassword(dto.getEmail());

		return ResponseEntity.noContent().build();
	}
}