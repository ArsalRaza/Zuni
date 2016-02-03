package sa.etrendz.zunni.asynctask;

import java.lang.reflect.Type;
import java.util.List;

import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.fragment.FragmentHomeCategory;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AsyncTaskGetAllCategories extends BaseAsynctask
{
	private List<BeanGetAllCategory> mBeanGetAllCategories;
	private Object mFromWhere;

	public AsyncTaskGetAllCategories (Activity activity, Object fromWhere)
	{
		super(activity);
		mFromWhere = fromWhere;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(String... params) 
	{
		String response = new GetPostSender().sendGet(ZunniConstants.BASE_URL + ZunniConstants.GET_ALL_CATEGORY_URL);
		String checkPoint = onResponseReceived(response);
		
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{ 
				Gson gson = new Gson();
				Type type = new TypeToken<List<BeanGetAllCategory>>(){}.getType();
				mBeanGetAllCategories = (List<BeanGetAllCategory>) gson.fromJson(response, type);
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
			if (mFromWhere instanceof FragmentHomeCategory)
			{
				((FragmentHomeCategory) mFromWhere).updateCategories(mBeanGetAllCategories);
			}
		}
		else
		{
			if (mFromWhere instanceof Fragment)
			Toast.makeText((Activity) ((Fragment) mFromWhere).getActivity(), output, Toast.LENGTH_LONG).show();
		}
	}
}