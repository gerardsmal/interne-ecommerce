package com.betacom.ecommerce.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemDTO {
	private Integer id;
	private LocalDate dataCreazione;
	private String productName;
	private String artist;
	private String genere;
	private String supporto;
	private Integer quantita;
	private double prezzoUnitario;
	private double prezzoDaPagare;

}
