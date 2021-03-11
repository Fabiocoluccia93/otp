package com.otp.service;

import com.otp.model.Utente;

public interface OtpServiceInterface {

	public boolean creaUtente(Utente u) throws Exception;

	public boolean login(Utente u) throws Exception;
	
	public boolean controlloUtenteEsistente(String username);
}
