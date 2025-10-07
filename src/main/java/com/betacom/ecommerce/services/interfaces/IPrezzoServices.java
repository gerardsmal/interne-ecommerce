package com.betacom.ecommerce.services.interfaces;

import com.betacom.ecommerce.requests.PrezzoReq;

public interface IPrezzoServices {

	void addPrezzo(PrezzoReq req) throws Exception;
	void removePrezzo(PrezzoReq req) throws Exception;
}
