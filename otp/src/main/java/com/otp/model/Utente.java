package com.otp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="utente")
public class Utente implements Serializable{
	

	private static final long serialVersionUID = 5805313715263745752L;

	@Id
	@Column(name="id_utente" , nullable = false)
	private Integer id_utente;

	@Column(name="mail", nullable = false, unique = true)
	private String mail;
	
	@Column(name="hex_id")
	private String hex_id;
	
	@Column(name="urlqr", nullable = false, unique = true)
	private String urlqr;
	
	@Lob
	@Column(name = "qrCode", nullable = false)
	private String qrCode;
	
	@Column(name="secret", nullable = false)
	private String secret;
	
	
}