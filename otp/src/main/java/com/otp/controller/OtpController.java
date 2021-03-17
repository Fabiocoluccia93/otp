package com.otp.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.model.Utente;
import com.otp.service.OtpServiceImpl;

@RestController
@RequestMapping("/totp")
@CrossOrigin(origins="http://localhost:4200")
public class OtpController {

	@Autowired
	OtpServiceImpl osi;

	
	//CREA UTENTE ------OK
	@PostMapping("/user/{id}/{mail}")
	public ResponseEntity creaUtente(@PathVariable int id,@PathVariable String mail ) throws Exception {
		Utente u = new Utente();
		u.setId_utente(id);
		u.setMail(mail);
		Utente utente = osi.creaUtente(u);
		if(utente!=null)
		{
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}

	
	
	// CONTROLLO ESISTENTE ---------OK
	@GetMapping("/user/{id}")
	public ResponseEntity<Boolean> controlloEsistente(@PathVariable int id) {
		Boolean controllo = osi.controlloUtenteEsistente(id);
		return new ResponseEntity<Boolean>(controllo, HttpStatus.OK);
	}
	
	//MODIFICA UTENTE --------OK
	@PutMapping("/user/{id}/{mail}")
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
	
	//ELIMINA UTENTE --------------OK
	@DeleteMapping("/user/{id}")
	public ResponseEntity cancellaUtente(@PathVariable int id)
	{
		osi.cancellaUtente(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//RECUPERA CODICE QR ------------OK
	@GetMapping("/user/{username}/qrcode")
	public ResponseEntity<String> recuperaQr(@PathVariable String username) {
		Utente recuperato = osi.recuperaQr(username);					
		return new ResponseEntity<String>(recuperato.getQrCode(), HttpStatus.OK);
	}
	
	
	//LOGIN ----------OK
	@GetMapping("/user/{id}/otp/{otp}")
	public ResponseEntity<Boolean> acceso(@PathVariable int id, @PathVariable String otp) throws Exception {
		Boolean accesso = osi.login(id,otp);
		return new ResponseEntity<Boolean>(accesso, HttpStatus.CREATED);
	}
	
	
	

}
