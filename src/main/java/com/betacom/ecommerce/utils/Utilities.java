package com.betacom.ecommerce.utils;

import java.util.List;

import com.betacom.ecommerce.dto.FamigliaDTO;
import com.betacom.ecommerce.dto.PrezzoDTO;
import com.betacom.ecommerce.models.Famiglia;
import com.betacom.ecommerce.models.Prezzo;

public class Utilities {

	public static List<FamigliaDTO> buildFamigliaDTOList(List<Famiglia> lF){
		return lF.stream()
				.map(f -> FamigliaDTO.builder()
						.id(f.getId())
						.descrizione(f.getDescrizione())
						.build())
				.toList();
	}
	
	public static List<PrezzoDTO> buildPrezzoDTOList(List<Prezzo> lP){
		return lP.stream()
				.map (p -> PrezzoDTO.builder()
						.id(p.getId())
						.supporto(p.getSupporto().toString())
						.prezzo(p.getPrezzo())
						.build())
				.toList();
	}

}
