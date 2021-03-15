package com.otp.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.otp.model.Utente;
import com.otp.repository.OtpRepoImp;

@Service
public class OtpServiceImpl implements OtpServiceInterface {

	@Autowired
	OtpRepoImp or;
	

	@Override
	public Utente creaUtente(Utente u) throws Exception 
	{
		
		String secret = OTP.randomBase32(20);
		String hexTime = OTP.timeInHex(System.currentTimeMillis());
		String code = OTP.create(secret, hexTime, 6, Type.TOTP);
		u.setHex_id(code);

		String url = OTP.getURL(secret, 6, Type.TOTP, "Example", u.getMail());
		
		u.setSecret(secret);
		
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200);
		
		ByteArrayOutputStream a = new ByteArrayOutputStream();
		
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", a);
		
		byte[] fileContent = a.toByteArray();
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		u.setQrCode(encodedString);

		return or.creaUtente(u);
	}
	
	

	@Override
	public boolean login(Utente u) throws Exception {
		boolean accesso = false;
		this.aggiornaOtp(u);
		
		Utente utente = or.login(u);
		if(utente!=null)
		{
			accesso=true;
		}

		return accesso;
	}


	//******************** OKOKOK **************
	@Override
	public boolean controlloUtenteEsistente(int id) {
		return or.controlloUtenteEsistente(id);
	}
	
	
	public void aggiornaOtp(Utente u) throws Exception
	{
		or.aggiornaOtp(u);
	}



	@Override
	public Utente recuperaQr(String username) {
		return or.recuperaQr(username);
	}
	
	@Override
	public boolean aggiornaUtente(int id)
	{
		return or.aggiornaUtente(id);
	}
	
	@Override
	public boolean cancellaUtente(int id)
	{
		return or. cancellaUtente(id);
	}



	@Override
	public Utente creaUtenteGoogleApi(Utente u) {
		// TODO Auto-generated method stub
		return null;
	}

}
