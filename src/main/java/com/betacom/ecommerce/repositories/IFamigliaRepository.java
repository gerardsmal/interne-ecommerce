package com.betacom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ecommerce.models.Famiglia;

public interface IFamigliaRepository extends JpaRepository<Famiglia, Integer>{
	Optional<Famiglia> findByDescrizione(String descrizione);

}
