package sa.etrendz.zunni.asynctask;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityProductReview;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;

public class AsynctaskGetProductDetail extends BaseAsynctask {

	private String mProductId;
	private BeanProductDetail mProductDetailBean;

	public AsynctaskGetProductDetail(Activity activity, String productId)
	{
		super(activity);
		this.mProductId = productId;
	}

	@Override
	protected String doInBackground(String... params)
	{
		String responseModel = obtainResponseFromService();
		String checkPoint = onResponseReceived(responseModel);
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				String response = new JSONObject(responseModel).getString("Model");
				mProductDetailBean = new Gson().fromJson(response, BeanProductDetail.class);
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
			response = new GetPostSender().sendPostJSON(ZunniConstants.GET_PRODUCT_ID, jsonObject.toString());
		}
		catch(Exception exception)
		{
			return "false";
		}
		return response;
	}
	
}