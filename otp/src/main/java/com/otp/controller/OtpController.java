package com.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.otp.model.Utente;
import com.otp.service.OtpServiceImpl;

@RestController
public class OtpController {
	
	@Autowired
	OtpServiceImpl osi;

	@PostMapping("crea")
	public ResponseEntity<Boolean> creaUtente(@RequestBody Utente u) throws Exception
	{
		Boolean utente = osi.creaUtente(u);
		return new ResponseEntity<Boolean>(utente, HttpStatus.CREATED);
	}
}
