package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.requests.CarelloReq;
import com.betacom.ecommerce.requests.RigaCarelloReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.services.interfaces.ICarelloServices;

@RestController
@RequestMapping("rest/carello")
public class CarelloController {
	private ICarelloServices carS;
	
	public CarelloController(ICarelloServices carS) {
		this.carS = carS;
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseBase> create(@RequestBody (required = true) CarelloReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			carS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@PostMapping("/addRiga")
	public ResponseEntity<ResponseBase> addRiga(@RequestBody (required = true) RigaCarelloReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			carS.addRiga(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);	}
	
	@DeleteMapping("/deleteRiga")
	public ResponseEntity<ResponseBase> deleteRiga(@RequestBody (required = true) RigaCarelloReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			carS.removeRiga(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
