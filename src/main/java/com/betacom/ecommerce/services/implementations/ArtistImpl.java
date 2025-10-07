package com.betacom.ecommerce.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.dto.ArtistaDTO;
import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.models.Artist;
import com.betacom.ecommerce.models.Famiglia;
import com.betacom.ecommerce.repositories.IArtistRepository;
import com.betacom.ecommerce.repositories.IFamigliaRepository;
import com.betacom.ecommerce.requests.ArtistReq;
import com.betacom.ecommerce.requests.ChangeFamilyReq;
import com.betacom.ecommerce.services.IMessaggiServices;
import com.betacom.ecommerce.services.interfaces.IArtistServices;

import static com.betacom.ecommerce.utils.Utilities.buildFamigliaDTOList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArtistImpl implements IArtistServices{

	private IArtistRepository artS;
	private IFamigliaRepository famS;
	private IMessaggiServices  msgS;
	
	public ArtistImpl(IArtistRepository artS, IFamigliaRepository famS,IMessaggiServices  msgS) {
		this.artS = artS;
		this.famS = famS;
		this.msgS = msgS;
	}

	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void create(ArtistReq req) throws Exception {
		log.debug("create:" + req);
		if (req.getNome() == null)
			throw new Exception(msgS.getMessaggio("artist_no_name"));
		Optional<Artist> ar = artS.findByNome(req.getNome().trim());
		if (ar.isPresent())
			throw new Exception(msgS.getMessaggio("artist_ntfnd"));
			
		Artist artist = new Artist();
		artist.setNome(req.getNome().trim());
		if (req.getIdFamiglia() != null) {
			Optional<Famiglia> fam = famS.findById(req.getIdFamiglia());
			if (fam.isEmpty())
				throw new Exception(msgS.getMessaggio("fam_ntfnd"));
			artist.setFamiglia(new ArrayList<>()); // init famiglia
			artist.getFamiglia().add(fam.get());
		}
		
		artS.save(artist);
		
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void update(ArtistReq req) throws Exception {
		log.debug("update:" + req);
		Optional<Artist> ar = artS.findById(req.getId());
		if (ar.isEmpty())
			throw new Exception("Artiste non trovato");
		
		Artist artist = ar.get();
		if (req.getNome() != null) {
			Optional<Artist> pr = artS.findByNome(req.getNome().trim());
			if (pr.isPresent())
				throw new Exception("Artiste esiste in DB");
			artist.setNome(req.getNome().trim());
		}
	
		// remove family if exist
		ar.ifPresent(arObj -> 
			arObj.getFamiglia().stream()
			.filter(f -> f.getId() == req.getIdFamiglia())
			.findFirst()
			.ifPresent(arObj.getFamiglia()::remove));

		if (req.getIdFamiglia() != null) {
			Optional<Famiglia> fam = famS.findById(req.getIdFamiglia());
			if (fam.isEmpty())
				throw new Exception("Famiglia non trovata");
			artist.getFamiglia().add(fam.get());			
		}
		
		

		artS.save(artist);	
		
		
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void removeFamigliaArtist(ArtistReq req) throws Exception {
		log.debug("remove:" + req);
		Optional<Artist> ar = artS.findById(req.getId());
		if (ar.isEmpty())
			throw new Exception("Artiste non trovato");
		
		int size = ar.get().getFamiglia().size(); 
		
		ar.ifPresent(arObj ->
				arObj.getFamiglia().stream()
				.filter(f -> f.getId() == req.getIdFamiglia())
				.findFirst()
				.ifPresent(arObj.getFamiglia()::remove)   
				);
		
		if (size == ar.get().getFamiglia().size())
			throw new Exception("Famiglia non non trovata per l'artista");
		
		
		artS.save(ar.get());
		
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void changeFamily(ChangeFamilyReq req) throws Exception {
		log.debug("changeFamily:" + req);
		ArtistReq reqInterne = new ArtistReq();
		reqInterne.setId(req.getId());
		reqInterne.setIdFamiglia(req.getIdFamiglia());
		removeFamigliaArtist(reqInterne);
		log.debug("After remove Family....");
		
		
		reqInterne.setIdFamiglia(req.getNewIdFamiglia());
		update(reqInterne);
		log.debug("After Update Family....");

	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void remove(ArtistReq req) throws Exception {
		log.debug("remove:" + req);
		Optional<Artist> ar = artS.findById(req.getId());
		
		if (ar.isEmpty())
			throw new Exception("Artiste non trovato");
	
		if (!ar.get().getProdotto().isEmpty())
			throw new Exception("Prodotto(i) associato all'artista");
		artS.delete(ar.get());
		
	}



	@Override
	public ArtistaDTO listByArtista(Integer id) throws Exception {
		log.debug("listByArtista:" + id);
		
		Optional<Artist> art = artS.findById(id);
		if (art.isEmpty())
			throw new Exception("Artiste non trovato");
		
		return ArtistaDTO.builder()
				.id(art.get().getId())
				.nome(art.get().getNome())
				.famiglia(buildFamigliaDTOList(art.get().getFamiglia()))
				.build();
	}


	@Override
	public List<ArtistaDTO> list() throws Exception {
		log.debug("list");
		List<Artist> lA = artS.findAll();

		return lA.stream()
				.map(art -> ArtistaDTO.builder()
						.id(art.getId())
						.nome(art.getNome())
						.famiglia(buildFamigliaDTOList(art.getFamiglia()))
						.build()
						)
				.toList();
	}


}
