package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.AccountDTO;
import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
import com.betacom.ecommerce.response.ResponseObject;
import com.betacom.ecommerce.services.interfaces.IAccountServices;

@RestController
@RequestMapping("rest/account")
public class AccountController {

	private IAccountServices accS;

	public AccountController(IAccountServices accS) {
		this.accS = accS;
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseBase> create(@RequestBody(required = true) AccountReq req) {
	    ResponseBase response = new ResponseBase();
	    HttpStatus status = HttpStatus.OK;

	    try {
	        accS.create(req);
	        response.setRc(true);
	    } catch (Exception e) {
	        response.setRc(false);
	        response.setMsg(e.getMessage());
	        status = HttpStatus.BAD_REQUEST;
	    }

	    return ResponseEntity.status(status).body(response);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseBase> update(@RequestBody(required = true) AccountReq req) {
	    ResponseBase response = new ResponseBase();
	    HttpStatus status = HttpStatus.OK;

	    try {
	        accS.update(req);
	        response.setRc(true);
	    } catch (Exception e) {
	        response.setRc(false);
	        response.setMsg(e.getMessage());
	        status = HttpStatus.BAD_REQUEST;
	    }

	    return ResponseEntity.status(status).body(response);
	}

	
	@PostMapping("/login")
	ResponseEntity<ResponseObject<SigninDTO>> login (@RequestBody (required = true) SigninReq req){
		ResponseObject<SigninDTO> r = new ResponseObject<SigninDTO>();
		  HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(accS.login(req));
			r.setRc(true);
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseList<AccountDTO>> list(
			@RequestParam (required = false) Integer id,
			@RequestParam (required = false) String nome,
			@RequestParam (required = false) String cognome,
			@RequestParam (required = false) String commune,
			@RequestParam (required = false) String stat,
			@RequestParam (required = false) String role
			){	
		ResponseList<AccountDTO> r = new ResponseList<AccountDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(accS.list(id, nome, cognome, commune, stat, role));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
}
