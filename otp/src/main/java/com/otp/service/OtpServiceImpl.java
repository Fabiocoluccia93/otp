package com.otp.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
	public Utente creaUtente(Utente u) throws Exception {
		String QRCODE_PATH = "C:\\Users\\ISC_025\\Desktop\\qrcode\\";
		
		String qrcode = QRCODE_PATH + u.getMail() + "-QRCODE.png";

		String secret = OTP.randomBase32(20);
		String hexTime = OTP.timeInHex(System.currentTimeMillis());
		String code = OTP.create(secret, hexTime, 6, Type.TOTP);
		u.setHex_id(code);

		String url = OTP.getURL(secret, 6, Type.TOTP, "Example", u.getMail());
		

		u.setSecret(secret);
		

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 350, 350);
		
		Path path = FileSystems.getDefault().getPath(qrcode);
		
		
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		
		File file = new File(qrcode);
		
		byte[] fileContent = FileUtils.readFileToByteArray(file);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		u.setQrCode(encodedString);

		return or.creaUtente(u);
	}
	
	

	@Override
	public boolean login(Utente u) throws Exception {
		boolean accesso = false;
		System.out.println("mail in login"+u.getMail());
		this.aggiornaOtp(u);
		Thread.sleep(1000);
		
		Utente utente = or.login(u);
		if(utente!=null)
		{
			accesso=true;
		}

		return accesso;
	}


	//******************** OKOKOK **************
	@Override
	public boolean controlloUtenteEsistente(String username) {
		return or.controlloUtenteEsistente(username);
	}
	
	@Transactional
	@Modifying
	public void aggiornaOtp(Utente u) throws Exception
	{
		or.aggiornaOtp(u);
	}

}
