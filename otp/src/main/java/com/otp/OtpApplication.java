package com.otp;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@SpringBootApplication
public class OtpApplication {

	public static String secret;
	public static String hexTime;
	public static String code;
	private static String QRCODE_PATH = "C:\\Users\\Alessio\\Desktop\\QRCODE\\";

	public String writeQRCode(Utente dati) throws Exception {

		secret = OTP.randomBase32(20);
		dati.setSecret(secret);

		hexTime = OTP.timeInHex(System.currentTimeMillis());
		code = OTP.create(secret, hexTime, 6, Type.TOTP);

		String qrcode = QRCODE_PATH + dati.getMail() + "-QRCODE.png";

		// String url = "otpauth://totp/"+dati.getMail()+"?secret="+secret+"issuer";

		String url = OTP.getURL(secret, 6, Type.TOTP, "Example", dati.getMail());

		QRCodeWriter writer = new QRCodeWriter();

		BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 350, 350);

		Path path = FileSystems.getDefault().getPath(qrcode);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		// salvare email o username, url e secret criptati

		return "QRCODE is generated successfully....";

	}

	public static void main(String[] args) throws Exception {
		OtpApplication codiceQr = new OtpApplication();
		System.out.println(codiceQr.writeQRCode(new Utente("test", secret)));

		Scanner otp = new Scanner(System.in);
		String otpgenerata = otp.nextLine();
		/*
		 * System.out.println( codeGenerator.writeQRCode(new PaytmRequestBody("alessio",
		 * "123456")));
		 */

		while (otpgenerata != "esci") {
			hexTime = OTP.timeInHex(System.currentTimeMillis());
			code = OTP.create(secret, hexTime, 6, Type.TOTP);
			if (OTP.verify(secret, hexTime, otpgenerata, 6, Type.TOTP)) {
				System.out.println("corretto");
				hexTime = OTP.timeInHex(System.currentTimeMillis());
				code = OTP.create(secret, hexTime, 6, Type.TOTP);
			} else {
				System.out.println("errato");
				hexTime = OTP.timeInHex(System.currentTimeMillis());
				code = OTP.create(secret, hexTime, 6, Type.TOTP);
			}
			otpgenerata = otp.nextLine();
		}

	}

}
