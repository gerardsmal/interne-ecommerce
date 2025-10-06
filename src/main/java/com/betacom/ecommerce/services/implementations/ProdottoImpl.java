package com.betacom.ecommerce.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.dto.ArtistaDTO;
import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.models.Artist;
import com.betacom.ecommerce.models.Famiglia;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.repositories.IArtistRepository;
import com.betacom.ecommerce.repositories.IFamigliaRepository;
import com.betacom.ecommerce.repositories.IProdottoRepository;
import com.betacom.ecommerce.requests.ProdottoReq;
import com.betacom.ecommerce.services.interfaces.IProdottoServices;

import static com.betacom.ecommerce.utils.Utilities.buildFamigliaDTOList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProdottoImpl implements IProdottoServices{

	private IProdottoRepository repP;
	private IFamigliaRepository repF;
	private IArtistRepository   artistR;

	
	public ProdottoImpl(IProdottoRepository repP, IFamigliaRepository repF, IArtistRepository   artistR) {
		this.repP = repP;
		this.repF = repF;
		this.artistR = artistR;
	}


	@Transactional (rollbackFor =  Exception.class)	
	@Override
	public void create(ProdottoReq req) throws Exception {
	
		log.debug("Begin create:" + req);
		
		if (req.getDescrizione() == null) {
			throw new Exception("Descrizione non presente");
		}
		Optional<Prodotto> prod = repP.findByDescrizione(req.getDescrizione().trim());
		if (prod.isPresent())
			throw new Exception("Prodotto presente in DB");
		
		if (req.getPrezzo() == null) {
			throw new Exception("Prezzo non presente");
		}
		
		Optional<Famiglia> fam = repF.findById(req.getIdFamiglia());
		if (fam.isEmpty()) {
			throw new Exception("Famiglia non trovata in DB");
		}
		
		Optional<Artist> artist = artistR.findById(req.getIdArtist());
		if (artist.isEmpty())
			throw new Exception("Artista  non trovata in DB");
		
		Prodotto p = new Prodotto();
		p.setDescrizione(req.getDescrizione().trim());
		p.setPrezzo(req.getPrezzo());
		p.setFamiglia(fam.get());
		p.setArtista(artist.get());
		
		repP.save(p);
		
	}

	@Transactional (rollbackFor =  Exception.class)
	@Override
	public void update(ProdottoReq req) throws Exception {
		log.debug("Begin update:" + req);
		Optional<Prodotto> prod = repP.findById(req.getId());
		if (prod.isEmpty())
			throw new Exception("prodotto non trovato in DB");
		
		Prodotto p = prod.get();
		
		if (req.getDescrizione() != null) {
			p.setDescrizione(req.getDescrizione().trim());
		}
		
		if (req.getPrezzo() != null) {
			p.setPrezzo(req.getPrezzo());
		}
		
		if (req.getIdArtist() != null) {
			Optional<Artist> artist = artistR.findById(req.getIdArtist());
			if (artist.isEmpty())
				throw new Exception("Artista  non trovata in DB");
			p.setArtista(artist.get());			
		}
		
		if (req.getIdFamiglia() != null) {
			Optional<Famiglia> fam = repF.findById(req.getIdFamiglia());
			if (fam.isEmpty()) {
				throw new Exception("Famiglia non trovata in DB");
			}
			p.setFamiglia(fam.get());
		}
		
		repP.save(p);
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(ProdottoReq req) throws Exception {
		log.debug("Begin delete:" + req);
		Optional<Prodotto> prod = repP.findById(req.getId());
		if (prod.isEmpty())
			throw new Exception("prodotto non trovato in DB");
		
		
		repP.delete(prod.get());
		
	}

	

	@Override
	public List<ProdottoDTO> list() throws Exception {
		log.debug("list");
		List<Prodotto> lP = repP.findAll();
		log.debug("prodotti trovati:" + lP.size());
		return lP.stream()
				.map(p -> ProdottoDTO.builder()
						.id(p.getId())
						.descrizione(p.getDescrizione())
						.prezzo(p.getPrezzo())
						.famiglia(FamigliaDTO.builder()
								.id(p.getFamiglia().getId())
								.descrizione(p.getFamiglia().getDescrizione())
								.build())
						.artista(ArtistaDTO.builder()
								.id(p.getArtista().getId())
								.nome(p.getArtista().getNome())
								.famiglia(buildFamigliaDTOList(p.getArtista().getFamiglia()))
								.build()
								)
						.build())
				.toList();
		
	}

	



}
