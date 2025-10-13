package com.betacom.ecommerce.services.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.models.Carello;
import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.models.RigaCarello;
import com.betacom.ecommerce.repositories.IAccountRepository;
import com.betacom.ecommerce.repositories.ICarelloRepository;
import com.betacom.ecommerce.repositories.IProdottoRepository;
import com.betacom.ecommerce.repositories.IRigaCarelloRepository;
import com.betacom.ecommerce.requests.CarelloReq;
import com.betacom.ecommerce.requests.PickItemReq;
import com.betacom.ecommerce.requests.RigaCarelloReq;
import com.betacom.ecommerce.services.interfaces.ICarelloServices;
import com.betacom.ecommerce.services.interfaces.IValidationServices;
import com.betacom.ecommerce.services.interfaces.IStockServices;
import com.betacom.ecommerce.utils.Supporto;



import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarelloImpl implements ICarelloServices{
	
	private ICarelloRepository carR;
	private IAccountRepository accountR;
	private IValidationServices  msgS;
	private IProdottoRepository prodR;
	private IRigaCarelloRepository rigaR;
	private IStockServices stockS;
	
	public CarelloImpl(ICarelloRepository carR, 
			IAccountRepository accountR, 
			IValidationServices msgS, 
			IProdottoRepository prodR,
			IRigaCarelloRepository rigaR,
			IStockServices stockS) {
		super();
		this.carR = carR;
		this.accountR = accountR;
		this.msgS = msgS;
		this.prodR = prodR;
		this.rigaR = rigaR;
		this.stockS = stockS;
	}
	
	@Transactional (rollbackFor = Exception.class)
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
	
	@Transactional (rollbackFor = Exception.class)	
	@Override
	public void addRiga(RigaCarelloReq req) throws Exception {
		log.debug("addRiga:" + req);
		Carello carello = carR.findById(req.getIdCarello())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("carello_ntfnd")));
		
		Prodotto prodotto = prodR.findById(req.getIdProdotto())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prod_ntfnd")));
		
		if (req.getQuantita() == null || req.getQuantita() <= 0)
			throw new Exception(msgS.getMessaggio("carello_quantita_ko"));
		
		Supporto sup = buildSupporto(req.getSupporto());
		
		Prezzo prezzo = msgS.searchSupporto(prodotto.getPrezzo(), sup);
				
		RigaCarello riga = new RigaCarello();
		riga.setDataCreazione(LocalDate.now());
		riga.setQuantita(req.getQuantita());
		riga.setCarello(carello);
		riga.setProdotto(prodotto);
		riga.setSupporto(sup);
		log.debug("Riga added...");
		
		if (prezzo.getStock() != null ) {
			stockS.pickItem(PickItemReq.builder()
					.prezzoId(prezzo.getId())
					.numeroItems(req.getQuantita())
					.build()
					);
			
			log.debug("Stock updated...");			
		}
		rigaR.save(riga);
		
	}
	
	@Transactional (rollbackFor = Exception.class)	
	@Override
	public void removeRiga(RigaCarelloReq req) throws Exception {
		log.debug("removeRiga:" + req);
		RigaCarello riga =  rigaR.findById(req.getId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("carello_elem_ko")));

		Prezzo prezzo = msgS.searchSupporto(riga.getProdotto().getPrezzo(), riga.getSupporto());

		if (prezzo.getStock() != null) {
			stockS.restoreItem(PickItemReq.builder()
					.prezzoId(prezzo.getId())
					.numeroItems(riga.getQuantita())
					.build()
					);
			
			log.debug("Stock updated...");
			
		}
		
		rigaR.delete(riga);
		
	}
	
	/*
	 * Questo metodo transforma la string supporto in enum
	 */
	private Supporto buildSupporto(String value) throws Exception{
		msgS.checkNotNull(value, "prezzo_no_supporto");		
		try {
			return Supporto.valueOf(value);				
		} catch (IllegalArgumentException e) {
			throw new Exception(msgS.getMessaggio("prezzo_no_supporto"));
		}
		
	}

}
