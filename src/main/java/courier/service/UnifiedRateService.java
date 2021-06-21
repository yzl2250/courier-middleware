package courier.service;

import courier.model.request.Request;
import courier.model.response.UnifiedRateResponse;

public interface UnifiedRateService {
	
	public UnifiedRateResponse getUnifiedRate(Request request);	
	
//	public JtExpressResponse getJtExpressRate();
//	
//	public CityLinkExpressResponse getCityLinkRate();
}
