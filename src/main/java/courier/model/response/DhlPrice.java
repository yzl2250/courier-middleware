package courier.model.response;

import java.math.BigDecimal;

public class DhlPrice {
	private BigDecimal amountWithTax;
	private BigDecimal amount;
	private BigDecimal billingAmount;
	private BigDecimal billingAmountWithTax;
	private String billingCurrencyCode;
	private String currencyCode;
	private String tax;
	
	public BigDecimal getAmountWithTax() {
		return amountWithTax;
	}
	public void setAmountWithTax(BigDecimal amountWithTax) {
		this.amountWithTax = amountWithTax;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(BigDecimal billingAmount) {
		this.billingAmount = billingAmount;
	}
	public BigDecimal getBillingAmountWithTax() {
		return billingAmountWithTax;
	}
	public void setBillingAmountWithTax(BigDecimal billingAmountWithTax) {
		this.billingAmountWithTax = billingAmountWithTax;
	}
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	
	
}
