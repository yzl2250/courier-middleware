package courier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import courier.model.request.Request;
import courier.model.response.UnifiedRateResponse;
import courier.service.UnifiedRateService;

@RestController
public class CourierRateController {
	
	@Autowired
	UnifiedRateService unifiedRateService;
	
	@PostMapping("/GetCourierRate")
	public UnifiedRateResponse unifiedRateResponse(@RequestBody Request request) {
		UnifiedRateResponse unifiedRateResponse = unifiedRateService.getUnifiedRate(request);		
		return unifiedRateResponse;
	}
	

}
