package sa.etrendz.zunni;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import sa.etrendz.zunni.asynctask.AsynctaskRegisterUser;
import sa.etrendz.zunni.utils.SystemIntents;
import sa.etrendz.zunni.utils.ZuniUtils;
import sa.etrendz.zunni.view.RoundedImageView;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

 public class ActivitySignUp extends AppCompatActivity implements OnClickListener
{
	private TextView mSignUpButton;
	private EditText mPasswordEditText;
	private EditText mEmailEditText;
	private EditText mUserNameEditText;
	private RoundedImageView mFullImageView;
	private Uri mImageUri;
	private String mEmail;
	private String mPassword;
	private String mUserName;
	private CallbackManager mCallbackManager;
	private TextView mFacebookButton;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_registration_toolbar);
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backbutton_actionbar));

		mCallbackManager = CallbackManager.Factory.create();

		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		ActionBar supportToolBar = getSupportActionBar();
		supportToolBar.setDisplayShowTitleEnabled(true);
		supportToolBar.setTitle("Registration");
		try {
		    PackageInfo info = getPackageManager().getPackageInfo(
		                           getPackageName(),
		                           PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) {
		        MessageDigest md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
		    }
		}
		catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		InitUI();
	}

	private void InitUI() 
	{
		mSignUpButton = (TextView) findViewById(R.id.activity_registration_register_btn);
		mFacebookButton = (TextView) findViewById(R.id.activity_registration_facebook_btn);
		
		mFullImageView = (RoundedImageView) findViewById(R.id.activity_registration_full_img);
		
		mUserNameEditText = (EditText) findViewById(R.id.activity_registration_username);
		mEmailEditText = (EditText) findViewById(R.id.activity_registration_email);
		mPasswordEditText = (EditText) findViewById(R.id.activity_registration_password);
		
		mSignUpButton.setOnClickListener(this);
		mFacebookButton.setOnClickListener(this);
		mFullImageView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.activity_registration_register_btn:
			registerUser();
			break;
		
		case R.id.activity_registration_facebook_btn:
			registerWithFacebook();
			break;
				
		case R.id.activity_registration_full_img:
			takeImage();
			break;		
		}
	}

	private void registerWithFacebook() 
	{

//		SocialMediaSignIn.facebookSignIn(this, mCallbackManager);
		LoginManager.getInstance().registerCallback(mCallbackManager,
	            new FacebookCallback<LoginResult>() 
	            {
	                @Override
	                public void onSuccess(LoginResult loginResult) {
	                    // App code
	                	GraphRequest request = GraphRequest.newMeRequest(
	                            loginResult.getAccessToken(),
	                            new GraphRequest.GraphJSONObjectCallback() {
	                                @Override
	                                public void onCompleted(
	                                        JSONObject object,
	                                        GraphResponse response) {
	                                    // Application code
	                                	JSONObject object2 = response.getJSONObject();
	                                	ZuniUtils.LogEvent(response.toString());
	                                	try 
	                                	{
											new AsynctaskRegisterUser(ActivitySignUp.this, object2.getString("email"), object2.getString("id"), object2.getString("name"), null).execute();
										}
	                                	catch (JSONException e) 
										{
											e.printStackTrace();
										}
	                                }
	                            });

	                	Bundle parameters = new Bundle();
	                    parameters.putString("fields", "id,name,email,gender, birthday");
	                    request.setParameters(parameters);
	                    request.executeAsync();
	                
	                }

	                @Override
	                public void onCancel() {
	                     // App code
//	                	Log.e("error", exception.getLocalizedMessage());
	                }

	                @Override
	                public void onError(FacebookException exception) {
	                     // App code  
	                	Log.e("error", exception.getLocalizedMessage());
	                }
	    });
		LoginManager.getInstance().logInWithReadPermissions(ActivitySignUp.this, Arrays.asList("public_profile", "email", "user_friends"));
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
		mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
}