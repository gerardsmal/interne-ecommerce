package com.betacom.ecommerce.services.interfaces;

import java.util.List;

import com.betacom.ecommerce.dto.ArtistaDTO;
import com.betacom.ecommerce.requests.ArtistReq;
import com.betacom.ecommerce.requests.ChangeFamilyReq;

public interface IArtistServices {
	void create(ArtistReq req) throws Exception;
	void update(ArtistReq req) throws Exception;
	void remove(ArtistReq req) throws Exception;
	
	void removeFamigliaArtist(ArtistReq req) throws Exception;
	void changeFamily(ChangeFamilyReq req) throws Exception;
	
	ArtistaDTO listByArtista(Integer id) throws Exception;
	List<ArtistaDTO> list() throws Exception;

	
}
