package courier.model.request;

import java.math.BigDecimal;
import java.sql.Date;

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
	private String originCity;
	private String destinationCountry;
	private String destinationState;
	private String destinationCity;
	private String receiverAddressType;
	private String receiverType;
	private String senderType;
	private String quantity;
	private String language;
	private String marketCountry;
	private String abTestVersion;
	private String selectedSegment;
	private boolean optionDocuments;
	private Date optionShippingDate;
	
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
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getReceiverAddressType() {
		return receiverAddressType;
	}
	public void setReceiverAddressType(String receiverAddressType) {
		this.receiverAddressType = receiverAddressType;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMarketCountry() {
		return marketCountry;
	}
	public void setMarketCountry(String marketCountry) {
		this.marketCountry = marketCountry;
	}
	public String getAbTestVersion() {
		return abTestVersion;
	}
	public void setAbTestVersion(String abTestVersion) {
		this.abTestVersion = abTestVersion;
	}
	public String getSelectedSegment() {
		return selectedSegment;
	}
	public void setSelectedSegment(String selectedSegment) {
		this.selectedSegment = selectedSegment;
	}
	public boolean isOptionDocuments() {
		return optionDocuments;
	}
	public void setOptionDocuments(boolean optionDocuments) {
		this.optionDocuments = optionDocuments;
	}
	public Date getOptionShippingDate() {
		return optionShippingDate;
	}
	public void setOptionShippingDate(Date optionShippingDate) {
		this.optionShippingDate = optionShippingDate;
	}

	
	
	
}
