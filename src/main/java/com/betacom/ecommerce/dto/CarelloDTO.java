package com.betacom.ecommerce.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarelloDTO {

	private double prezzoTotale;
	List<CarelloRigaDTO> riga;
}
