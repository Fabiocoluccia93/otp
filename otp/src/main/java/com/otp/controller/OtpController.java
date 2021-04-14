package com.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.otp.model.QrObject;
import com.otp.model.Utente;
import com.otp.service.OtpServiceImpl;

@RestController
@RequestMapping("/totp")
@CrossOrigin(origins="http://localhost:4200")
public class OtpController {

	@Autowired
	OtpServiceImpl osi;

	@GetMapping("/stato")
	public String getStato()
	{
		return "avviato";
	}
	
	
	//INSERIMENTO UTENTE
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
			return new ResponseEntity<>(messaggio,HttpStatus.CREATED);
		}
		else 
		{
			messaggio = "esito : NON INSERITO";
			return new ResponseEntity<>(messaggio,HttpStatus.OK);
		}
		
	}
	
	// VERIFICA UTENTE
	@GetMapping("/user/{id}")
	public ResponseEntity<String> verificaUtente(@PathVariable int id) {
		boolean controllo = osi.controlloUtenteEsistente(id);
		String messaggio = null;
		
		if(controllo)
		{	
			messaggio = "OK";
			return new ResponseEntity<>(messaggio, HttpStatus.OK);
		}
		else
		{
			messaggio = "KO";
			return new ResponseEntity<>(messaggio, HttpStatus.OK);
		}
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<String> handleMethodArgumentTypeMismatch() {
	    String messaggio=("tipo errato");
	    return new ResponseEntity<>(messaggio,HttpStatus.BAD_REQUEST);
	}
	
	//AGGIORNA UTENTE
	@PutMapping("/user/{id}/{mail}")
	public ResponseEntity<Void> aggiornamentoUtente(@PathVariable int id, @PathVariable String mail){
		boolean c;
		c=osi.aggiornaUtente(id,mail);
		if(c)
		{
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}	
		
		
	}
	
	//ELIMINA UTENTE
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Void> cancellazioneUtente(@PathVariable int id){		
		boolean eliminato = osi.cancellaUtente(id);
		if(eliminato)
		{
			return new ResponseEntity<>(HttpStatus.OK);			
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	//RICHIESTA CODICE QR
	@GetMapping("/user/{id}/qrcode")
	public ResponseEntity<QrObject> richiestaQRCode(@PathVariable int id) {
		Utente recuperato = osi.recuperaQr(id);
		QrObject codici = new QrObject();
		if(recuperato != null)
		{			
			codici.setUrlqr(recuperato.getUrlqr());
			codici.setQrCode(recuperato.getQrCode());
			return new ResponseEntity<>(codici, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	//VERIFICA OTP PER L'ACCESSO ----------OK
	@GetMapping("/user/{id}/otp/{otp}")
	public ResponseEntity<String> verificaOTP(@PathVariable int id, @PathVariable String otp) throws Exception 
	{
		int check = osi.login(id,otp);
		String messaggio = null;
		switch(check)
		{
			case 3:
				messaggio="esito : OK";		
				return new ResponseEntity<>(messaggio, HttpStatus.OK);
			case 2:
				messaggio="esito : OTP NON VALIDO";		
				return new ResponseEntity<>(messaggio, HttpStatus.OK);
			case 1:
				messaggio="esito : UTENTE NON ESISTE";		
				return new ResponseEntity<>(messaggio, HttpStatus.OK);
			default:
				messaggio="esito : ERRORE";		
				return new ResponseEntity<>(messaggio, HttpStatus.OK);		
		}

	}
}
