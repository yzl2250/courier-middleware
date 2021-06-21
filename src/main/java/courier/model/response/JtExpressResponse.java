package courier.model.response;

public class JtExpressResponse {
	private String parcel;
	private String document;
	private String totalParcel;
	private String totalDocument;
	private String insuranceCharge;
	
	public String getParcel() {
		return parcel;
	}
	public void setParcel(String parcel) {
		this.parcel = parcel;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getTotalParcel() {
		return totalParcel;
	}
	public void setTotalParcel(String totalParcel) {
		this.totalParcel = totalParcel;
	}
	public String getTotalDocument() {
		return totalDocument;
	}
	public void setTotalDocument(String totalDocument) {
		this.totalDocument = totalDocument;
	}
	public String getInsuranceCharge() {
		return insuranceCharge;
	}
	public void setInsuranceCharge(String insuranceCharge) {
		this.insuranceCharge = insuranceCharge;
	}
	
	
}
