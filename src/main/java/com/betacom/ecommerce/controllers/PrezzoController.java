package com.betacom.ecommerce.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.requests.PrezzoReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.services.interfaces.IPrezzoServices;

@RestController
@RequestMapping("rest/prezzo")
public class PrezzoController {

	private IPrezzoServices prezzoS;

	public PrezzoController(IPrezzoServices prezzoS) {
		this.prezzoS = prezzoS;
	}
	
	@PostMapping("/addPrezzo")
	public ResponseBase create(@RequestBody (required = true) PrezzoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			prezzoS.addPrezzo(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@DeleteMapping("/deletePrezzo")
	public ResponseBase deletePrezzo(@RequestBody (required = true) PrezzoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			prezzoS.removePrezzo(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
}
