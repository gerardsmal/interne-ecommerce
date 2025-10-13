package com.betacom.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseBase> create(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			prodS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status= HttpStatus.BAD_REQUEST;
		}
		
		 return ResponseEntity.status(status).body(r);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<ResponseBase> update(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			prodS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status= HttpStatus.BAD_REQUEST;
		}
		
		 return ResponseEntity.status(status).body(r);
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseBase> delete(@RequestBody (required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			prodS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status= HttpStatus.BAD_REQUEST;
		}
		
		return ResponseEntity.status(status).body(r);
	}
	
	
	@GetMapping("/list")
	public  ResponseEntity<ResponseList<ProdottoDTO>> list(
			@RequestParam (required = false) Integer id,
			@RequestParam (required = false) String desc,
			@RequestParam (required = false) String artist,
			@RequestParam (required = false) String famiglia
			){
		
		ResponseList<ProdottoDTO> r = new ResponseList<ProdottoDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(prodS.list(id,desc, artist, famiglia));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status= HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
}
