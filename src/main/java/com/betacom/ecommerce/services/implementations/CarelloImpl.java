package com.betacom.ecommerce.services.implementations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.models.Carello;
import com.betacom.ecommerce.repositories.IAccountRepository;
import com.betacom.ecommerce.repositories.ICarelloRepository;
import com.betacom.ecommerce.requests.CarelloReq;
import com.betacom.ecommerce.services.IMessaggiServices;
import com.betacom.ecommerce.services.interfaces.ICarelloServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarelloImpl implements ICarelloServices{
	
	private ICarelloRepository carR;
	private IAccountRepository accountR;
	private IMessaggiServices  msgS;
	
	public CarelloImpl(ICarelloRepository carR, IAccountRepository accountR, IMessaggiServices msgS) {
		super();
		this.carR = carR;
		this.accountR = accountR;
		this.msgS = msgS;
	}

	@Override
	public void create(CarelloReq req) throws Exception {
		log.debug("create:" + req);
		Account ac = accountR.findById(req.getAccountID())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("account_ntfnd")));
		
		Carello car = new Carello();
		car.setAccount(ac);
		car.setDataCreazione(LocalDate.now());
		
		carR.save(car);
	}

}
