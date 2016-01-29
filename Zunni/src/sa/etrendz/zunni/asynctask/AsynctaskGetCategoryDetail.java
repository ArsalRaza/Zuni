package sa.etrendz.zunni.asynctask;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityProductList;
import sa.etrendz.zunni.ActivitySubCategory;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanSubCategoryProductForAdapter;
import sa.etrendz.zunni.bean.CategoryDetailBean;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsynctaskGetCategoryDetail extends BaseAsynctask {

	private String mCategoryId;
	private CategoryDetailBean mCategoryDetailBean;
	private Object mFromWhere;

	public AsynctaskGetCategoryDetail(Activity activity, String mCategoryId)
	{
		super(activity);
		this.mCategoryId = mCategoryId;
		this.mFromWhere = activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(String... params)
	{
		String responseModel = obtainResponseFromApi();
		String checkPoint = onResponseReceived(responseModel);

		Log.e("Response:", "asd/" + responseModel);
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				String response = new JSONObject(responseModel).getString("Model");
				mCategoryDetailBean = new Gson().fromJson(response, CategoryDetailBean.class);
				
				List<BeanSubCategoryProductForAdapter> mEmptyAdapterList = new ArrayList<BeanSubCategoryProductForAdapter>();
				Gson gson = new Gson();
				Type type = new TypeToken<List<BeanGetAllCategory>>(){}.getType();
				
				List<BeanGetAllCategory> mFilledSubCategories = (List<BeanGetAllCategory>) gson.fromJson(new JSONObject(response).getString("SubCategories"), type);
				
				for (BeanGetAllCategory beanGetAllCategory : mFilledSubCategories)
				{
					BeanSubCategoryProductForAdapter beanSubCategoryProductForAdapter = new BeanSubCategoryProductForAdapter();
					beanSubCategoryProductForAdapter.setmBean(beanGetAllCategory);
					beanSubCategoryProductForAdapter.setmIsSubCategoryObject(true);
					
					mEmptyAdapterList.add(beanSubCategoryProductForAdapter);
					
				}
				
//				Type typeForProductType = new TypeToken<List<BeanProductDetail>>(){}.getType();
//				List<BeanProductDetail> mFilledProducts = (List<BeanProductDetail>) gson.fromJson(new JSONObject(response).getString("Products"), typeForProductType);
//				for (BeanProductDetail beanGetAllProductDetail : mFilledProducts)
//				{
//					BeanSubCategoryProductForAdapter beanSubCategoryProductForAdapter = new BeanSubCategoryProductForAdapter();
//					beanSubCategoryProductForAdapter.setmBean(beanGetAllProductDetail);
//					beanSubCategoryProductForAdapter.setmIsSubCategoryObject(false);
//					mEmptyAdapterList.add(beanSubCategoryProductForAdapter);
//				}

				mCategoryDetailBean.setmBeanListForAdapter(mEmptyAdapterList);
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

	private String obtainResponseFromApi() 
	{
		String response = "";
		try
		{
			String url = ZunniConstants.BASE_URL + ZunniConstants.GET_CATEGORY_DETAILS + mCategoryId;
			response = new GetPostSender().sendGet(url);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "false";
		}
		return response;
	}

	@Override
	protected void onComplete(String output) 
	{
		if (output.equalsIgnoreCase(""))
		{
			if (mFromWhere instanceof ActivitySubCategory)
			{
				((ActivitySubCategory) mFromWhere).updateCategories(mCategoryDetailBean);
			}
			else if (mFromWhere instanceof ActivityProductList) {
				((ActivityProductList) mFromWhere).updateProducts(mCategoryDetailBean.getProducts());
			}
		}
		else
		{
			if (mFromWhere instanceof Activity)
				Toast.makeText(((Activity) mFromWhere), output, Toast.LENGTH_LONG).show();
		}
	}
}