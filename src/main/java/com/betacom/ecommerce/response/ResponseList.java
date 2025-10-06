package com.betacom.ecommerce.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseList<T> extends ResponseBase{
	private List<T> dati;
}
