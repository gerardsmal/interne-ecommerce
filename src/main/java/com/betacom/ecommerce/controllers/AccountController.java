package com.betacom.ecommerce.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;
import com.betacom.ecommerce.response.ResponseBase;
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
	public ResponseBase create(@RequestBody (required = true) AccountReq req) {
		ResponseBase r = new ResponseBase();
		try {
			accS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@PostMapping("/login")
	ResponseObject<SigninDTO> login (@RequestBody (required = true) SigninReq req){
		ResponseObject<SigninDTO> r = new ResponseObject<SigninDTO>();
		try {
			r.setDati(accS.login(req));
			r.setRc(true);
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
		
	}
}
