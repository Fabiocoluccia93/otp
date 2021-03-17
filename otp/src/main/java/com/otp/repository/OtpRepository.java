package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public Utente creaUtente(Utente u);
	
	public Utente login(Utente u);
	
	public boolean controlloUtenteEsistente(int id);
	
	public Utente recuperaQr(String username);
	
	public boolean cancellaUtente(int id);
<<<<<<< HEAD
	public boolean aggiornaUtente(int id,String mail);
=======
	
	public boolean aggiornaUtente(int id);
	
	public Utente creaUtenteGoogleApi(Utente u);
>>>>>>> branch 'testAlessio+' of https://github.com/Fabiocoluccia93/otp.git
}
