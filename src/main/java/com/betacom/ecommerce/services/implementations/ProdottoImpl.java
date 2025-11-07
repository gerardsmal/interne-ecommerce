package com.betacom.ecommerce.services.implementations;

import static com.betacom.ecommerce.utils.Utilities.buildFamigliaDTOList;
import static com.betacom.ecommerce.utils.Utilities.buildPrezzoDTOList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.dto.input.ProdottoReq;
import com.betacom.ecommerce.dto.output.ArtistaDTO;
import com.betacom.ecommerce.dto.output.FamigliaDTO;
import com.betacom.ecommerce.dto.output.ProdottoDTO;
import com.betacom.ecommerce.exception.EcommerceException;
import com.betacom.ecommerce.models.Artist;
import com.betacom.ecommerce.models.Famiglia;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.repositories.IArtistRepository;
import com.betacom.ecommerce.repositories.IFamigliaRepository;
import com.betacom.ecommerce.repositories.IProdottoRepository;
import com.betacom.ecommerce.services.interfaces.IValidationServices;
import com.betacom.ecommerce.services.interfaces.IProdottoServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProdottoImpl implements IProdottoServices{

	private IProdottoRepository repP;
	private IArtistRepository   artistR;
	private IValidationServices   msgS;

	
	public ProdottoImpl(IProdottoRepository repP, 
			IFamigliaRepository repF, 
			IArtistRepository   artistR,
			IValidationServices   msgS) {
		this.repP = repP;
		this.artistR = artistR;
		this.msgS = msgS;
	}


	@Transactional (rollbackFor =  Exception.class)	
	@Override
	public void create(ProdottoReq req) throws Exception {
	
		log.debug("Begin create:" + req);
		msgS.checkNotNull(req.getDescrizione(), "prod_no_desc");

		Optional<Prodotto> prod = repP.findByDescrizione(req.getDescrizione().trim());
		if (prod.isPresent())
			throw new Exception(msgS.getMessaggio("prod_fnd"));
		
		msgS.checkNotNull(req.getIdFamiglia(), "prod_no_famiglia");
		msgS.checkNotNull(req.getIdArtist(), "prod_no_artist");
		
		Artist artist = artistR.findById(req.getIdArtist())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("artist_ntfnd")));
	
		Famiglia fam = controlFamiglia(artist.getFamiglia(), req.getIdFamiglia());
		
		Prodotto p = new Prodotto();
		p.setDescrizione(req.getDescrizione().trim());
		p.setFamiglia(fam);
		p.setArtista(artist);
		
		repP.save(p);
		
	}

	@Transactional (rollbackFor =  Exception.class)
	@Override
	public void update(ProdottoReq req) throws Exception {
		log.debug("Begin update:" + req);
		Prodotto prod = repP.findById(req.getId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prod_ntfnd")));
		
		Optional.ofNullable(req.getDescrizione()).ifPresent(prod::setDescrizione);
				
		if (req.getIdArtist() != null) {
			Artist artist = artistR.findById(req.getIdArtist())
					.orElseThrow(() -> new Exception(msgS.getMessaggio("artist_ntfnd")));
			prod.setArtista(artist);			
		}
		
		
		Optional.ofNullable(req.getIdFamiglia())
			.ifPresent(idFamiglia -> {
				prod.setFamiglia(controlFamiglia(prod.getArtista().getFamiglia(), idFamiglia));
			});
		
		repP.save(prod);
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(ProdottoReq req) throws Exception {
		log.debug("Begin delete:" + req);
		Prodotto prod = repP.findById(req.getId())
				.orElseThrow(() -> new Exception(msgS.getMessaggio("prod_ntfnd")));
		
		repP.delete(prod);
		
	}

	

	@Override
	public List<ProdottoDTO> list(Integer id, String desc, String artist, String famiglia) throws Exception {
		log.debug("list:" + id + "/" + desc);
		List<Prodotto> lP = repP.searchByFilter(id, desc, artist, famiglia);
		log.debug("prodotti trovati:" + lP.size());
		return lP.stream()
				.map(p -> ProdottoDTO.builder()
						.id(p.getId())
						.descrizione(p.getDescrizione())
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
						.prezzo(buildPrezzoDTOList(p.getPrezzo()))
						.build()
						)
						
				.toList();
		
	}
	
	/*
	 * control family validity
	 * family must be compatible with original artist family
	 */
	Famiglia controlFamiglia(List<Famiglia> lF, Integer idFamiglia) throws EcommerceException{
		Famiglia fam = lF.stream()
			    .filter(f -> f.getId() == idFamiglia)
			    .findFirst()
			    .orElseThrow(() -> new EcommerceException(msgS.getMessaggio("prod_fam.incomp")));
		return fam;
	}
	

}
