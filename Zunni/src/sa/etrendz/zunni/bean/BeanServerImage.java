package sa.etrendz.zunni.bean;

public class BeanServerImage 
{
	private String ImageUrl, FullSizeImageUrl, Title, AlternateText;
	
	private Object CustomProperties;
	
	public String getThumbImageUrl() {
		return ImageUrl;
	}

	public void setThumbImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getmFullSizeImageUrl() {
		return FullSizeImageUrl;
	}

	public void setmFullSizeImageUrl(String fullSizeImageUrl) {
		FullSizeImageUrl = fullSizeImageUrl;
	}
}
