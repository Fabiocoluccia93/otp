package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public Utente creaUtente(Utente u);
	
	public Utente login(Utente u);
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(String username);
	
	public boolean cancellaUtente(int id);
	
	public boolean aggiornaUtente(int id);
	
	public Utente creaUtenteGoogleApi(Utente u);
}
