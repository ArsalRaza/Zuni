package sa.etrendz.zunni;

import sa.etrendz.zunni.asynctask.AsynctaskLogin;
import sa.etrendz.zunni.utils.ZuniUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ActivityLogin extends AppCompatActivity implements OnClickListener 
{
	private EditText mPasswordEditText;
	private EditText mEmailEditText;
	private String mEmail;
	private String mPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InitUI();
	}

	private void InitUI() 
	{
		ImageView mRegisterImageView = (ImageView) findViewById(R.id.activity_login_join_us);
		Button mLoginButton = (Button) findViewById(R.id.activity_login_button);
		mPasswordEditText = (EditText) findViewById(R.id.activity_login_password_edittext);
		mEmailEditText = (EditText) findViewById(R.id.activity_login_email_edittext);
	
		mLoginButton.setOnClickListener(this);
		mRegisterImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.activity_login_join_us:
			startActivity(new Intent(ActivityLogin.this, ActivitySignUp.class));
			break;

		case R.id.activity_login_button:
			if (validateInfo())
				new AsynctaskLogin(this, mEmail, mPassword).execute();
			break;
		}
	}

	private boolean validateInfo()
	{
		mEmail = mEmailEditText.getText().toString();
		mPassword = mPasswordEditText.getText().toString();
		
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
		return true;
	}
}