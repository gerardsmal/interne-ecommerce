package com.betacom.ecommerce.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.Stock;
import com.betacom.ecommerce.repositories.IPrezzoRepository;
import com.betacom.ecommerce.repositories.IStockRepositoy;
import com.betacom.ecommerce.requests.PickItemReq;
import com.betacom.ecommerce.requests.StockReq;
import com.betacom.ecommerce.services.interfaces.IValidationServices;
import com.betacom.ecommerce.services.interfaces.IStockServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockImpl implements IStockServices{
	private IStockRepositoy stockR;
	private IPrezzoRepository prezzoR;
	private IValidationServices msgS;
	
	
	public StockImpl(IStockRepositoy stockR, IPrezzoRepository prezzoR, IValidationServices msgS) {
		super();
		this.stockR = stockR;
		this.prezzoR = prezzoR;
		this.msgS = msgS;
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void update(StockReq req) throws Exception {
		log.debug("update :" + req);
		Prezzo prez = prezzoR.findById(req.getPrezzoId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prezzo_ntfnd")));
		
		Stock stock = null;
		if (prez.getStock() == null) {
			stock = new Stock();
		} else {
			stock = prez.getStock();
		}
		if (req.getCurrentStock() != null)
			stock.setCurrentStock(req.getCurrentStock());
		if (req.getStockAlert() != null)
			stock.setStockAlert(req.getStockAlert());
		
		/*
		 * control stock
		 */
		if (stock.getCurrentStock() == null)
			throw new Exception(msgS.getMessaggio("stock_no_current"));
		if (stock.getStockAlert() == null)
			throw new Exception(msgS.getMessaggio("stock_no_alert"));
		
		stockR.save(stock);
		log.debug("Stocke saved");
		
		prez.setStock(stock);	
		
		prezzoR.save(prez);		
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(StockReq req) throws Exception {
		log.debug("delete :" + req);
		Prezzo prez = prezzoR.findById(req.getPrezzoId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prezzo_ntfnd")));

		if (prez.getStock() == null) 
			throw new Exception(msgS.getMessaggio("stock_ntfnd"));
		
		int id = prez.getStock().getId();
		
		prez.setStock(null);
		prezzoR.save(prez);
		log.debug("save prezzo");
		
		
		stockR.deleteById(id);

	}
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void pickItem(PickItemReq req) throws Exception {
		log.debug("pickItem :" + req);

		Prezzo prez = prezzoR.findById(req.getPrezzoId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prezzo_ntfnd")));

		msgS.checkNotNull(prez.getStock(), "stock_ntfnd");
		/*
		if (prez.getStock() == null) 
			throw new Exception(msgS.getMessaggio("stock_ntfnd"));
		*/	
		Stock st = prez.getStock();
		
		st.setCurrentStock(st.getCurrentStock() - req.getNumeroItems());
		
		if (st.getCurrentStock() <= 0)
			throw new Exception(msgS.getMessaggio("stock_no_qta"));
		stockR.save(st);
		
	}

	@Override
	public void restoreItem(PickItemReq req) throws Exception {
		log.debug("restoreItem:" + req);
		Prezzo prez = prezzoR.findById(req.getPrezzoId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prezzo_ntfnd")));
		
		msgS.checkNotNull(prez.getStock(), "stock_ntfnd");
		Stock st = prez.getStock();
		
		st.setCurrentStock(st.getCurrentStock() + req.getNumeroItems());
		stockR.save(st);
	}

}
