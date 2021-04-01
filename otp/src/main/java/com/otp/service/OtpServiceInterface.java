package com.otp.service;

import com.otp.model.Utente;

public interface OtpServiceInterface {

	public Utente creaUtente(Utente u) throws Exception;

	public boolean login(int id ,String otp) throws Exception;
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(int id);
	
	public boolean aggiornaUtente(int id,String mail);
	
	public boolean cancellaUtente(int id);
	
	public Utente creaUtenteGoogleApi(Utente u);
}
