package sa.etrendz.zunni.base;

import sa.etrendz.zunni.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class BaseAsynctask extends
		AsyncTask<String, Void, String>
{
	protected Activity mActivity;
	private ProgressDialog mDialog;
	
	protected abstract void onComplete(String output);
	
	public BaseAsynctask (Activity activity)
	{
		this.mActivity = activity;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		mDialog = ProgressDialog.show(mActivity, "", mActivity.getString(R.string.progress_msg));
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);
		if (mDialog != null && mDialog.isShowing())
		{
			mDialog.dismiss();
		}
		onComplete(result);
	}
	
	public String onResponseReceived(String response)
	{
		if (response == null)
		{
			return "No response received..!"; 
		}
		
		if (response.equalsIgnoreCase("false"))
		{
			return "Connection timed out..!"; 
		}
		return "";
	}
}