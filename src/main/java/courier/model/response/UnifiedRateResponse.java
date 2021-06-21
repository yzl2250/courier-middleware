package courier.model.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UnifiedRateResponse {
	@SerializedName("data")
	private List<UnifiedRateData> unifiedRateDataList;
		
	public UnifiedRateResponse(List<UnifiedRateData> unifiedRateDataList) {
		this.unifiedRateDataList = unifiedRateDataList;
	}

	public List<UnifiedRateData> getUnifiedRateDataList() {
		return unifiedRateDataList;
	}

	public void setUnifiedRateDataList(List<UnifiedRateData> unifiedRateDataList) {
		this.unifiedRateDataList = unifiedRateDataList;
	}
	
}
