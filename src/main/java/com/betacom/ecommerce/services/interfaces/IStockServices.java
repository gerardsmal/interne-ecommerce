package com.betacom.ecommerce.services.interfaces;

import com.betacom.ecommerce.requests.PickItemReq;
import com.betacom.ecommerce.requests.StockReq;

public interface IStockServices {

	void update(StockReq req) throws Exception;
	void delete(StockReq req) throws Exception;
	void pickItem(PickItemReq req) throws Exception;

}
