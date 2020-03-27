package com.joe.accountingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.joe.accountingapp.R;
import com.joe.accountingapp.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLogin();
            }
        });

        initView();
        initGoogleLogin();
    }

    private void GoToLogin() {
        startActivity(new Intent(this, HomeActivity.class));
    }


    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 99;

    private void initGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mainBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.sign_in_button:
                        singIn();
                        break;
                }
            }
        });

    }

    private void singIn() {
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        //TODO:

    }

    private void updateUI(GoogleSignInAccount account) {
        if(account != null)
        {
            //TODO
            //google 已登入
            Toast.makeText(this, "已登入google", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //TODO:
            //未登入
            Toast.makeText(this, "未登入google", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if(!isLoggedIn)
        {
            mainBinding.loginButton.setReadPermissions("email");

            // Callback registration
            mainBinding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Toast.makeText(MainActivity.this, "成功登入", Toast.LENGTH_SHORT).show();

                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
                }

                @Override
                public void onCancel() {
                    // App code
                    Toast.makeText(MainActivity.this, "登入已取消", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Toast.makeText(MainActivity.this, "登入錯誤，請稍後再試" + exception, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}