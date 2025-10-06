package com.betacom.ecommerce.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProdottoReq {

	private Integer id;
	private String descrizione;
	private Double prezzo;
	private Integer idFamiglia;
	private Integer idArtist;

}
