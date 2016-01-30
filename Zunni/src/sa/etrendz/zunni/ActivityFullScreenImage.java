package sa.etrendz.zunni;

import com.squareup.picasso.Callback;

import sa.etrendz.zunni.utils.ZunniConstants;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ActivityFullScreenImage extends AppCompatActivity implements OnClickListener, Callback
{
	private String mImageUrl;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_screen_image);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backbutton_actionbar));

		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		ActionBar supportToolBar = getSupportActionBar();
		supportToolBar.setDisplayShowTitleEnabled(true);
		supportToolBar.setTitle(ZunniApplication.getmAppPreferences().getString(ZunniConstants.SELECTED_CATEGORY_NAME, ""));
	
		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("image-url"))
		{
			mImageUrl = getIntent().getStringExtra("image-url");
		}
		else
		{
			finish();
		}
		ImageView imageView = (ImageView) findViewById(R.id.activity_full_image);
		mProgressBar = (ProgressBar) findViewById(R.id.activity_full_progress_bar);
		ZunniApplication.getmCacheManager().load(Uri.parse(mImageUrl)).into(imageView, this);
	}
	
	@Override
	public void onClick(View v) 
	{
		
	}

	@Override
	public void onError() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onSuccess() {
		mProgressBar.setVisibility(View.GONE);
	}
	
}
