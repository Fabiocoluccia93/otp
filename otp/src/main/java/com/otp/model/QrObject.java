package com.otp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QrObject {
	
	private String urlqr;
	private String qrCode;
	
}
