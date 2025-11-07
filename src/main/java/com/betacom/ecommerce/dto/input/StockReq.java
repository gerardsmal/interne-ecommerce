package com.betacom.ecommerce.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StockReq {
	private Integer prezzoId;
	private Integer currentStock;
	private Integer stockAlert;

}
