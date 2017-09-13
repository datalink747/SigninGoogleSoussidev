package com.soussidev.kotlin.signingooglesoussidev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.soussidev.kotlin.rx_java2_lib.RxActivityResult.ActivityResult;
import com.soussidev.kotlin.rx_java2_lib.RxActivityResult.compac.RxActivityResultCompact;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import static com.soussidev.kotlin.signingooglesoussidev.Connect.mGoogleApiClient;

public class Login extends AppCompatActivity  {
    private Button deconnecter;
    private static final String TAG=Login.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private Connect cn;
    private ImageView icon_profil;
    private TextView name,email;
    private LinearLayout layout_user,layout_connect;
    private Intent signInIntent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Load init Function
        cn= new Connect(this,TAG);
        signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //Button deconnection
        deconnecter =(Button)findViewById(R.id.deconnecter);
        // icon profil
        icon_profil =(ImageView)findViewById(R.id.iconprofil);
        // name profil
        name =(TextView)findViewById(R.id.name_user);
        // emil profil
        email =(TextView)findViewById(R.id.email_user);

        layout_user = (LinearLayout)findViewById(R.id.layout_user);
        layout_connect = (LinearLayout)findViewById(R.id.layout_connect);
        // Set color for Linearlayout
        layout_user.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        layout_connect.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signIn();
                getResultActivity();
            }
        });

        deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              revokeAccess();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            cn.showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    cn.hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cn.hideProgressDialog();
    }

    //function onRxActivityResult
    private void getResultActivity()
    {

        RxActivityResultCompact.startActivityForResult(this, signInIntent, RC_SIGN_IN)
                .subscribe(new Consumer<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult result) throws Exception {
                        if (result.isOk()) {

                            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(result.getData());
                            handleSignInResult(res);
                        }
                    }
                });
    }


    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

               name.setText(acct.getDisplayName().toString());
               email.setText(acct.getEmail());
            Glide.with(this)
                 .load(acct.getPhotoUrl().toString())
                 .apply(RequestOptions.circleCropTransform())
                 .into(icon_profil);

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }



    // [START signIn]
    private void signIn() {

        setResult(RC_SIGN_IN , signInIntent); //setResult function in RxActivityResult

    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {

            findViewById(R.id.layout_user).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_connect).setVisibility(View.GONE);
        } else {
            Toast.makeText(this,getString(R.string.login_disconnect),Toast.LENGTH_SHORT).show();
            findViewById(R.id.layout_user).setVisibility(View.GONE);
            findViewById(R.id.layout_connect).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cn.getMProgressDialog() != null) {
            cn.getMProgressDialog().dismiss();
        }
    }
}
