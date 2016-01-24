package sa.etrendz.zunni.bean;

public class BeanGetAllCategory 
{
	private String Name, Description, MetaKeywords, MetaDescription, MetaTitle, SeName
				, HasChild, DisplayCategoryBreadcrumb
				,Id;
	
	private PictureModel PictureModel;

	private Object CustomProperties;
	private Object PriceRanges;
	private Object PageSizeOptions;
	
	public static class PictureModel
	{
		private String ImageUrl, FullSizeImageUrl, Title, AlternateText;
		
		private Object CustomProperties;
		
		public String getThumbImageUrl() {
			return ImageUrl;
		}

		public void setThumbImageUrl(String imageUrl) {
			ImageUrl = imageUrl;
		}
	}

	public PictureModel getImageModel() {
		return PictureModel;
	}

	public void setImageModel(PictureModel pictureModel) {
		this.PictureModel = pictureModel;
	}

	public String getmCategoryName() {
		return Name;
	}

	public void setmCategoryName(String name) {
		Name = name;
	}

	public String getmCategoryId() {
		return Id;
	}

	public void setmCategoryId(String id) {
		Id = id;
	}
}