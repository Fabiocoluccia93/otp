package com.otp.service;

import com.otp.model.Utente;

public interface OtpServiceInterface {

	public Utente creaUtente(Utente u) throws Exception;

	public boolean login(Utente u) throws Exception;
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(String username);
	
	public boolean aggiornaUtente(int id);
	
	public boolean cancellaUtente(int id);
}
