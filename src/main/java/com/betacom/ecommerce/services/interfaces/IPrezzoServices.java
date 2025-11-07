package com.betacom.ecommerce.services.interfaces;

import com.betacom.ecommerce.dto.input.PrezzoReq;

public interface IPrezzoServices {

	void addPrezzo(PrezzoReq req) throws Exception;
	void removePrezzo(PrezzoReq req) throws Exception;
}
