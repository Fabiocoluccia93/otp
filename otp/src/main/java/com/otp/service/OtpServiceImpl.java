package com.otp.service;

import java.nio.file.FileSystems;
import java.nio.file.Path;

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
	public boolean creaUtente(Utente u) throws Exception {
		String QRCODE_PATH = "C:\\Users\\Alessio\\Desktop\\QRCODE\\";
		
		String qrcode = QRCODE_PATH + u.getMail() + "-QRCODE.png";

		String secret = OTP.randomBase32(20);
		String hexTime = OTP.timeInHex(System.currentTimeMillis());
		String code = OTP.create(secret, hexTime, 6, Type.TOTP);

		String url = OTP.getURL(secret, 6, Type.TOTP, "Example", u.getMail());

		u.setSecret(secret);
		u.setQrCode(url);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 350, 350);
		Path path = FileSystems.getDefault().getPath(qrcode);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		return or.creaUtente(u);
	}
	
	

	@Override
	public boolean login(Utente u) throws Exception {
		boolean accesso = false;

		String hexTime = OTP.timeInHex(System.currentTimeMillis());
		String code = OTP.create(u.getSecret(), hexTime, 6, Type.TOTP);
		Utente utente = or.login(u);

		return accesso;
	}



}
