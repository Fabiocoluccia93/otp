package com.otp.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.otp.model.IdPc;

@Repository
public class IdPcRepoimp implements IdPcRepository {

	@Autowired
	EntityManager em;

	@Override
	public IdPc accessoIdpc(IdPc idpc) {
		IdPc idpcObject = null;
		try {
			Query q = em.createQuery("select i from IdPc i where i.mail=:mail and i.password=:pass");
			q.setParameter("mail", idpc.getMail());
			q.setParameter("pass", idpc.getPassword());
			if (q.getSingleResult() != null) {
				idpcObject = (IdPc) q.getSingleResult();
			}
		} catch (NoResultException e) {
		}
		return idpcObject;
	}
	
	

	@Transactional
	@Override
	public boolean creazioneIdpc(IdPc idpc) {
		boolean creato=false;
		IdPc esistente = null;
		try {
			Query q=em.createQuery("select i from IdPc i where i.mail=:mail");
			q.setParameter("mail", idpc.getMail());
			esistente = (IdPc) q.getSingleResult();
		} catch (NoResultException e) {
		}
		if(esistente== null)
		{
			em.persist(idpc);
			creato= true;
		}
		
		return creato;
	}

}
