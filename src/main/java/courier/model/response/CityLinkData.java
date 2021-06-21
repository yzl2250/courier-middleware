package courier.model.response;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class CityLinkData {
	private BigDecimal rate;
	private BigDecimal totalRate;
	private String code;
	@SerializedName("api_days")
	private Integer apiDays;
	@SerializedName("final_days")
	private Integer finalDays;
	private String dayString;
	private Integer weekendDays;
	private String message;
	
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getApiDays() {
		return apiDays;
	}
	public void setApiDays(Integer apiDays) {
		this.apiDays = apiDays;
	}
	public Integer getFinalDays() {
		return finalDays;
	}
	public void setFinalDays(Integer finalDays) {
		this.finalDays = finalDays;
	}
	public String getDayString() {
		return dayString;
	}
	public void setDayString(String dayString) {
		this.dayString = dayString;
	}
	public Integer getWeekendDays() {
		return weekendDays;
	}
	public void setWeekendDays(Integer weekendDays) {
		this.weekendDays = weekendDays;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}
	
	
}
