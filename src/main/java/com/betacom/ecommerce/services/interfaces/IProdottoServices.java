package com.betacom.ecommerce.services.interfaces;

import java.util.List;

import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.requests.ProdottoReq;

public interface IProdottoServices {
	
	void create(ProdottoReq req) throws Exception;
	void update(ProdottoReq req) throws Exception;
	void delete(ProdottoReq req) throws Exception;
	
	List<ProdottoDTO> list() throws Exception;
}
