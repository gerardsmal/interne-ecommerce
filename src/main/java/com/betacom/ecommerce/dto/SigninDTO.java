package com.betacom.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SigninDTO {

	private Integer userID;
	private String role;
}
