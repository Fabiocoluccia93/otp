package com.otp.repository;

import com.otp.model.Utente;

public interface OtpRepository {

	public boolean creaUtente(Utente u);
	
	public Utente login(Utente u);
}