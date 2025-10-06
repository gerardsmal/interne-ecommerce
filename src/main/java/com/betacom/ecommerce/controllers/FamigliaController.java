package com.betacom.ecommerce.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.dto.ProdottoFamigliaDTO;
import com.betacom.ecommerce.requests.FamigliaReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
import com.betacom.ecommerce.response.ResponseObject;
import com.betacom.ecommerce.services.interfaces.IFamigliaServices;

@RestController
@RequestMapping("rest/famiglia")
public class FamigliaController {

	private IFamigliaServices famS;

	public FamigliaController(IFamigliaServices famS) {
		this.famS = famS;
	}

	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase(); 
		try {
			famS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}

	@PutMapping("/update")
	public ResponseBase update(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase();
		try {
			famS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}


	@DeleteMapping("/delete")
	public ResponseBase delete(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase();
		try {
			famS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@GetMapping("/list")
	public ResponseList<FamigliaDTO> list(){
		ResponseList<FamigliaDTO> r = new ResponseList<FamigliaDTO>();
		try {
			r.setDati(famS.list());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
	
	@GetMapping("/listByProduct")
	public ResponseList<ProdottoFamigliaDTO> listByProduct(){
		ResponseList<ProdottoFamigliaDTO> r = new ResponseList<ProdottoFamigliaDTO>();
		try {
			r.setDati(famS.listPerFamiglia());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	@GetMapping("/listProductbyId")
	public ResponseObject<ProdottoFamigliaDTO> listProductbyId(@RequestParam (required = true) Integer id){
		ResponseObject<ProdottoFamigliaDTO> r = new ResponseObject<ProdottoFamigliaDTO>();
		try {
			r.setDati(famS.ListByIdProdottoFamiglia(id));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
	
	
}
