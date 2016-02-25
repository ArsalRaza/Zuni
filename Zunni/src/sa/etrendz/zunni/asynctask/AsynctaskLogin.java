package sa.etrendz.zunni.asynctask;

import org.json.JSONObject;

import sa.etrendz.zunni.ActivityMainCategory;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.base.BaseAsynctask;
import sa.etrendz.zunni.network.GetPostSender;
import sa.etrendz.zunni.utils.ZuniUtils;
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
				JSONObject jsonObject = new JSONObject(response);
				if (jsonObject.getString("Code").equalsIgnoreCase("100"))
				{
					Editor editor = ZunniApplication.getmAppPrefEditor()/*.putString("", "")*/;
					editor.putString(ZunniConstants.USER_EMAIL_PREFS_KEY, mUserEmail);
					editor.putString(ZunniConstants.IS_USER_LOGGED_IN, "true");
					editor.commit();
					return "";
				}
				else
				{
					return "Oops...! You entered wrong credentials, please check your email and password.";
				}
			}
			catch (Exception exce)
			{
				exce.printStackTrace();
				return "Invalid response is coming from the server...!";
			}
		}
		else
		{
			return checkPoint;
		}
	}

	private String obtainResponseFromService() 
	{
		String response = "";
		
		JSONObject jsonObject = new JSONObject();
		try 
		{
			jsonObject.put("Email", mUserEmail);
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
		if (output.equalsIgnoreCase(""))
		{
			Intent intent = new Intent(mActivity, ActivityMainCategory.class);
			mActivity.startActivity(intent);
			mActivity.finish();
		}
		else
		{
			ZuniUtils.showMsgDialog(mActivity, mActivity.getString(R.string.app_name), output, null, null);
		}
	}
}