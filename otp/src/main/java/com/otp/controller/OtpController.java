package com.otp.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("crea/{id}/{mail}")
	public ResponseEntity creaUtente(@PathVariable int id,@PathVariable String mail ) throws Exception {
		Utente u = new Utente();
		u.setId_utente(id);
		u.setMail(mail);
		Utente utente = osi.creaUtente(u);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	//mockato //
	@PostMapping("accesso/{id}/{otp}")
	public ResponseEntity<Boolean> acceso(@PathVariable int id, @PathVariable String otp) throws Exception {
		Boolean accesso = osi.login(id,otp);
		return new ResponseEntity<Boolean>(accesso, HttpStatus.CREATED);
	}

	@GetMapping("esistente/{id}")
	public ResponseEntity<Boolean> controlloEsistente(@PathVariable int id) {
		Boolean controllo = osi.controlloUtenteEsistente(id);
		return new ResponseEntity<Boolean>(controllo, HttpStatus.OK);
	}
	
	@GetMapping("recupera/{username}")
	public ResponseEntity<Utente> recuperaQr(@PathVariable String username) {
		Utente recuperato = osi.recuperaQr(username);
		return new ResponseEntity<Utente>(recuperato, HttpStatus.OK);
	}
	
	@PutMapping("aggiorna/{id}/{mail}")
	public ResponseEntity aggiornaUtente(@PathVariable int id, @PathVariable String mail)
	{
		boolean c;
		c=osi.aggiornaUtente(id,mail);
		if(c==true)
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@DeleteMapping("cancella/{id}")
	public ResponseEntity cancellaUtente(@PathVariable int id)
	{
		osi.cancellaUtente(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
