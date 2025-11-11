package com.betacom.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ecommerce.models.Famiglia;

public interface IFamigliaRepository extends JpaRepository<Famiglia, Integer>{
	Optional<Famiglia> findByDescrizione(String descrizione);
	boolean existsByDescrizione(String descrizione);
	
	List<Famiglia> findByDescrizioneContainingIgnoreCase(String pattern);  // like %pattern%
}
