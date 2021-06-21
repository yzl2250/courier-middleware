package courier.model.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UnifiedRateData {
	private String courier;
	private BigDecimal rate;
	private BigDecimal parcelRate;
	private BigDecimal documentRate;
	private BigDecimal insuranceCharge;
	private BigDecimal totalParcelRate;
	private BigDecimal totalDocumentRate;
	private String error;
		
	public UnifiedRateData(String courier, BigDecimal rate) {
		this.courier = courier;
		this.rate = rate;
	}	
	
	public UnifiedRateData() {
	}

	public String getCourier() {
		return courier;
	}
	
	public void setCourier(String courier) {
		this.courier = courier;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getParcelRate() {
		return parcelRate;
	}

	public void setParcelRate(BigDecimal parcelRate) {
		this.parcelRate = parcelRate;
	}

	public BigDecimal getDocumentRate() {
		return documentRate;
	}

	public void setDocumentRate(BigDecimal documentRate) {
		this.documentRate = documentRate;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public BigDecimal getInsuranceCharge() {
		return insuranceCharge;
	}

	public void setInsuranceCharge(BigDecimal insuranceCharge) {
		this.insuranceCharge = insuranceCharge;
	}

	public BigDecimal getTotalParcelRate() {
		return totalParcelRate;
	}

	public void setTotalParcelRate(BigDecimal totalParcelRate) {
		this.totalParcelRate = totalParcelRate;
	}

	public BigDecimal getTotalDocumentRate() {
		return totalDocumentRate;
	}

	public void setTotalDocumentRate(BigDecimal totalDocumentRate) {
		this.totalDocumentRate = totalDocumentRate;
	}

	
}
