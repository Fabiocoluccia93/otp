package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public Utente creaUtente(Utente u);
	
	public Utente login(Utente u) throws Exception;
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(String username);
	
	public boolean cancellaUtente(int id);
}
