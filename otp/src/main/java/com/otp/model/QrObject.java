package com.otp.model;

import lombok.Data;

@Data
public class QrObject {
	
	private String urlqr;
	private String qrCode;
	private String issuer;
	private String secret;
	private String mail;
	
}
