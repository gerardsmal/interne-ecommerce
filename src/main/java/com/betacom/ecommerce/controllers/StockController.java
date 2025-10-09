package com.betacom.ecommerce.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.requests.StockReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.services.interfaces.IStockServices;

@RestController
@RequestMapping("rest/stock")
public class StockController {
	private IStockServices stockS;
	
	public StockController(IStockServices stockS) {
		this.stockS = stockS;
	}

	@PostMapping("/update")
	public ResponseBase update(@RequestBody (required = true) StockReq req) {
		ResponseBase r = new ResponseBase();
		try {
			stockS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@DeleteMapping("/delete")
	public ResponseBase delete(@RequestBody (required = true) StockReq req) {
		ResponseBase r = new ResponseBase();
		try {
			stockS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}

	
}
