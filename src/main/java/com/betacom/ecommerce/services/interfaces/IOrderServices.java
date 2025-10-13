package com.betacom.ecommerce.services.interfaces;

import com.betacom.ecommerce.requests.OrderReq;

public interface IOrderServices {

	void create(OrderReq req) throws Exception;
}
