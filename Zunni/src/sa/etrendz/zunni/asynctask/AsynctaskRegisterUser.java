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
import android.net.Uri;

public class AsynctaskRegisterUser extends BaseAsynctask 
{
	private String mUserPassword;
	private String mUserEmail;
	private String mUserName;
//	private Uri UserImage;

	public AsynctaskRegisterUser(Activity activity, String mEmail,
			String mPassword, String mUserName, Uri mImageUri) 
	{
		super(activity);
		this.mUserEmail = mEmail;
		this.mUserPassword = mPassword;
		this.mUserName = mUserName;
//		this.UserImage = mImageUri;
	}


	@Override
	protected String doInBackground(String... params)
	{
		String response = obtainResponseFromService();
		String checkPoint = onResponseReceived(response);
		if (checkPoint.equalsIgnoreCase(""))
		{
			Editor editor = ZunniApplication.getmAppPrefEditor();
			
			editor.putString(ZunniConstants.USER_NAME_PREFS_KEY, mUserName);
			editor.putString(ZunniConstants.USER_EMAIL_PREFS_KEY, mUserEmail);
			editor.putString(ZunniConstants.USER_PASSWORD_PREFS_KEY, mUserPassword);
			editor.commit();
		}
		else
		{
			return checkPoint;
		}
		return "";
	}

	private String obtainResponseFromService() 
	{
		String response = null;
		JSONObject jsonObject = new JSONObject();
		try
		{
			jsonObject.put("UserEmail", mUserEmail);
			jsonObject.put("Password", mUserPassword);
			jsonObject.put("ConfirmPassword", mUserPassword);
			jsonObject.put("FirstName", mUserName);
			jsonObject.put("LastName", "");

			response = new GetPostSender().sendPostJSON(ZunniConstants.REGISTER_USER, jsonObject.toString());
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
		Intent intent = new Intent(mActivity, ActivityMainCategory.class);
		mActivity.startActivity(intent);
		mActivity.finish();
	}
}