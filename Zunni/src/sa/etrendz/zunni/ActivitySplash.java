package sa.etrendz.zunni;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Timer;
import java.util.TimerTask;

import sa.etrendz.zunni.utils.ZunniConstants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActivitySplash extends AppCompatActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
//		CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() 
		{
			
			@Override
			public void run() 
			{
				if (ZunniApplication
						.getmAppPreferences()
						.getString(ZunniConstants.IS_USER_LOGGED_IN, "false")
					.equalsIgnoreCase("false"))
				{
					startActivity(new Intent(ActivitySplash.this, ActivityLogin.class));
				}
				else
				{
					startActivity(new Intent(ActivitySplash.this, ActivityMainCategory.class));
				}
				finish();
			}
		}, 3 * 1000); // 3 secs
	}
}
