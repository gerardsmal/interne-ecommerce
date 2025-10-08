package com.betacom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.requests.ProdottoReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
import com.betacom.ecommerce.services.interfaces.IProdottoServices;

@RestController
@RequestMapping("rest/prodotto")
public class ProdottoController {

	@Autowired
	private IProdottoServices prodS;
	
	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			prodS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	
	@PutMapping("/update")
	public ResponseBase update(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			prodS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	
	@DeleteMapping("/delete")
	public ResponseBase delete(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			prodS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	
	@GetMapping("/list")
	public ResponseList<ProdottoDTO> list(
			@RequestParam (required = false) Integer id,
			@RequestParam (required = false) String desc,
			@RequestParam (required = false) String artist,
			@RequestParam (required = false) String famiglia
			){
		
		ResponseList<ProdottoDTO> r = new ResponseList<ProdottoDTO>();
		try {
			r.setDati(prodS.list(id,desc, artist, famiglia));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
}
