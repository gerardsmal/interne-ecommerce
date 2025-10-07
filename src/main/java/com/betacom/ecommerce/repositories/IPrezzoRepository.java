package com.betacom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.utils.Supporto;

public interface IPrezzoRepository extends JpaRepository<Prezzo, Integer>{

	Optional<Prezzo> findBySupporto(Supporto supporto);
}
