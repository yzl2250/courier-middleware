package courier.model.response;

import com.google.gson.annotations.SerializedName;

public class CityLinkExpressResponse {
	@SerializedName("req")
	private CityLinkReq cityLinkReq;
	private String error;

	public CityLinkReq getCityLinkReq() {
		return cityLinkReq;
	}

	public void setCityLinkReq(CityLinkReq cityLinkReq) {
		this.cityLinkReq = cityLinkReq;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

}
