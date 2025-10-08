package com.betacom.ecommerce.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PickItemReq {
	private Integer prezzoId;
	private Integer numeroItems;

}
