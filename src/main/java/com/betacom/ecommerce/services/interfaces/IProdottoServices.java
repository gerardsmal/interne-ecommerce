package com.betacom.ecommerce.services.interfaces;

import java.util.List;

import com.betacom.ecommerce.dto.input.ProdottoReq;
import com.betacom.ecommerce.dto.output.ProdottoDTO;

public interface IProdottoServices {
	
	void create(ProdottoReq req) throws Exception;
	void update(ProdottoReq req) throws Exception;
	void delete(ProdottoReq req) throws Exception;
	
	List<ProdottoDTO> list(Integer id, String desc, String artist, String famiglia) throws Exception;
}
