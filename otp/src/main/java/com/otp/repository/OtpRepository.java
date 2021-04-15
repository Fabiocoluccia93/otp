package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public int creaUtente(Utente u);
	
	public int login(int id, String otp);
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(int id);
	
	public boolean aggiornaUtente(int id,String mail);
	public boolean cancellaUtente(int id);
}
