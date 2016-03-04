package sa.etrendz.zunni.bean;

import java.util.ArrayList;

public class BeanPostAttributes
{
	private String ProductAttributeId, IsRequired, AttributeControlType;
	private ArrayList<BeanProductAttributeValues> Values;
	
	public static class BeanProductAttributeValues
	{
		private String Id, ColorSquaresRgb, IsPreSelected, Name;
		private BeanServerImage PictureModel;
		
		public String getColorSquaresRgb()
		{
			return ColorSquaresRgb;
		}
		
		public void setColorSquaresRgb(String colorSquaresRgb)
		{
			ColorSquaresRgb = colorSquaresRgb;
		}
		
		public BeanServerImage getPictureModel()
		{
			return PictureModel;
		}
		
		public void setPictureModel(BeanServerImage pictureModel)
		{
			PictureModel = pictureModel;
		}
	}

	public String getAttributeControlType() {
		return AttributeControlType;
	}

	public void setAttributeControlType(String attributeControlType) {
		AttributeControlType = attributeControlType;
	}

	public ArrayList<BeanProductAttributeValues> getAttributeValues() {
		return Values;
	}

	public void setAttributeValues(ArrayList<BeanProductAttributeValues> values) {
		Values = values;
	}
}