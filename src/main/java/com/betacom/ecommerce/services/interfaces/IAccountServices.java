package com.betacom.ecommerce.services.interfaces;

import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;

public interface IAccountServices {

	void create (AccountReq req) throws  Exception;
	SigninDTO login(SigninReq req) throws Exception;
}
