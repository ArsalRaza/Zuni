package sa.etrendz.zunni;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.picasso.Picasso;

@ReportsCrashes(mailTo = "shaikh.ahmed1972@gmail.com", // my email here
mode = ReportingInteractionMode.TOAST,
resToastText = R.string.crash_toast)
public class ZunniApplication extends Application 
{
	private static Picasso mCacheManager;
	private static SharedPreferences mPreferences;
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.e("App", "Created");
		
		ACRA.init(this);
		setmCacheManager(Picasso.with(getApplicationContext()));
		setmAppPreferences(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
	}
	
	public static Picasso getmCacheManager() {
		return mCacheManager;
	}
	public static void setmCacheManager(Picasso mCacheManager) {
		ZunniApplication.mCacheManager = mCacheManager;
	}

	public static SharedPreferences getmAppPreferences() {
		return mPreferences;
	}

	public static void setmAppPreferences(SharedPreferences mPreferences) {
		ZunniApplication.mPreferences = mPreferences;
	}

	public static Editor getmAppPrefEditor() {
		return mPreferences.edit();
	}
}
