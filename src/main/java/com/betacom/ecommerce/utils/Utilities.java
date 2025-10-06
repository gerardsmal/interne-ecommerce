package com.betacom.ecommerce.utils;

import java.util.List;

import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.models.Famiglia;

public class Utilities {

	public static List<FamigliaDTO> buildFamigliaDTOList(List<Famiglia> lF){
		return lF.stream()
				.map(f -> FamigliaDTO.builder()
						.id(f.getId())
						.descrizione(f.getDescrizione())
						.build())
				.toList();
	}
	
}
