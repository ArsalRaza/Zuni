package sa.etrendz.zunni.asynctask;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityProductReview;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanProductForCategory;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsynctaskGetProductDetail extends BaseAsynctask
{
	private String mProductId;
	private BeanProductDetail mProductDetailBean;

	public AsynctaskGetProductDetail(Activity activity, String productId)
	{
		super(activity);
		this.mProductId = productId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(String... params)
	{
		String responseModel = obtainResponseFromService();
		String checkPoint = onResponseReceived(responseModel);
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				JSONObject jsonObject = new JSONObject(responseModel);
				String responsePageModel = jsonObject.getString("PageDetailModel");
				String responseRelatedProductsModel = jsonObject.getString("RelatedProducts");
				mProductDetailBean = new Gson().fromJson(responsePageModel, BeanProductDetail.class);
				
				Type type = new TypeToken<List<BeanProductForCategory>>(){}.getType();
				mProductDetailBean.setmRelatedProducts((List<BeanProductForCategory>) new Gson().fromJson(responseRelatedProductsModel, type));
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
		return "";
	}

	@Override
	protected void onComplete(String output)
	{
		if (output.equalsIgnoreCase(""))
		{
			if (mActivity instanceof ActivityProductReview)
			{
				((ActivityProductReview) mActivity).onProductDetailsObtained(mProductDetailBean);
			}
		}
		else
		{
			Toast.makeText(mActivity, output, Toast.LENGTH_LONG).show();
		}
	}
	
	private String obtainResponseFromService()
	{
		JSONObject jsonObject = new JSONObject();
		String response = "";
		try
		{
			jsonObject.put("productId", mProductId);
			response = new GetPostSender().sendPostJSON(ZunniConstants.GET_PRODUCT_WITH_RELATED_PRODUCTS, jsonObject.toString());
		}
		catch(Exception exception)
		{
			return "false";
		}
		return response;
	}
	
}