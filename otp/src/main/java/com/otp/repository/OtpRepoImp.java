package com.otp.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.otp.model.Utente;

@Repository
public class OtpRepoImp implements OtpRepository {

	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public Utente creaUtente(Utente u) {
		
		em.persist(u);
		
		return u;
	}

	
	
	@Override
	public Utente login(Utente u)
	{
		Utente utente =new Utente();
		try 
		{
			
			Query q = em.createQuery("SELECT u FROM Utente u WHERE u.mail =: mail and u.hex_id=:otp");
			q.setParameter("mail", u.getMail());
			q.setParameter("otp", u.getHex_id());
			utente = (Utente) q.getSingleResult();
			
		} catch (NoResultException e) {
			utente=null;
		}
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
	
	@Transactional
	public void aggiornaOtp(Utente u) throws Exception
	{
		try {
			System.out.println("la mail è"+u.getMail());
			Query q=em.createQuery("select u from Utente u where u.mail=:username");
			q.setParameter("username", u.getMail());
			Utente a = (Utente) q.getSingleResult();
			String hexTime = OTP.timeInHex(System.currentTimeMillis());
			String code = OTP.create(a.getSecret(), hexTime, 6, Type.TOTP);
			a.setHex_id(code);
			em.merge(a);
		} catch (NoResultException e) {
			// TODO: handle exception
		}
		
	}
}
