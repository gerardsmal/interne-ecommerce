package com.betacom.ecommerce.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseObject<T> extends ResponseBase{
	private T dati;
}
