package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.requests.OrderReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.services.interfaces.IOrderServices;

@RestController
@RequestMapping("rest/order")
public class OrderController {

	private IOrderServices orderS;

	public OrderController(IOrderServices orderS) {
		this.orderS = orderS;
	}

	
	@PostMapping("/create")
	public ResponseEntity<ResponseBase> create(@RequestBody (required = true) OrderReq req) {
		ResponseBase r = new ResponseBase(); 
		HttpStatus status = HttpStatus.OK;
		try {
			orderS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
