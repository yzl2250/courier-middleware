package courier.model.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DhlResponse {
	@SerializedName("offers")
	private List<DhlOffers> dhlOffers;

	public List<DhlOffers> getDhlOffers() {
		return dhlOffers;
	}

	public void setDhlOffers(List<DhlOffers> dhlOffers) {
		this.dhlOffers = dhlOffers;
	}
	
	
}
