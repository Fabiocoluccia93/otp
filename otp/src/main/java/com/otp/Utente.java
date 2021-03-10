package com.otp;

public class Utente {

	private String mail;
	private String secret;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Utente(String mail, String secret) {
		super();
		this.mail = mail;
		this.secret = secret;
	}

	public Utente() {
		super();
		// TODO Auto-generated constructor stub
	}

}
