package com.betacom.ecommerce.controllers;

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

import com.betacom.ecommerce.dto.FamigliaDTO;
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
	public ResponseEntity<ResponseBase> create(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase(); 
		HttpStatus status = HttpStatus.OK;
		try {
			famS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseBase> update(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			famS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}


	@DeleteMapping("/delete")
	public ResponseEntity<ResponseBase> delete(@RequestBody (required = true) FamigliaReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			famS.delete(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseList<FamigliaDTO>> list(){
		ResponseList<FamigliaDTO> r = new ResponseList<FamigliaDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(famS.list());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/listByProduct")
	public ResponseEntity<ResponseList<ProdottoFamigliaDTO>> listByProduct(){
		ResponseList<ProdottoFamigliaDTO> r = new ResponseList<ProdottoFamigliaDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(famS.listPerFamiglia());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@GetMapping("/listProductbyId")
	public ResponseEntity<ResponseObject<ProdottoFamigliaDTO>> listProductbyId(@RequestParam (required = true) Integer id){
		ResponseObject<ProdottoFamigliaDTO> r = new ResponseObject<ProdottoFamigliaDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(famS.ListByIdProdottoFamiglia(id));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	
}
