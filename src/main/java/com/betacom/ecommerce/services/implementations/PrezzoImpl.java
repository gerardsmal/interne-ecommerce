package com.betacom.ecommerce.services.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.repositories.IPrezzoRepository;
import com.betacom.ecommerce.repositories.IProdottoRepository;
import com.betacom.ecommerce.requests.PrezzoReq;
import com.betacom.ecommerce.services.interfaces.IValidationServices;
import com.betacom.ecommerce.services.interfaces.IPrezzoServices;
import com.betacom.ecommerce.utils.Supporto;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class PrezzoImpl implements IPrezzoServices{

	private IPrezzoRepository prezzoR;
	private IProdottoRepository prodR;
	private IValidationServices   msgS;
	
	
	public PrezzoImpl(IPrezzoRepository prezzoR, IProdottoRepository prodR, IValidationServices   msgS) {
		this.prezzoR = prezzoR;
		this.prodR = prodR;
		this.msgS  = msgS;
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void addPrezzo(PrezzoReq req) throws Exception {
		log.debug("addPrezzo:" + req);
		msgS.checkNotNull(req.getSupporto(), "prezzo_no_supporto");
		
		
		Prodotto prod = prodR.findById(req.getIdProdotto())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prod_ntfnd")));
		
		Supporto sup = null;
		try {
			sup =Supporto.valueOf(req.getSupporto());
		} catch (IllegalArgumentException e) {
			throw new Exception(msgS.getMessaggio("prezzo_no_supporto"));
		}
		
		Prezzo pr = null;
		
		try {
			pr = msgS.searchSupporto(prod.getPrezzo(), sup);			
		} catch (Exception e) {  // if support no exist
			pr = new Prezzo();
		}

		pr.setSupporto(Supporto.valueOf(req.getSupporto()));

		msgS.checkNotNull(req.getPrezzo(), "prod_no_prezzo");
		
		pr.setPrezzo(req.getPrezzo());	
		pr.setProdotto(prod);
		
		prezzoR.save(pr);
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void removePrezzo(PrezzoReq req) throws Exception {
		log.debug("addPrezzo:" + req);
		
		Prezzo pr = prezzoR.findById(req.getId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prezzo_ntfnd")));
		
		prezzoR.delete(pr);
		
	}
	
	
	

}
