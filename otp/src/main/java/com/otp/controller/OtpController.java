package com.otp.controller;

import java.util.Base64;

import javax.print.DocFlavor.STRING;

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

	
	//INSERIMENTO UTENTE ------OK
	@PostMapping("/user/{id}/{mail}")
	public ResponseEntity<String> inserimentoUtente(@PathVariable int id,@PathVariable String mail ) throws Exception {
		Utente u = new Utente();
		String messaggio = null;
		
		u.setId_utente(id);
		u.setMail(mail);
		Utente utente = osi.creaUtente(u);
		if(utente!=null)
		{
			messaggio = "esito : INSERITO";
			return new ResponseEntity<String>(messaggio,HttpStatus.CREATED);
		}
		else
		{
			messaggio = "esito : NON INSERITO";
			return new ResponseEntity<String>(messaggio,HttpStatus.CONFLICT);
		}
		
	}

	
	
	// VERIFICA UTENTE ---------OK
	@GetMapping("/user/{id}")
	public ResponseEntity<String> verificaUtente(@PathVariable int id) {
		Boolean controllo = osi.controlloUtenteEsistente(id);
		String messaggio = null;
		
		if(controllo == false)
		{	
			messaggio = "OK";
			return new ResponseEntity<String>(messaggio, HttpStatus.OK);
		}
		else
		{
			messaggio = "KO";
			return new ResponseEntity<String>(messaggio, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	//AGGIORNA UTENTE --------OK
	@PutMapping("/user/{id}/{mail}")
	public ResponseEntity aggiornamentoUtente(@PathVariable int id, @PathVariable String mail)
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
	public ResponseEntity cancellazioneUtente(@PathVariable int id)
	{		
		boolean eliminato = osi.cancellaUtente(id);
		if(eliminato == true)
		{
			return new ResponseEntity<>(HttpStatus.OK);			
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	
	
	//RICHIESTA CODICE QR ------------OK
	@GetMapping("/user/{id}/qrcode")
	public ResponseEntity<String> richiestaQRCode(@PathVariable int id) {
		Utente recuperato = osi.recuperaQr(id);
		String qr="QRCODE : ";
		
		if(recuperato != null)
		{			
			return new ResponseEntity<String>(qr+recuperato.getQrCode(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//VERIFICA OTP PER L'ACCESSO ----------OK
	@GetMapping("/user/{id}/otp/{otp}")
	public ResponseEntity<String> verificaOTP(@PathVariable int id, @PathVariable String otp) throws Exception {
		Boolean accesso = osi.login(id,otp);
		String messaggio = null;
		
		if(accesso == true)
		{
			messaggio="esito : OK";		
			return new ResponseEntity<String>(messaggio, HttpStatus.OK);
		}
		else
		{
			messaggio="esito : KO";
			return new ResponseEntity<String>(messaggio, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	

}
