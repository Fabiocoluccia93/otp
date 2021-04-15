package com.otp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
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
	public int creaUtente(Utente u){
		try {
			String secret = OTP.randomBase32(20);
			String hexTime = OTP.timeInHex(System.currentTimeMillis());
			String code = OTP.create(secret, hexTime, 6, Type.TOTP);
			u.setHex_id(code);
			String url = OTP.getURL(secret, 6, Type.TOTP, "Example", u.getMail());
			u.setUrlqr(url);
			u.setSecret(secret);
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix bitMatrix = null;
			bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200);
			ByteArrayOutputStream a = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", a);
			byte[] fileContent = a.toByteArray();
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			u.setQrCode(encodedString);
			return or.creaUtente(u);
		} 
		catch (IOException | InvalidKeyException | IllegalArgumentException | NoSuchAlgorithmException| WriterException e)	{
			e.printStackTrace();
			return 2;
		}
		
		
	}
	
	

	@Override
	public int login(int id ,String otp)
	{
		return or.login(id,otp);
	}

	@Override
	public boolean controlloUtenteEsistente(int id) {
		return or.controlloUtenteEsistente(id);
	}

	@Override
	public Utente recuperaQr(int id) {
		return or.recuperaQr(id);
	}
	
	@Override
	public boolean aggiornaUtente(int id,String mail){
		return or.aggiornaUtente(id,mail);
	}
	
	@Override
	public boolean cancellaUtente(int id){
		return or. cancellaUtente(id);
	}

	@Override
	public Utente creaUtenteGoogleApi(Utente u) {
		return null;
	}

}
