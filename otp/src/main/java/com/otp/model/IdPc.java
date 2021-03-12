package com.otp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idpc")
public class IdPc implements Serializable {


	private static final long serialVersionUID = -4621417229274061473L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "mail", nullable = false , unique = true)
	private String mail;
	
	@Column(name = "password" , nullable = false)
	private String password;
}
