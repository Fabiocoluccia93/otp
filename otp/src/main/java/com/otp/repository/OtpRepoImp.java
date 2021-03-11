package com.otp.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.otp.model.Utente;

@Repository
public class OtpRepoImp implements OtpRepository {

	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public boolean creaUtente(Utente u) {
		boolean creato = false;
		em.persist(u);
		em.flush();

		if (u.getId_utente() != null) {
			creato = true;
		}
		return creato;
	}

	
	
	@Override
	public Utente login(Utente u) {
		boolean accesso = false;
		Query q = em.createQuery("SELECT u FROM Utente u WHERE u.mail =: mail and u.hex_id=:otp");
		q.setParameter("mail", u.getMail());
		q.setParameter("otp", u.getHex_id());
		Utente utente = (Utente) q.getSingleResult();
		return utente;
	}


	
	
	//******************** OKOKOK **************
	@Override
	public boolean controlloUtenteEsistente(String username)   {
		boolean esistente = false;
		
		Query q=em.createQuery("select u from Utente u where u.mail=:username");
		q.setParameter("username", username);
		try {
			if(q.getSingleResult()!=null)
			{
				esistente = true;
			}
			
		} catch (NoResultException e) {
		}
		
		
		return esistente;
	}

}
