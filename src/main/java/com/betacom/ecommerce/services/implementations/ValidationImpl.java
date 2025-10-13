package com.betacom.ecommerce.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.ecommerce.models.MessageID;
import com.betacom.ecommerce.models.Messaggi;
import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.repositories.IMessaggiRepository;
import com.betacom.ecommerce.services.interfaces.IValidationServices;
import com.betacom.ecommerce.utils.Supporto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValidationImpl implements IValidationServices{

	private IMessaggiRepository msgR;
	
	@Value("${lang}")
	private String lang;
	
	public ValidationImpl(IMessaggiRepository msgR) {
		this.msgR = msgR;
	}


	@Override
	public String getMessaggio(String code) {
		log.debug("getMessaggio:" + code);
		
		String r = msgR.findById(new MessageID(lang, code))
	               .map(Messaggi::getMessaggio)
	               .orElse(code);
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
	
	/*
	 * questo metodo search l'element prezzo giusto che corisponde al supporto
	 * se non lo trova lancia un exception
	 */
	public Prezzo searchSupporto(List<Prezzo> prezzi, Supporto sup) throws Exception {
	    return prezzi.stream()
	            .filter(p -> p.getSupporto() == sup)
	            .findFirst()
	            .orElseThrow(() -> new Exception(getMessaggio("carello_supporto_ko")));
	}


	@Override
	public void validatePassword(String password) throws Exception {
		String passwordRegex =
				 "^(?=.*[0-9])" +        // almeno un numero
				 "(?=.*[a-z])" +         // almeno un minusculo
				 "(?=.*[A-Z])" +         // almeno un maiusculo
				 "(?=.*[@#$%^&+=!?])" +  // almeno un carettere speciale
				 "(?=\\S+$)" +           // nessuno spazio
				 ".{8,}$";               // almeno 8 caratteri
		if (password == null || !password.matches(passwordRegex)) {
			throw new Exception(getMessaggio("account_no_pwd"));
		}
	}

}
