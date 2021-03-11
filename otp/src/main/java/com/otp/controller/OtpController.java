package com.otp.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otp.model.Utente;
import com.otp.service.OtpServiceImpl;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class OtpController {

	@Autowired
	OtpServiceImpl osi;

	@PostMapping("crea")
	public ResponseEntity<Utente> creaUtente(@RequestBody Utente u) throws Exception {
		Utente utente = osi.creaUtente(u);
		return new ResponseEntity<Utente>(utente, HttpStatus.CREATED);
	}

	@PostMapping("accesso")
	public ResponseEntity<Boolean> acceso(@RequestBody Utente u) throws Exception {
		Boolean accesso = osi.login(u);
		return new ResponseEntity<Boolean>(accesso, HttpStatus.CREATED);
	}

	@GetMapping("esistente/{username}")
	public ResponseEntity<Boolean> controlloEsistente(@PathVariable String username) {
		Boolean controllo = osi.controlloUtenteEsistente(username);
		return new ResponseEntity<Boolean>(controllo, HttpStatus.OK);
	}

}
