package com.betacom.ecommerce.services.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.betacom.ecommerce.dto.AccountDTO;
import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;

public interface IAccountServices {

	void create (AccountReq req) throws  Exception;
	void update (AccountReq req) throws  Exception;

	SigninDTO login(SigninReq req) throws Exception;
	
	List<AccountDTO> list(
			Integer id,
			String nome,
			String cognome,
			String commune,
			String status,
			String role)
			throws Exception;
}
