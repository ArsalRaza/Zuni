package sa.etrendz.zunni;

import java.util.Calendar;

import sa.etrendz.zunni.asynctask.AsynctaskRegisterUser;
import sa.etrendz.zunni.utils.SystemIntents;
import sa.etrendz.zunni.utils.ZuniUtils;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class ActivitySignUp extends AppCompatActivity implements OnClickListener, OnDateSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener 
{
	private Button mSignUpButton;
	private EditText mPasswordEditText;
	private EditText mEmailEditText;
	private EditText mUserNameEditText;
	private ImageView mFullImageView;
	private Uri mImageUri;
	private String mEmail;
	private String mPassword;
	private String mUserName;
	private ImageView mTakeImageView;
	private TextView mDateOfBirthEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_registration_toolbar);
//		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backbutton_actionbar));
		setSupportActionBar(toolbar);
		
		ActionBar supportToolBar = getSupportActionBar();
		supportToolBar.setDisplayShowTitleEnabled(true);
		supportToolBar.setTitle("Registration");

		InitUI();
    }

	private void InitUI() 
	{
		mSignUpButton = (Button) findViewById(R.id.activity_registration_register);
		
		mFullImageView = (ImageView) findViewById(R.id.activity_registration_full_img);
		mTakeImageView = (ImageView) findViewById(R.id.activity_registration_take_img);
		
		mUserNameEditText = (EditText) findViewById(R.id.activity_registration_username);
		mEmailEditText = (EditText) findViewById(R.id.activity_registration_email);
		mPasswordEditText = (EditText) findViewById(R.id.activity_registration_password);
		mDateOfBirthEditText = (TextView) findViewById(R.id.activity_registration_dob);
		
		mDateOfBirthEditText.setOnClickListener(this);
		mSignUpButton.setOnClickListener(this);
		mTakeImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.activity_registration_register:
			registerUser();
			break;
		case R.id.activity_registration_dob:
		    Calendar now = Calendar.getInstance();
	        DatePickerDialog dpd = DatePickerDialog.newInstance(
	                ActivitySignUp.this,
	                now.get(Calendar.YEAR),
	                now.get(Calendar.MONTH),
	                now.get(Calendar.DAY_OF_MONTH)
	        );
	        
	        dpd.setThemeDark(false);
	        dpd.vibrate(true);
	        dpd.dismissOnPause(true);
	        dpd.showYearPickerFirst(true);
	        dpd.setAccentColor(Color.parseColor("#9C27B0"));
	        dpd.setTitle("DatePicker Title");
	        dpd.show(getFragmentManager(), "Datepickerdialog");
			break;
			
		case R.id.activity_registration_take_img:
			takeImage();
			break;		
		}
	}

	private void takeImage()
	{
		SystemIntents.pickImageIntent(ActivitySignUp.this);
	}
	
	private void registerUser() 
	{
		if (ZuniUtils.isNetworkAvailable(getApplicationContext()))
		{
			if (validateInfo())
				new AsynctaskRegisterUser(ActivitySignUp.this, mEmail, mPassword, mUserName, mImageUri).execute();
		}
		else
		{
			ZuniUtils.showMsgDialog(this, getString(R.string.app_name), getString(R.string.no_internet), null, null);
		}
		
	}
	
	private boolean validateInfo() 
	{
		mEmail = mEmailEditText.getText().toString();
		mPassword = mPasswordEditText.getText().toString();
		mUserName = mUserNameEditText.getText().toString();
		
		if (mEmail.isEmpty())
		{
			ZuniUtils.showMsgDialog(this, "", getString(R.string.error_email), null, null);
			return false;
		}

		if (ZuniUtils.validateEmail(mEmail) == false)
		{
			ZuniUtils.showMsgDialog(this, "", getString(R.string.invalid_email), null, null);
			return false;
		}

		if (mPassword.isEmpty())
		{
			ZuniUtils.showMsgDialog(this, "", getString(R.string.error_password), null, null);
			return false;
		}
		
		if (mUserName.isEmpty())
		{
			ZuniUtils.showMsgDialog(this, "", getString(R.string.error_user_name), null, null);
			return false;
		}
		
		if (mImageUri == null)
		{
			ZuniUtils.showMsgDialog(this, "", getString(R.string.error_image), null, null);
			return false;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			if (SystemIntents.SYSTEM_INTENT_GALLERY_REQUEST_CODE == requestCode)
			{
				Uri path = data.getData();
				if (path != null)
				{
					mImageUri = path;
//					ZunniApplication.getmCacheManager().load(path).fit().into(mFullImageView);
					mFullImageView.setImageURI(path);
				}
			}
		}
		else
		{
			ZuniUtils.LogEvent("Operation not selected");
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}
}