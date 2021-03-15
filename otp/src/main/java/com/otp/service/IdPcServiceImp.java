package com.otp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.model.IdPc;
import com.otp.repository.IdPcRepoimp;

@Service
public class IdPcServiceImp implements IdPcService {

	@Autowired
	IdPcRepoimp idrepo;
	
	@Override
	public IdPc accessoIdpc(IdPc idpc) {
		return idrepo.accessoIdpc(idpc);
	}

	@Override
	public boolean creazioneIdpc(IdPc idpc) {
		return idrepo.creazioneIdpc(idpc);
	}

}
