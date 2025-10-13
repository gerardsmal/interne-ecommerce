package com.betacom.ecommerce.services.implementations;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.enums.StatoCarello;
import com.betacom.ecommerce.enums.StatusPagamento;
import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.models.Carello;
import com.betacom.ecommerce.models.Order;
import com.betacom.ecommerce.models.OrderItems;
import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.RigaCarello;
import com.betacom.ecommerce.repositories.IAccountRepository;
import com.betacom.ecommerce.repositories.ICarelloRepository;
import com.betacom.ecommerce.repositories.IOrderItemsRepository;
import com.betacom.ecommerce.repositories.IOrderRepository;
import com.betacom.ecommerce.requests.OrderReq;
import com.betacom.ecommerce.services.interfaces.IOrderServices;
import com.betacom.ecommerce.services.interfaces.IValidationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderImpl implements IOrderServices{

	private IAccountRepository accountR;
	private IOrderRepository   orderR;
	private IValidationServices validS;
	private IOrderItemsRepository itemR;
	private ICarelloRepository    carelloR;
	
	public OrderImpl(IAccountRepository accountR, 
			IOrderRepository orderR, 
			IValidationServices validS, 
			IOrderItemsRepository itemR,
			ICarelloRepository    carelloR) {
		this.accountR = accountR;
		this.orderR = orderR;
		this.validS = validS;
		this.itemR = itemR;
		this.carelloR = carelloR;
	}
	
	@Transactional (rollbackFor = Exception.class)	
	@Override
	public void create(OrderReq req) throws Exception {
		log.debug("create:" + req);
		Account ac = accountR.findById(req.getAccountID())
				.orElseThrow(() -> new Exception(validS.getMessaggio("account_ntfnd")));
		if (req.getStatusPagamento() == null) {
			req.setStatusPagamento("NON_PAGATO");
		}
		if (ac.getCarello().getStato() == StatoCarello.valueOf("ordine"))
			throw new Exception(validS.getMessaggio("order_not_available"));
		
		Order order = new Order();
		try {
			order.setStatusPagamento(StatusPagamento.valueOf(req.getStatusPagamento()));	
		} catch (IllegalArgumentException e) {
			throw new Exception(validS.getMessaggio("order_status_invalid"));
		}
		
		order.setDataOrdine(LocalDate.now());
		order.setAccount(ac);
		
		Integer id =orderR.save(order).getId();
		
		buildOrderItems(id);
		
	}
	
	@Transactional (rollbackFor = Exception.class)	
	private void buildOrderItems(Integer orderID) throws Exception{
		log.debug("buildOrderItems id:" + orderID);
		Order order = orderR.findById(orderID)
				.orElseThrow(() -> new Exception(validS.getMessaggio("order_ntfnd")));
		
		double totale = 0;
		for (RigaCarello riga: order.getAccount().getCarello().getRigaCarello()) {
			OrderItems item = new OrderItems();
			item.setDataCreazione(riga.getDataCreazione());
			item.setQuantita(riga.getQuantita());
			item.setProductName(riga.getProdotto().getDescrizione());
			item.setArtist(riga.getProdotto().getArtista().getNome());
			item.setGenere(riga.getProdotto().getFamiglia().getDescrizione());
			item.setSupporto(riga.getSupporto());
			Prezzo prezzo = validS.searchSupporto(riga.getProdotto().getPrezzo(), riga.getSupporto());
			item.setPrezzoUnit(prezzo.getPrezzo());
			item.setPrezzo(item.getPrezzoUnit() * riga.getQuantita());
			item.setOrder(order);
			totale = totale + item.getPrezzo();
			itemR.save(item);	
			log.debug("totale:" + totale);
						
		}
		
		order.setTotale(totale);
		orderR.save(order);
		
		Carello car = order.getAccount().getCarello();
		car.setStato(StatoCarello.valueOf("ordine"));
		carelloR.save(car);
	}
}
