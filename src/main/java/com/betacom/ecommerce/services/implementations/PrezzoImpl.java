package com.betacom.ecommerce.services.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.repositories.IPrezzoRepository;
import com.betacom.ecommerce.repositories.IProdottoRepository;
import com.betacom.ecommerce.requests.PrezzoReq;
import com.betacom.ecommerce.services.IMessaggiServices;
import com.betacom.ecommerce.services.interfaces.IPrezzoServices;
import com.betacom.ecommerce.utils.Supporto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class PrezzoImpl implements IPrezzoServices{

	private IPrezzoRepository prezzoR;
	private IProdottoRepository prodR;
	private IMessaggiServices   msgS;
	
	
	public PrezzoImpl(IPrezzoRepository prezzoR, IProdottoRepository prodR, IMessaggiServices   msgS) {
		this.prezzoR = prezzoR;
		this.prodR = prodR;
		this.msgS  = msgS;
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void addPrezzo(PrezzoReq req) throws Exception {
		log.debug("addPrezzo:" + req);
		
		if (req.getSupporto() == null)
			throw new Exception(msgS.getMessaggio("prezzo_no_supporto"));
		
		Optional<Prodotto> prod = prodR.findById(req.getIdProdotto());
		if (prod.isEmpty())
			throw new Exception(msgS.getMessaggio("prod_ntfnd"));
		
		Supporto sup = null;
		try {
			sup =Supporto.valueOf(req.getSupporto());
		} catch (IllegalArgumentException e) {
			throw new Exception(msgS.getMessaggio("prezzo_no_supporto"));
		}
		
		Prezzo pr = null;
		Optional<Prezzo> prez = prezzoR.findBySupporto(sup);
		if (prez.isPresent()) {
			pr = prez.get();
		} else {
			pr = new Prezzo();
		}
	
		pr.setSupporto(Supporto.valueOf(req.getSupporto()));

		if (req.getPrezzo() == null)
			throw new Exception(msgS.getMessaggio("prod_no_prezzo"));
		
		pr.setPrezzo(req.getPrezzo());
		
		pr.setProdotto(prod.get());
		
		prezzoR.save(pr);
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void removePrezzo(PrezzoReq req) throws Exception {
		log.debug("addPrezzo:" + req);
		
		Optional<Prezzo> pr = prezzoR.findById(req.getId());
		if (pr.isEmpty())
			throw new Exception(msgS.getMessaggio("prezzo_ntfnd"));
		
		prezzoR.delete(pr.get());
		
	}
	
	
	

}
