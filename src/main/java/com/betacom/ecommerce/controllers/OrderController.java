package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.AccountDTO;
import com.betacom.ecommerce.dto.OrderDTO;
import com.betacom.ecommerce.requests.OrderReq;
import com.betacom.ecommerce.requests.RigaCarelloReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
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

	@PostMapping("/confirm")
	public ResponseEntity<ResponseBase> confirm(@RequestBody (required = true) OrderReq req) {
		ResponseBase r = new ResponseBase(); 
		HttpStatus status = HttpStatus.OK;
		try {
			orderS.confirm(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseBase> deleteRiga(@RequestBody (required = true) OrderReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			orderS.remove(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseList<OrderDTO>> list(@RequestParam (required = true) Integer id){	
		ResponseList<OrderDTO> r = new ResponseList<OrderDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(orderS.listByAccountId(id));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
}
