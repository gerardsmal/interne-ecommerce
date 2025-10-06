package com.betacom.ecommerce.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBase {
	private Boolean rc;
	private String msg;
}
