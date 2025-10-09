package com.betacom.ecommerce.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.ecommerce.models.MessageID;
import com.betacom.ecommerce.models.Messaggi;
import com.betacom.ecommerce.repositories.IMessaggiRepository;
import com.betacom.ecommerce.services.IMessaggiServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessaggioImpl implements IMessaggiServices{

	private IMessaggiRepository msgR;
	
	@Value("${lang}")
	private String lang;
	
	public MessaggioImpl(IMessaggiRepository msgR) {
		this.msgR = msgR;
	}


	@Override
	public String getMessaggio(String code) {
		log.debug("getMessaggio:" + code);
		String r = null;
		Optional<Messaggi> m = msgR.findById(new MessageID(lang, code));
		if (m.isEmpty())
			r= code;
		else
			r= m.get().getMessaggio();
		
		return r;
	}
	
	@Override
	public void validateWithRegex(String value, String regex, String msgKey) throws Exception {
	    if (value == null || !value.trim().matches(regex)) {
	        throw new Exception(getMessaggio(msgKey));
	    }
	}
	@Override
	public void checkNotNull(Object value, String messageKey) throws Exception {
	    if (value == null || (value instanceof String s && s.trim().isEmpty())) {
	        throw new Exception(getMessaggio(messageKey));
	    }
	}
}
