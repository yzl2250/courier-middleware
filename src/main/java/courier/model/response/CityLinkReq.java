package courier.model.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CityLinkReq {
	@SerializedName("data")
	private CityLinkData cityLinkData;
	private List<String> headers;
	private Integer status;
	
	public CityLinkData getCityLinkData() {
		return cityLinkData;
	}
	public void setCityLinkData(CityLinkData cityLinkData) {
		this.cityLinkData = cityLinkData;
	}
	public List<String> getHeaders() {
		return headers;
	}
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
