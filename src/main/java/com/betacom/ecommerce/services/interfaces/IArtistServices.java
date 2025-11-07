package com.betacom.ecommerce.services.interfaces;

import java.util.List;

import com.betacom.ecommerce.dto.input.ArtistReq;
import com.betacom.ecommerce.dto.input.ChangeFamilyReq;
import com.betacom.ecommerce.dto.output.ArtistaDTO;

public interface IArtistServices {
	void create(ArtistReq req) throws Exception;
	void update(ArtistReq req) throws Exception;
	void remove(ArtistReq req) throws Exception;
	
	void removeFamigliaArtist(ArtistReq req) throws Exception;
	void changeFamily(ChangeFamilyReq req) throws Exception;
	
	ArtistaDTO listByArtista(Integer id) throws Exception;
	List<ArtistaDTO> list() throws Exception;

	
}
