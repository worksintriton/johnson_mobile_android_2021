package com.triton.johnsonapp.activity;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.materialeditext.MaterialEditText;
import com.triton.johnsonapp.materialspinner.MaterialSpinner;
import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.responsepojo.LoginResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;
import com.triton.johnsonapp.utils.NumericKeyBoardTransformationMethod;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private String TAG ="LoginActivity";

    MaterialEditText employeeMaterialEditText, userNameMaterialEditText, passwordMaterialEditText;

    MaterialSpinner mainMaterialSpinner;

    LinearLayout forgotLinearLayout, loginMainLinearLayout;

    RequestQueue requestQueue;

    TextView mainReasonCustomFontTextView;

    Button loginButton;

    String networkStatus = "", stationId = "";
    String status = "", message = "", user_level = "", station_code = "", station_name = "", empid = "", name = "", username = "", mobile;

    Dialog  dialog;

    SessionManager sessionManager;
    private String role = "";

    private String userid;
    private String token;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.w(TAG,"onCreate-->");

        sessionManager = new SessionManager(getApplicationContext());


        userNameMaterialEditText =  findViewById(R.id.user_name);
        passwordMaterialEditText =  findViewById(R.id.password);

        userNameMaterialEditText.setTransformationMethod(new NumericKeyBoardTransformationMethod());


        loginButton = findViewById(R.id.loginnnn_button);

        userNameMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        passwordMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });



        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

        loginButton.setOnClickListener(view -> {
           /* Intent intent = new Intent(CMRLLogin.this, CmrlLoginDashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
*/
            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
                Snackbar snackbar = Snackbar
                        .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", view1 -> {

                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        });

                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else {
                if (Objects.requireNonNull(userNameMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("CMRL")
                            .setContentText("Enter phone number")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();

                } else if (Objects.requireNonNull(passwordMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("CMRL")
                            .setContentText("Enter password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();
                } else {

                    LoginResponseCall();
                    //LoginUrl(ApiCall.API_URL + "login_access.php?empid=" + employeeMaterialEditText.getText().toString().replace(" ", "%20") + "&username=" + userNameMaterialEditText.getText().toString().replace(" ", "%20") + "&password=" + passwordMaterialEditText.getText().toString().replace(" ", "%20") + "&user_level=5");

                }
            }


        });

        //StationListUrl();
    }

    // default back button action
    public void onBackPressed() {

      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);*/

        //super.onBackPressed();
    }

    protected void onResume() {

        super.onResume();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();


        } else {

            //StationListUrl();
        }
    }

    @SuppressLint("LogNotTimber")
    private void LoginResponseCall() {
        dialog = new Dialog(LoginActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.LoginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LoginResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            userid = response.body().getData().get_id();
                            sessionManager.createSessionLogin(
                                    response.body().getData().get_id(),
                                    String.valueOf(response.body().getData().getUser_id()),
                                    response.body().getData().getUser_name(),
                                    response.body().getData().getUser_email_id(),
                                    response.body().getData().getUser_designation(),
                                    response.body().getData().getUser_token(),
                                    response.body().getData().getUser_status(),
                                    String.valueOf(response.body().getData().getUser_type())
                            );

                            startActivity(new Intent(LoginActivity.this,ServiceListActivity.class));

                        }


                    } else {
                        dialog.dismiss();
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("JOHNSON")
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private LoginRequest loginRequest() {

        /**
         * user_id : 12345
         * user_password : 12345
         * last_login_time : 20-10-2021 11:00 AM
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_id(userNameMaterialEditText.getText().toString().trim());
        loginRequest.setUser_password(passwordMaterialEditText.getText().toString());
        loginRequest.setLast_login_time(currentDateandTime);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(loginRequest));
        return loginRequest;
    }


}