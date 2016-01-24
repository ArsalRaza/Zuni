package sa.etrendz.zunni;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.util.Log;

import com.squareup.picasso.Picasso;

@ReportsCrashes(mailTo = "shaikh.ahmed1972@gmail.com", // my email here
mode = ReportingInteractionMode.TOAST,
resToastText = R.string.crash_toast)
public class ZunniApplication extends Application 
{
	private static Picasso mCacheManager;
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.e("App", "Created");
		
		ACRA.init(this);
		setmCacheManager(Picasso.with(getApplicationContext()));
	}
	public static Picasso getmCacheManager() {
		return mCacheManager;
	}
	public static void setmCacheManager(Picasso mCacheManager) {
		ZunniApplication.mCacheManager = mCacheManager;
	}
}
