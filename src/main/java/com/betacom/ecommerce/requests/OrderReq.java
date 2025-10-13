package com.betacom.ecommerce.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderReq {
	private Integer id;
	private String statusPagamento;
	private Integer accountID;

}
