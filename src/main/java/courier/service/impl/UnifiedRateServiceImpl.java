package courier.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import courier.HttpClientConnection;
import courier.constants.CourierConst;
import courier.model.request.Request;
import courier.model.response.CityLinkExpressResponse;
import courier.model.response.DhlResponse;
import courier.model.response.JtExpressResponse;
import courier.model.response.UnifiedRateData;
import courier.model.response.UnifiedRateResponse;
import courier.service.UnifiedRateService;

@Service
public class UnifiedRateServiceImpl implements UnifiedRateService {
	
	Gson gson;
		
	public UnifiedRateServiceImpl() {
		gson = new Gson();
	}

	public UnifiedRateResponse getUnifiedRate(Request request) {
		UnifiedRateResponse unifiedDateResponse = new UnifiedRateResponse(new ArrayList<>());	
		
//		addJtExpressRate(request, unifiedDateResponse, new ArrayList<>());     API changed, needs to update the code
		addCityLinkExpressRate(request, unifiedDateResponse, new ArrayList<>());
		addDhlRate(request, unifiedDateResponse, new ArrayList<>());

		return unifiedDateResponse;
	}
	
	public void addJtExpressRate(Request request, UnifiedRateResponse unifiedDateResponse, List<String> errorMsgList) {		
		UnifiedRateData unifiedRateData = new UnifiedRateData();
		Boolean isSuccess = jtExpressDataValidation(request, errorMsgList);
		
		if (!isSuccess) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1));
		else {
			JtExpressResponse jtExpressResponse = getJtExpressRate(request, errorMsgList);
			if (jtExpressResponse == null) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1));
			else {
				if (request.getGoodsType().equalsIgnoreCase(CourierConst.GOODS_TYPE_DOCUMENT)) {
					unifiedRateData.setDocumentRate(new BigDecimal(jtExpressResponse.getDocument()));
					unifiedRateData.setTotalDocumentRate(new BigDecimal(jtExpressResponse.getTotalDocument()));					
					unifiedRateData.setInsuranceCharge(!Strings.isNullOrEmpty(jtExpressResponse.getInsuranceCharge()) ? (new BigDecimal(jtExpressResponse.getInsuranceCharge())) : null); 
				}
				else if (request.getGoodsType().equalsIgnoreCase(CourierConst.GOODS_TYPE_PARCEL)) {
					unifiedRateData.setParcelRate(new BigDecimal(jtExpressResponse.getParcel()));
					unifiedRateData.setTotalParcelRate(new BigDecimal(jtExpressResponse.getTotalParcel()));
					unifiedRateData.setInsuranceCharge(!Strings.isNullOrEmpty(jtExpressResponse.getInsuranceCharge()) ? (new BigDecimal(jtExpressResponse.getInsuranceCharge())) : null);
				}
				else if (request.getGoodsType().equalsIgnoreCase(CourierConst.GOODS_TYPE_ALL)) {
					unifiedRateData.setDocumentRate(new BigDecimal(jtExpressResponse.getDocument()));
					unifiedRateData.setTotalDocumentRate(new BigDecimal(jtExpressResponse.getTotalDocument()));
					unifiedRateData.setParcelRate(new BigDecimal(jtExpressResponse.getParcel()));
					unifiedRateData.setTotalParcelRate(new BigDecimal(jtExpressResponse.getTotalParcel()));
					unifiedRateData.setInsuranceCharge(!Strings.isNullOrEmpty(jtExpressResponse.getInsuranceCharge()) ? (new BigDecimal(jtExpressResponse.getInsuranceCharge())) : null);
				}						
			}
		}
		unifiedRateData.setCourier(CourierConst.COURIER_JT_EXPRESS);
		unifiedDateResponse.getUnifiedRateDataList().add(unifiedRateData);
	}
	
	public Boolean jtExpressDataValidation(Request request, List<String> errorMsgList) {
		Boolean isSuccess = false;		
				
		if (Strings.isNullOrEmpty(request.getFromPostalCode())) errorMsgList.add("From Postal Code cannot be empty");
		else if (Strings.isNullOrEmpty(request.getToPostalCode())) errorMsgList.add("To Postal Code cannot be empty");
		else if (request.getWeight() == null) errorMsgList.add("Weight cannot be empty");
		else if (!request.isInsured() && request.getInsuranceValue() != null && request.getInsuranceValue().compareTo(BigDecimal.ZERO) != 0) errorMsgList.add("Please set insured flag to true in order to use insurance value");
		else if (request.isInsured() && request.getInsuranceValue() == null) errorMsgList.add("Insurance value is missing");
		else {
			if (request.getWeight().compareTo(new BigDecimal(70)) == 1 || request.getWeight().compareTo(BigDecimal.ZERO) == -1) errorMsgList.add("Weight must be between 0-70.");
			else if (request.getWidth() != null && (request.getWidth().compareTo(new BigDecimal(150)) == 1 || request.getWidth().compareTo(BigDecimal.ZERO) == -1)) errorMsgList.add("Width must be between 0-150.");
			else if (request.getHeight() != null && request.getHeight().compareTo(new BigDecimal(150)) == 1 || request.getHeight().compareTo(BigDecimal.ZERO) == -1) errorMsgList.add("Height must be between 0-150.");
			else if (request.getLength() != null && request.getLength().compareTo(new BigDecimal(150)) == 1 || request.getLength().compareTo(BigDecimal.ZERO) == -1) errorMsgList.add("Length must be between 0-150.");
			else {
				if (request.getWidth() == null || request.getHeight() == null || request.getLength() == null) isSuccess = true; // Weight will be used since 1 of the dimension value is missing
				else {
					BigDecimal totalDimension = request.getWidth().add(request.getHeight()).add(request.getLength());
					if (totalDimension.compareTo(new BigDecimal(300)) == 1) {						
						errorMsgList.add("Total dimension must not surpass 300cm.");
						isSuccess = false;
					} else {
						BigDecimal dimensionWeight = (request.getWidth().multiply(request.getHeight()).multiply(request.getLength())).divide(new BigDecimal(6000), 2, RoundingMode.HALF_UP);
						if (dimensionWeight.compareTo(request.getWeight()) == 1) request.setWeight(dimensionWeight);
						isSuccess = true;
					}
				}
			}	
		}		
		return isSuccess;
	}

	public JtExpressResponse getJtExpressRate(Request request, List<String> errorMsgList) {		
		JtExpressResponse jtExpressResponse = null;
		
		HttpClientConnection httpClientConnection = new HttpClientConnection();
		String url = "https://www.jtexpress.my/function/order.php";
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    builder.addTextBody("action", "checkPrice");
	    builder.addTextBody("s_post", request.getFromPostalCode());
	    builder.addTextBody("r_post", request.getToPostalCode());
	    builder.addTextBody("weight", request.getWeight().toString());
			
	    String responseStr = httpClientConnection.sendPostWithFormData(builder, url, errorMsgList);	    	    
	    if (!responseStr.isEmpty()) {
	    	jtExpressResponse = gson.fromJson(responseStr, JtExpressResponse.class);
	    	calculateJtExpressFinalRate(request, jtExpressResponse, errorMsgList);
	    }			
		return jtExpressResponse;
	}
	
	public void calculateJtExpressFinalRate(Request request, JtExpressResponse jtExpressResponse, List<String> errorMsgList) {
		
		BigDecimal parcelRate = new BigDecimal(jtExpressResponse.getParcel());
		BigDecimal documentRate = new BigDecimal(jtExpressResponse.getDocument());
		BigDecimal totalParcelRate = parcelRate.multiply(BigDecimal.valueOf(1.06));
		BigDecimal totalDocumentRate= documentRate.multiply(BigDecimal.valueOf(1.06));		
		
		if (request.isInsured() && request.getInsuranceValue() != null) {
			BigDecimal insuranceValue = request.getInsuranceValue().setScale(2, RoundingMode.HALF_UP);
			
			if (insuranceValue.compareTo(new BigDecimal(10000)) == 1) insuranceValue = new BigDecimal(10000);
			insuranceValue = insuranceValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
			
			String lastDigit = insuranceValue.toString().substring(insuranceValue.toString().length()-1);
			if (lastDigit.equalsIgnoreCase("0") || lastDigit.equalsIgnoreCase("1") || lastDigit.equalsIgnoreCase("2")) lastDigit = "0";
			else if (lastDigit.equalsIgnoreCase("3") || lastDigit.equalsIgnoreCase("4") || lastDigit.equalsIgnoreCase("5")
					|| lastDigit.equalsIgnoreCase("6") || lastDigit.equalsIgnoreCase("7")) lastDigit = "5";
			else lastDigit = "9";
			
			String insuranceValueStr = insuranceValue.toString().substring(0, insuranceValue.toString().length()-1);
			insuranceValueStr = insuranceValueStr + lastDigit;
			insuranceValue = new BigDecimal(insuranceValueStr);
			
			if (totalParcelRate.compareTo(BigDecimal.ZERO) == 1) {
				totalParcelRate = totalParcelRate.add(insuranceValue);
				jtExpressResponse.setInsuranceCharge(insuranceValue.toString());
			}
			if (totalDocumentRate.compareTo(BigDecimal.ZERO) == 1) {
				totalDocumentRate = totalDocumentRate.add(insuranceValue);
				jtExpressResponse.setInsuranceCharge(insuranceValue.toString());
			}
		}
		
		jtExpressResponse.setTotalParcel(totalParcelRate.toString());
		jtExpressResponse.setTotalDocument(totalDocumentRate.toString());
	}
	
	public void addCityLinkExpressRate(Request request, UnifiedRateResponse unifiedDateResponse, List<String> errorMsgList) {
		UnifiedRateData unifiedRateData = new UnifiedRateData();
		Boolean isSuccess = cityLinkExpressDataValidation(request, errorMsgList);
		if (!isSuccess) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1)); 
		else {
			CityLinkExpressResponse cityLinkExpressResponse = getCityLinkExpressRate(request, errorMsgList);
			if (cityLinkExpressResponse == null) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1));
			else if (!Strings.isNullOrEmpty(cityLinkExpressResponse.getError())) unifiedRateData.setError(cityLinkExpressResponse.getError());
			else if (cityLinkExpressResponse.getCityLinkReq().getStatus() == 200 
					&& !cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getMessage().isEmpty()) 
				unifiedRateData.setError(cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getMessage()); 
			else {								
				unifiedRateData.setRate(cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getRate());
			}
		}
		unifiedRateData.setCourier(CourierConst.COURIER_CITYLINK_EXPRESS);
		unifiedDateResponse.getUnifiedRateDataList().add(unifiedRateData);
	}
	
	public Boolean cityLinkExpressDataValidation(Request request, List<String> errorMsgList) {
		Boolean isSuccess = false;
		
		if (Strings.isNullOrEmpty(request.getOriginCountry())) errorMsgList.add("Origin Country cannot be empty");
		else if (Strings.isNullOrEmpty(request.getDestinationCountry())) errorMsgList.add("Destination Country cannot be empty");
		else if (Strings.isNullOrEmpty(request.getOriginState())) errorMsgList.add("Origin State cannot be empty");
		else if (Strings.isNullOrEmpty(request.getDestinationState())) errorMsgList.add("Destination State cannot be empty");
		else if (Strings.isNullOrEmpty(request.getFromPostalCode())) errorMsgList.add("From Postal Code cannot be empty");
		else if (Strings.isNullOrEmpty(request.getToPostalCode())) errorMsgList.add("To Postal Code cannot be empty");
		else if (request.getLength() == null) errorMsgList.add("Length cannot be empty");
		else if (request.getWidth() == null) errorMsgList.add("Width cannot be empty");
		else if (request.getHeight() == null) errorMsgList.add("Height cannot be empty");
		else if (request.getWeight() == null) errorMsgList.add("Weight cannot be empty");
		else if (Strings.isNullOrEmpty(request.getGoodsType())) errorMsgList.add("Goods Type cannot be empty");
		else if (!request.getGoodsType().equalsIgnoreCase("parcel") && !request.getGoodsType().equalsIgnoreCase("document")) errorMsgList.add("Goods type must be \"parcel\" or \"document\" only");
		else {
			BigDecimal dimensionWeight = (request.getWidth().multiply(request.getHeight()).multiply(request.getLength())).divide(new BigDecimal(5000), 2, RoundingMode.HALF_UP);
			if (dimensionWeight.compareTo(request.getWeight()) == 1) request.setWeight(dimensionWeight);
			isSuccess = true;					
		}	
		
		return isSuccess;
	}

	public CityLinkExpressResponse getCityLinkExpressRate(Request request, List<String> errorMsgList) {
		CityLinkExpressResponse cityLinkExpressResponse = null;
    	
		HttpClientConnection httpClientConnection = new HttpClientConnection();
		String url = "https://www.citylinkexpress.com/wp-json/wp/v2/getShippingRate";		

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("origin_country", request.getOriginCountry());
        builder.addTextBody("origin_state", request.getOriginState());
        builder.addTextBody("origin_postcode", request.getFromPostalCode());
        builder.addTextBody("destination_country", request.getDestinationCountry());
        builder.addTextBody("destination_state", request.getDestinationState());
        builder.addTextBody("destination_postcode", request.getToPostalCode());
        builder.addTextBody("length", request.getLength().toString());
        builder.addTextBody("width", request.getWidth().toString());
        builder.addTextBody("height", request.getHeight().toString());        
        if (request.getGoodsType().equalsIgnoreCase(CourierConst.GOODS_TYPE_PARCEL)) {
        	builder.addTextBody("selected_type", "1");  // type 1 = parcel, type 2 = document
        	builder.addTextBody("parcel_weight", request.getWeight().toString());
        } else if (request.getGoodsType().equalsIgnoreCase(CourierConst.GOODS_TYPE_DOCUMENT)) {
        	builder.addTextBody("selected_type", "2");  // type 1 = parcel, type 2 = document
        	builder.addTextBody("document_weight", request.getWeight().toString());
        }

	    String responseStr = httpClientConnection.sendPostWithFormData(builder, url, errorMsgList);	    	    
	    if (!responseStr.isEmpty()) {
	    	cityLinkExpressResponse = gson.fromJson(responseStr, CityLinkExpressResponse.class);
	    }			
		return cityLinkExpressResponse;
	}
	
//	public void calculateCityLinkExpressFinalRate(Request request, CityLinkExpressResponse cityLinkExpressResponse, List<String> errorMsgList) {
//		BigDecimal rate = cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getRate();
//		System.out.println(rate);
//		BigDecimal afterSurchargeRate = rate.multiply(BigDecimal.valueOf(1.36)).setScale(2, RoundingMode.HALF_UP);  // 18% fuel + 18% Handling
//		BigDecimal totalRate = afterSurchargeRate.multiply(BigDecimal.valueOf(1.06)).setScale(2, RoundingMode.HALF_UP); // 6% service tax
//		
//		cityLinkExpressResponse.getCityLinkReq().getCityLinkData().setRate(totalRate);
//	}	
	
	public void addDhlRate(Request request, UnifiedRateResponse unifiedDateResponse, List<String> errorMsgList) {
		UnifiedRateData unifiedRateData = new UnifiedRateData();
		Boolean isSuccess = dhlDataValidation(request, errorMsgList);
		if (!isSuccess) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1)); 
		else {
			DhlResponse dhlResponse = getDhlRate(request, errorMsgList);
			if (dhlResponse == null) unifiedRateData.setError(errorMsgList.get(errorMsgList.size()-1));
//			else if (!Strings.isNullOrEmpty(dhlResponse.getError())) unifiedRateData.setError(dhlResponse.getError());
//			else if (cityLinkExpressResponse.getCityLinkReq().getStatus() == 200 
//					&& !cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getMessage().isEmpty()) 
//				unifiedRateData.setError(cityLinkExpressResponse.getCityLinkReq().getCityLinkData().getMessage()); 
			else {								
				unifiedRateData.setRate(dhlResponse.getDhlOffers().get(0).getDhlPrice().getBillingAmountWithTax());
			}
		}
		unifiedRateData.setCourier(CourierConst.COURIER_DHL);
		unifiedDateResponse.getUnifiedRateDataList().add(unifiedRateData);
	}
	
	public Boolean dhlDataValidation(Request request, List<String> errorMsgList) {
		Boolean isSuccess = false;
		
		if (Strings.isNullOrEmpty(request.getOriginCountry())) errorMsgList.add("Origin Country cannot be empty");
		else if (Strings.isNullOrEmpty(request.getDestinationCountry())) errorMsgList.add("Destination Country cannot be empty");
		else if (Strings.isNullOrEmpty(request.getOriginCity())) errorMsgList.add("Origin City cannot be empty");
		else if (Strings.isNullOrEmpty(request.getDestinationCity())) errorMsgList.add("Destination City cannot be empty");
		else if (Strings.isNullOrEmpty(request.getFromPostalCode())) errorMsgList.add("From Postal Code cannot be empty");
		else if (Strings.isNullOrEmpty(request.getToPostalCode())) errorMsgList.add("To Postal Code cannot be empty");
//		else if (Strings.isNullOrEmpty(request.getReceiverAddressType())) errorMsgList.add("Receiver Address Type cannot be empty, please fill in CONSUMER or BUSINESS");
//		else if (Strings.isNullOrEmpty(request.getReceiverType())) errorMsgList.add("Receiver Type cannot be empty, please fill in CONSUMER or BUSINESS");
//		else if (Strings.isNullOrEmpty(request.getToPostalCode())) errorMsgList.add("Sender Type cannot be empty, please fill in CONSUMER or BUSINESS");
//		else if (Strings.isNullOrEmpty(request.getLanguage())) errorMsgList.add("Language cannot be empty, please fill in EN, CN, etc");
//		else if (Strings.isNullOrEmpty(request.getMarketCountry())) errorMsgList.add("Market Country cannot be empty, please fill in country code i.g MY, SG, ID, etc");
//		else if (Strings.isNullOrEmpty(request.isOptionDocuments())) errorMsgList.add("Please enter true/false to incidate document option");
		else if (request.getOptionShippingDate() == null) errorMsgList.add("Shipping Date cannot be empty");
		else if (request.getLength() == null) errorMsgList.add("Length cannot be empty");
		else if (request.getWidth() == null) errorMsgList.add("Width cannot be empty");
		else if (request.getHeight() == null) errorMsgList.add("Height cannot be empty");
		else if (request.getWeight() == null) errorMsgList.add("Weight cannot be empty");
		else if (request.getQuantity() == null) errorMsgList.add("Quantity cannot be empty");
		else if (Strings.isNullOrEmpty(request.getGoodsType())) errorMsgList.add("Goods Type cannot be empty");
		else if (!request.getGoodsType().equalsIgnoreCase("parcel") && !request.getGoodsType().equalsIgnoreCase("document")) errorMsgList.add("Goods type must be \"parcel\" or \"document\" only");
		else {
			BigDecimal dimensionWeight = (request.getWidth().multiply(request.getHeight()).multiply(request.getLength())).divide(new BigDecimal(5000), 2, RoundingMode.HALF_UP);
			if (dimensionWeight.compareTo(request.getWeight()) == 1) request.setWeight(dimensionWeight);
			isSuccess = true;					
		}	
		
		return isSuccess;
	}	
	
	public DhlResponse getDhlRate(Request request, List<String> errorMsgList) {
		DhlResponse dhlResponse = null;	
		
		HttpClientConnection httpClientConnection = new HttpClientConnection();
		String url = "https://cj-gaq.dhl.com/api/quote?";
		
		try {
	    	URIBuilder builder = new URIBuilder(url);
	    	builder.setParameter("destinationCountry", request.getDestinationCountry())
	    	.setParameter("destinationCity", request.getDestinationCity())
	    	.setParameter("destinationZip", request.getToPostalCode())
	    	.setParameter("originCountry", request.getOriginCountry())
	    	.setParameter("originCity", request.getOriginCity())
	    	.setParameter("originZip", request.getFromPostalCode())
//	    	.setParameter("receiverAddressType", request.getReceiverAddressType())
//	    	.setParameter("receiverType", request.getReceiverAddressType())
//	    	.setParameter("senderType", request.getSenderType())
	    	.setParameter("items(0).weight", request.getWeight().toString())
	    	.setParameter("items(0).height", request.getHeight().toString())
	    	.setParameter("items(0).length", request.getLength().toString())
	    	.setParameter("items(0).width", request.getWidth().toString())
	    	.setParameter("items(0).quantity", request.getQuantity())
//	    	.setParameter("language", request.getLanguage())
//	    	.setParameter("marketCountry", request.getMarketCountry())
//	    	.setParameter("abTestVersion", request.getAbTestVersion())
//	    	.setParameter("marketCountry", request.getSelectedSegment())
	    	.setParameter("option.DOCUMENTS", String.valueOf(request.isOptionDocuments()))
	    	.setParameter("option.SHIPPING_DATE", request.getOptionShippingDate().toString());
    	    
		    String responseStr = httpClientConnection.sendGetWithParams(builder, errorMsgList);	    	    
		    if (!responseStr.isEmpty()) {
		    	dhlResponse = gson.fromJson(responseStr, DhlResponse.class);
		    }	
	    			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		
		return dhlResponse;
	}
	
	
}
