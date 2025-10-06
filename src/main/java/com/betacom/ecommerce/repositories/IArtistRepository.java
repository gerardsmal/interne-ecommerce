package com.betacom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ecommerce.models.Artist;

public interface IArtistRepository extends JpaRepository<Artist, Integer>{
	Optional<Artist> findByNome(String nome);

}
