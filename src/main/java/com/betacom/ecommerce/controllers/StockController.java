package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseBase> update(@RequestBody (required = true) StockReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			stockS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseBase> delete(@RequestBody (required = true) StockReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			stockS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	
}
