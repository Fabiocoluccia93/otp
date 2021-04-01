package com.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.model.IdPc;
import com.otp.service.IdPcServiceImp;

import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("idpc")
@CrossOrigin(origins="http://localhost:4200")
public class IdPcController {
	
	@Autowired
	IdPcServiceImp idservice;
	
	@PostMapping("accesso")
	public ResponseEntity<IdPc> accessoPrimoLivello(@RequestBody IdPc idpc)
	{
		IdPc accesso = idservice.accessoIdpc(idpc);
		return new ResponseEntity<IdPc>(accesso, HttpStatus.OK);					
	}
	
	
	
	@PostMapping("creazione")
	public ResponseEntity<Boolean> creazioneUtente(@RequestBody IdPc idpc)
	{
		Boolean creato = idservice.creazioneIdpc(idpc);
		return new ResponseEntity<Boolean>(creato, HttpStatus.CREATED);
	}

}
