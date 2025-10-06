package com.betacom.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ProdottoDTO {
	private Integer id;
	private String descrizione;
	private Double prezzo;
	private FamigliaDTO famiglia;
	private ArtistaDTO artista;
}
