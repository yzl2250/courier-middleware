package courier.model.request;

import java.math.BigDecimal;

public class Request {
	private String fromPostalCode;
	private String toPostalCode;
	private String goodsType;
	private BigDecimal weight;
	private BigDecimal length;
	private BigDecimal width;
	private BigDecimal height;
	private boolean insured;
	private BigDecimal insuranceValue;
	private String originCountry;
	private String originState;
	private String destinationCountry;
	private String destinationState;
	
	public String getFromPostalCode() {
		return fromPostalCode;
	}
	public void setFromPostalCode(String fromPostalCode) {
		this.fromPostalCode = fromPostalCode;
	}
	public String getToPostalCode() {
		return toPostalCode;
	}
	public void setToPostalCode(String toPostalCode) {
		this.toPostalCode = toPostalCode;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public BigDecimal getWidth() {
		return width;
	}
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public boolean isInsured() {
		return insured;
	}
	public void setInsured(boolean insured) {
		this.insured = insured;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	
	
	
}
