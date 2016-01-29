package sa.etrendz.zunni.asynctask;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import sa.etrendz.zunni.ActivityProductReview;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.widget.Toast;

public class AsynctaskGetRelatedProducts extends
		BaseAsynctask
{
	private String mProductId;
	private List<BeanProductDetail> mBeanRelatedProducts;

	public AsynctaskGetRelatedProducts(Activity activity, String productId)
	{
		super(activity);
		mActivity = activity;
		mProductId = productId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(String... params) 
	{
		String response = obtainResponseFromService();
		String checkPoint = onResponseReceived(response);
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				Gson gson = new Gson();
				Type type = new TypeToken<List<BeanProductDetail>>(){}.getType();
				mBeanRelatedProducts = (List<BeanProductDetail>) gson.fromJson(response, type);
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

	private String obtainResponseFromService()
	{
		JSONObject jsonObject = new JSONObject();
		String response = "";
		try
		{
			jsonObject.put("productId", mProductId);
			response = new GetPostSender().sendPostJSON(ZunniConstants.GET_RELATED_PRODUCTS, jsonObject.toString());
		}
		catch(Exception exception)
		{
			return "false";
		}
		return response;
	}

	@Override
	protected void onComplete(String output) 
	{
		if (output.equalsIgnoreCase(""))
		{
			if (mActivity instanceof ActivityProductReview)
			{
				((ActivityProductReview) mActivity).updateRelatedProducts(mBeanRelatedProducts);
			}
		}
		else
		{
			Toast.makeText(mActivity, output, Toast.LENGTH_LONG).show();
		}
	}
}