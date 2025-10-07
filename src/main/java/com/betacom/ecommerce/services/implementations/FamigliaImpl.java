package com.betacom.ecommerce.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.dto.ProdottoDTO;
import com.betacom.ecommerce.dto.ProdottoFamigliaDTO;
import com.betacom.ecommerce.models.Famiglia;
import com.betacom.ecommerce.models.Prodotto;
import com.betacom.ecommerce.repositories.IFamigliaRepository;
import com.betacom.ecommerce.requests.FamigliaReq;
import com.betacom.ecommerce.services.interfaces.IFamigliaServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FamigliaImpl  implements IFamigliaServices{

	private IFamigliaRepository repoF;
	
	
	public FamigliaImpl(IFamigliaRepository repoF) {
		this.repoF = repoF;
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void create(FamigliaReq req) throws Exception {
		log.debug("Begin create:" + req);
		
		Optional<Famiglia> fam = repoF.findByDescrizione(req.getDescrizione().trim());
		if (fam.isPresent()) {
			throw new Exception("Famiglia presente in DB");
		}
		Famiglia f = new Famiglia();
		f.setDescrizione(req.getDescrizione().trim());
		
		repoF.save(f);
		
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void update(FamigliaReq req) throws Exception {
		log.debug("Begin update:" + req);
		
		Optional<Famiglia> fam = repoF.findById(req.getId());
		if (fam.isEmpty()) {
			throw new Exception("Famiglia non presente in DB");
		}
		Famiglia f = fam.get();
		
		if (req.getDescrizione() != null) {
			f.setDescrizione(req.getDescrizione().trim());
			
			repoF.save(f);
			
		}
		
	}
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(FamigliaReq req) throws Exception {
		log.debug("Begin delete:" + req);
		
		Optional<Famiglia> fam = repoF.findById(req.getId());
		if (fam.isEmpty()) {
			throw new Exception("Famiglia non presente in DB");
		}
		
		try {
			repoF.delete(fam.get());
		} catch (Exception e) {
			throw new Exception("Famiglia con figli attivi");
		}
	}


	@Override
	public List<FamigliaDTO> list() throws Exception {
		log.debug("Begin list");
		List<Famiglia> lF = repoF.findAll();
		return lF.stream()
				.map(f -> FamigliaDTO.builder()
						.id(f.getId())
						.descrizione(f.getDescrizione())
						.build())
				.toList();
	}


	@Override
	public List<ProdottoFamigliaDTO> listPerFamiglia() throws Exception {
		log.debug("Begin listPerFamiglia");
		List<Famiglia> lF = repoF.findAll();
		
		return lF.stream()
				.map(f -> ProdottoFamigliaDTO.builder()
						.id(f.getId())
						.descrizione(f.getDescrizione())
						.prodotto(buildProdottoList(f.getProdotto()))
						.build())
				.toList();						
	}

	@Override
	public ProdottoFamigliaDTO ListByIdProdottoFamiglia(Integer id) throws Exception {
		log.debug("Begin ListByIdProdottoFamiglia :" + id);
		Optional<Famiglia> fam = repoF.findById(id);
		if (fam.isEmpty()) {
			throw new Exception("Famiglia non presente in DB");
		}
		
		return  ProdottoFamigliaDTO.builder()
				.id(fam.get().getId())
				.descrizione(fam.get().getDescrizione())
				.prodotto(buildProdottoList(fam.get().getProdotto()))
				.build();
	}
	
	private List<ProdottoDTO> buildProdottoList(List<Prodotto> lP){
		return lP.stream()
				.map(p -> ProdottoDTO.builder()
						.id(p.getId())
						.descrizione(p.getDescrizione())
						.famiglia(FamigliaDTO.builder()
								.id(p.getFamiglia().getId())
								.descrizione(p.getFamiglia().getDescrizione())
								.build())
						.build())
				.toList();
	}



}
