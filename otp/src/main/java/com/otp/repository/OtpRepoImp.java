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

	
	@Transactional
	@Override
	public Utente login(Utente u)  throws Exception
	{
		Utente utente =new Utente();
		try 
		{
			Query q2=em.createQuery("select u from Utente u where u.mail=:username");
			q2.setParameter("username", u.getMail());
			Utente a = (Utente) q2.getSingleResult();
			String hexTime = OTP.timeInHex(System.currentTimeMillis());
			String code = OTP.create(a.getSecret(), hexTime, 6, Type.TOTP);
			a.setHex_id(code);
			em.merge(a);
			
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
	public boolean controlloUtenteEsistente(int id)   {
		boolean esistente = false;
		
		Query q=em.createQuery("select u from Utente u where u.id_utente=:id");
		q.setParameter("id", id);
		try {
			if(q.getSingleResult()!=null)
			{
				esistente = true;
			}
			
		} catch (NoResultException e) {
		}
		
		
		return esistente;
	}
	



	@Override
	public Utente recuperaQr(String username) {
		
		Utente recuperato =null;
		Query q=em.createQuery("select u from Utente u where u.mail=:username");
		q.setParameter("username", username);
		try {
			if(q.getSingleResult()!=null)
			{
				recuperato = (Utente) q.getSingleResult();
			}
			
		} catch (NoResultException e) {
		}
		return recuperato;
		
	}
	
	@Transactional
	@Override
	public boolean aggiornaUtente(int id,String mail)
	{
		boolean c=false;
		try {
			Utente a = em.find(Utente.class, id);
			a.setMail(mail);
			em.merge(a);
			c=true;
		} catch (NoResultException e) {
			// TODO: handle exception
		}
		return c;
	}
	
	@Transactional
	@Override
	public boolean cancellaUtente(int id)
	{
		Utente a = null;
		boolean c=false;
		try 
		{
			a = em.find(Utente.class, id);
		}catch(NoResultException e)
		{
			
		}
		if(a!=null)
		{
			em.remove(a);
			c=true;
		}
		return c;
	}



	@Override
	public Utente creaUtenteGoogleApi(Utente u) {
		// TODO Auto-generated method stub
		return null;
	}
}
