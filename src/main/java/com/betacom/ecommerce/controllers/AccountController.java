package com.betacom.ecommerce.controllers;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.AccountDTO;
import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
import com.betacom.ecommerce.response.ResponseObject;
import com.betacom.ecommerce.services.interfaces.IAccountServices;
import com.betacom.ecommerce.utils.Role;

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
	
	@GetMapping("/list")
	public ResponseList<AccountDTO> list(
			@RequestParam (required = false) Integer id,
			@RequestParam (required = false) String nome,
			@RequestParam (required = false) String cognome,
			@RequestParam (required = false) String commune,
			@RequestParam (required = false) String status,
			@RequestParam (required = false) String role
			){	
		ResponseList<AccountDTO> r = new ResponseList<AccountDTO>();
		try {
			r.setDati(accS.list(id, nome, cognome, commune, status, role));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
}
