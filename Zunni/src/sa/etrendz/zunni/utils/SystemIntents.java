package sa.etrendz.zunni.utils;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

public class SystemIntents 
{
	public static final int SYSTEM_INTENT_GALLERY_REQUEST_CODE = 1122;

	public static void pickImageIntent(Activity fragment)
	{
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		fragment.startActivityForResult(galleryIntent, SYSTEM_INTENT_GALLERY_REQUEST_CODE);
	}

	public static void makeCall(Activity mActivity, String snippet)
	{
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + snippet));
		mActivity.startActivity(intent);
	}
	
	public static void settingLocations(Activity activity, Integer requestCode)
	{
		Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		if (requestCode != null)
			activity.startActivityForResult(gpsOptionsIntent, requestCode);
		else
			activity.startActivity(gpsOptionsIntent);
	}
	
	public static void whatsAppSharing(Activity activity, String text)
	{
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("text/plain");

		System.out.println(" == text == " + text);
		sendIntent.putExtra(Intent.EXTRA_TEXT, text);
	    
		/** Event for post share on whatsapp */
		PackageManager pm = activity.getApplicationContext().getPackageManager();
		final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent, 0);
		boolean temWhatsApp = false;
		String packageName = "com.whatsapp";
		for (final ResolveInfo info : matches)
		{
			if (info.activityInfo.packageName.startsWith(packageName))
			{
				final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);

				sendIntent.setPackage(info.activityInfo.packageName);
				sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
				sendIntent.setComponent(name);
				temWhatsApp = true;
				break;
			}
		}
		if (temWhatsApp)
		{
			activity.startActivity(sendIntent);
		}
		else
		{
			Toast.makeText(activity, "Your device doesn't have whatsApp.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void sendMsg (Activity activity, String text)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("sms:"));
		intent.putExtra("sms_body", text);
		activity.startActivity(intent);
	}
}