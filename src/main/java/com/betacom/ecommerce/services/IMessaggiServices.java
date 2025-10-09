package com.betacom.ecommerce.services;

public interface IMessaggiServices {
	String getMessaggio(String code);
	
	void validateWithRegex(String value, String regex, String msgKey) throws Exception; 
	void checkNotNull(Object value, String messageKey) throws Exception;
}
