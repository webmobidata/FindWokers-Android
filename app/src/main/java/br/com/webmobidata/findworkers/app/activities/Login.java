package br.com.webmobidata.findworkers.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.webmobidata.findworkers.R;
import br.com.webmobidata.findworkers.app.handleViews.HideShowProgressBar;
import br.com.webmobidata.findworkers.app.tasks.AutoCompleteEmailTask;
import br.com.webmobidata.findworkers.delegate.AutoCompleteEmailTaskDelegate;
import br.com.webmobidata.findworkers.utils.FacebookUtils;

public class Login extends AppCompatActivity  implements AutoCompleteEmailTaskDelegate{

    AutoCompleteTextView mEmailView;
    EditText mPasswordView;
    TextView textLogo;
    Button mEmailSignInButton, mEmailSignUpButton;
    View mProgressView, mLoginFormView;
    LoginButton loginButtonFacebook;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        if(AccessToken.getCurrentAccessToken() != null){
            startActivity(new Intent(Login.this,Main.class));
            finish();
        }

        referenceUIs();

        new AutoCompleteEmailTask(this,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "raleway.thin.ttf");
        textLogo.setTypeface(font);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        loginButtonFacebook.setReadPermissions(FacebookUtils.getPermissionsReadFacebook());
        loginButtonFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //LoginManager.getInstance().logInWithReadPermissions(Login.this, FacebookUtils.getPermissionsReadFacebook());
                        startActivity(new Intent(Login.this, Main.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void referenceUIs() {
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        loginButtonFacebook = (LoginButton) findViewById(R.id.login_facebook);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        textLogo = (TextView) findViewById(R.id.text_logo);
    }

    public void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if(!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            HideShowProgressBar.showProgress(true, mProgressView, mLoginFormView, this);
        }

    }

    private boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void setAdapterEmail(List<String> listEmails) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, listEmails);
        mEmailView.setAdapter(adapter);
    }
}