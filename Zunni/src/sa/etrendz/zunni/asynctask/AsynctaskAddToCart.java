package sa.etrendz.zunni.asynctask;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityProductReview;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZuniUtils;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;

public class AsynctaskAddToCart extends BaseAsynctask
{
	private String mProductId;
	private String mCartType;
	private String mProductQuantity;
	private String mProductPrice;

	public AsynctaskAddToCart(Activity activity, String mProductId, String mCartType,
			String mProductQuantity, String mProductPrice)
	{
		super(activity);
		
		this.mProductId = mProductId;
		this.mCartType = mCartType;
		this.mProductQuantity = mProductQuantity;
		this.mProductPrice = mProductPrice;
	}

	@Override
	protected void onComplete(String output)
	{
		if (output.equalsIgnoreCase(""))
		{
			if (mActivity instanceof ActivityProductReview)
				((ActivityProductReview) mActivity).onAddToCartComplete();
		}
		else
		{
			ZuniUtils.showMsgDialog(mActivity, "", output, null, null);
		}
	}

	@Override
	protected String doInBackground(String... params)
	{
		String response = getResponseFromService();
		String checkPoint = onResponseReceived(response);
		
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				return "Please add after response...!";
			}
			catch (Exception exce)
			{
				exce.printStackTrace();
				return "Invalid response is coming from the server";
			}
		}
		else
		{
			return checkPoint;
		}
//		return "";
	}

	private String getResponseFromService()
	{
		String response = "";
		try
		{
			JSONObject jsonObject = new JSONObject();
		
			jsonObject.put("productId", mProductId);
			jsonObject.put("shoppingCartTypeId", mCartType);
			jsonObject.put("sQuantity", mProductQuantity);
			if (mProductPrice != null)
			{
				jsonObject.put("sPrice", mProductPrice);
				jsonObject.put("sUpdatedItemId", "");
			}
			response = new GetPostSender().sendPostJSON(ZunniConstants.BASE_URL + ZunniConstants.ADD_TO_CART, jsonObject.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "false";
		}
		return response;
	}
}