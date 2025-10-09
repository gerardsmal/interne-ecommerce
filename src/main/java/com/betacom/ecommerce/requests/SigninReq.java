package com.betacom.ecommerce.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SigninReq {
	private String userName;
	private String password;
}
