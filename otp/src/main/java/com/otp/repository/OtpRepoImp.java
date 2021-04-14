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
		
		Utente creato = null;
		
		try {
			creato = em.find(Utente.class, u.getId_utente());
			if(creato == null)
			{
				em.persist(u);
			}
			else	
			{
				u=null;
			}
		} catch (NoResultException e) 
		{
			u=null;
		}
		return u;
	}

	
	@Transactional
	@Override
	public int login(int id, String otp)  throws Exception
	{
		int check = 0;
		try 
		{
			Utente a = em.find(Utente.class, id);
			String hexTime = OTP.timeInHex(System.currentTimeMillis());
			String code = OTP.create(a.getSecret(), hexTime, 6, Type.TOTP);
			a.setHex_id(code);
			em.merge(a);
			
			Query q = em.createQuery("SELECT u FROM Utente u WHERE u.id_utente =:id and u.hex_id=:otp");
			q.setParameter("id", id);
			q.setParameter("otp", otp);
			
			
			check=3;//utente esiste
			
		} catch (NoResultException e) 
		{
			check=2;//utente non esiste
		}
		catch (NullPointerException e) 
		{
			check=1;// 
		}
		return check;
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
			
		} catch (NoResultException e)
		{
			 //catch blank
		}
		
		
		return esistente;
	}
	



	@Override
	public Utente recuperaQr(int id) {
	
		Utente recuperato = null;
		
		try {
			
			recuperato = em.find(Utente.class, id);
			
		} catch (NoResultException e) 
		{
			recuperato = null;
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
			if(a!=null)
			{
				a.setMail(mail);
				em.merge(a);
				c=true;				
			}
		} catch (NoResultException e) {
			c=false;
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
		}
		catch(NoResultException e)
		{
			//this catch 
		}
		if(a!=null)
		{
			em.remove(a);
			c=true;
		}
		return c;
	}

}
