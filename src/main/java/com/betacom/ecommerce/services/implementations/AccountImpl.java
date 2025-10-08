package com.betacom.ecommerce.services.implementations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.repositories.IAccountRepository;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.services.IMessaggiServices;
import com.betacom.ecommerce.services.interfaces.IAccountServices;
import com.betacom.ecommerce.utils.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountImpl implements IAccountServices{

	private IAccountRepository accR;
	private IMessaggiServices  msgS;
	
	
	public AccountImpl(IAccountRepository accR, IMessaggiServices msgS) {
		this.accR = accR;
		this.msgS = msgS;
	}


	@Override
	public void create(AccountReq req) throws Exception {
		log.debug("create:" + req);
		
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		String capRegex = "^[0-9]{5}$";
		String telefonoRegex = "^(\\+39)?\\s?(3\\d{2}|0\\d{1,3})\\s?\\d{5,10}$";

		if (req.getNome() == null)
			throw new Exception(msgS.getMessaggio("account_no_nome"));
		if (req.getCognome() == null)
			throw new Exception(msgS.getMessaggio("account_no_cognome"));
		if (req.getEmail() == null)
			throw new Exception(msgS.getMessaggio("account_email_ko"));
		if (!req.getEmail().trim().matches(emailRegex))
			throw new Exception(msgS.getMessaggio("account_email_ko"));
		if (req.getCommune() == null)
			throw new Exception(msgS.getMessaggio("account_no_comune"));
		if (req.getVia() == null)
			throw new Exception(msgS.getMessaggio("account_no_via"));	
		if (!req.getCap().trim().matches(capRegex))
			throw new Exception(msgS.getMessaggio("account_cap_ko"));
		if (req.getTelefono() != null) {
			if (!req.getTelefono().trim().matches(telefonoRegex))
				throw new Exception(msgS.getMessaggio("account_telefono_ko"));			
		}
		if (req.getUserName() == null)
			throw new Exception(msgS.getMessaggio("account_no_username"));	
		if (req.getPwd() == null)
			throw new Exception(msgS.getMessaggio("account_no_pwd"));	
		try {
			Role role = Role.valueOf(req.getRole());
		} catch (IllegalArgumentException e) {
			throw new Exception(msgS.getMessaggio("account_ruolo_ko"));	
		}
		
		Optional<Account> a = accR.findByUserName(req.getUserName());
		if (a.isPresent())
			throw new Exception(msgS.getMessaggio("account_username_ko"));	
		
		
		Account acc = new Account();
		acc.setNome(req.getNome());
		acc.setCognome(req.getCognome());
		acc.setEmail(req.getEmail());
		acc.setCommune(req.getCommune());
		acc.setVia(req.getVia());
		acc.setCap(req.getCap());
		acc.setTelefono(req.getTelefono());
		acc.setUserName(req.getUserName());
		acc.setPwd(req.getPwd());
		acc.setRole(Role.valueOf(req.getRole()));
		acc.setDataCreazione(LocalDate.now());
		
		if (req.getSesso() == null) req.setSesso(true);
		acc.setSesso(req.getSesso());
		
		
		accR.save(acc);
		
	}

}
