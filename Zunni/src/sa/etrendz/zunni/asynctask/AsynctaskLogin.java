package sa.etrendz.zunni.asynctask;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityMainCategory;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

public class AsynctaskLogin extends BaseAsynctask
{
	private String mUserEmail;
	private String mUserPassword;

	public AsynctaskLogin(Activity activity, String email, String password) 
	{
		super(activity);
		this.mUserEmail = email;
		this.mUserPassword = password;
	}

	@Override
	protected String doInBackground(String... params) 
	{
		String response = obtainResponseFromService();
		String checkPoint = onResponseReceived(response);
		if (checkPoint.equalsIgnoreCase(""))
		{
			try
			{
				Editor editor = ZunniApplication.getmAppPrefEditor()/*.putString("", "")*/;
				editor.putString(ZunniConstants.USER_EMAIL_PREFS_KEY, mUserEmail);
				editor.commit();
			}
			catch (Exception exce)
			{
				exce.printStackTrace();
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
		String response = "";
		
		JSONObject jsonObject = new JSONObject();
		try 
		{
			jsonObject.put("UserEmail", mUserEmail);
			jsonObject.put("Password", mUserPassword);
			
			response = new GetPostSender().sendPostJSON(ZunniConstants.LOGIN_URL, jsonObject.toString());
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
		Intent intent = new Intent(mActivity, ActivityMainCategory.class);
		mActivity.startActivity(intent);
		mActivity.finish();		
	}
}