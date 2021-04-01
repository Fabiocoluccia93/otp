package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public Utente creaUtente(Utente u);
	
	public Utente login(int id, String otp) throws Exception;
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(int id);
	
	public boolean aggiornaUtente(int id,String mail);
	public boolean cancellaUtente(int id);
}
