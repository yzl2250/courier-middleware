package courier.model.response;

import com.google.gson.annotations.SerializedName;

public class DhlOffers {
	@SerializedName("price")
	private DhlPrice dhlPrice;

	public DhlPrice getDhlPrice() {
		return dhlPrice;
	}

	public void setDhlPrice(DhlPrice dhlPrice) {
		this.dhlPrice = dhlPrice;
	}
	
	
}
