package sa.etrendz.zunni.utils;

import java.util.ArrayList;
import java.util.Iterator;

import sa.etrendz.zunni.R;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;

public class ZuniUtils {

	public static void LogEvent(String logstring) 
	{
		if (ZunniConstants.isLogEnabled)
		{
			Log.e("Zuni:", logstring);
		}
	}
//	public static <T> ArrayList<T> toArrayList(final Iterator<T> iterator)
//	{
//	    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
//	                        .collect(Collectors.toCollection(ArrayList::new));
//	}
	public static boolean isNetworkAvailable(Context context)
	{
		final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
		
		if (activeNetworkInfo != null)
		{ 
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void showMsgDialog(Activity activity,
			String title, String msg, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton)
	{
		try
		{
			if (positiveButton == null)
			{
				positiveButton = new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};
			}
			
			new AlertDialog.Builder(activity).setTitle(title).setMessage(msg).setIcon(R.drawable.ic_launcher).setCancelable(false)
					.setPositiveButton(activity.getString(android.R.string.ok), positiveButton).create().show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean validateEmail(String mEmail) {
		return Patterns.EMAIL_ADDRESS.matcher(mEmail).matches();
	}
}