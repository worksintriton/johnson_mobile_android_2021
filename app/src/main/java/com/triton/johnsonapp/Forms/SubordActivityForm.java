
package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GeneralActivity;
import com.triton.johnsonapp.adapter.AttendanceListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.GetFetchAttendanceResponse;
import com.triton.johnsonapp.requestpojo.RowBasedStroeDataRequest;
import com.triton.johnsonapp.requestpojo.SubordActivityFormReqest;
import com.triton.johnsonapp.responsepojo.GetFetchAttendanceListResponse;
import com.triton.johnsonapp.responsepojo.SubordActivityFormResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubordActivityForm extends AppCompatActivity {

    Spinner AN,FN,spr_dropdownperoff,spr_dropdownpeHRS,AN1,FN1,spr_dropdownperoff1,spr_dropdownpeHRS1,AN2,FN2,spr_dropdownperoff2,spr_dropdownpeHRS2;
    TextView crnt_date;
    Dialog dialog;
    Dialog alertdialog;
    String Tag = "SuboardActivityForm";
    String userid, username,user_phone;
    private String userrole = "";
    private String TAG = "SubordActivityForm";
    String phone_number,thisDate;
    String networkStatus = "",message;
    List<GetFetchAttendanceListResponse.DataBean> dataBeanList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_rowdatalist)
    RecyclerView rv_rowdatalist;
    Dialog submittedSuccessfulalertdialog;
    Button btn_save;
    ImageView img_back;
    private final List<SubordActivityFormReqest.DataBean> Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subord_form);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"Userid"+userid);
        userrole = user.get(SessionManager.KEY_USERROLE);
        phone_number="9043456963";

        Log.w(TAG,"userrole  : "+userrole);
        user_phone = user.get(SessionManager.KEY_USERID);
        Log.w(TAG,"user_phone  : "+user_phone);
        username = user.get(SessionManager.KEY_USERNAME);

        img_back = findViewById(R.id.img_back);

        String [] Afternoon = {"Select Value","PP","LL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Afternoon);

        String [] forenoon = {"Select Value","PP","LL"};
        ArrayAdapter<String> adapterfore = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Afternoon);

        String [] peroff = {"Select Value","P","L"};
        ArrayAdapter<String> adapterperoff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, peroff);

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();
        thisDate = currentDate.format(todayDate);
        TextView crnt_date = (TextView)findViewById(R.id.crnt_date);
        Log.w(Tag,thisDate);
        crnt_date.setText(thisDate);



        rv_rowdatalist = findViewById(R.id.rv_rowdatalist1);
        rv_rowdatalist.setHasFixedSize(true);


        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(), "No Internet", Toasty.LENGTH_LONG).show();

        }else
        {
            getfetchatendancelist();
        }
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Data != null && Data.size()>0)
                {
                    saveSubordAttendanceForm();
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }

    private void saveSubordAttendanceForm() {
        dialog = new Dialog(SubordActivityForm.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubordActivityFormResponse> call = apiInterface.postsuboradattendenceRequestCall(RestUtils.getContentType(), postsuboradattendenceRequest());
        call.enqueue(new Callback<SubordActivityFormResponse>() {
            @Override
            public void onResponse(Call<SubordActivityFormResponse> call, Response<SubordActivityFormResponse> response) {
                Log.w(TAG, "getformdataListResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        String Submitted_status = response.body().getStatus();
                        if(Submitted_status !=null && Submitted_status.equalsIgnoreCase("Success"))
                        {
                            dialog.dismiss();
                            showSubmittedSuccessful();
                        }

                    }
                    else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }

                }
            }

            @Override
            public void onFailure(Call<SubordActivityFormResponse> call, Throwable t) {

            }
        });
    }

    private SubordActivityFormReqest postsuboradattendenceRequest() {

        /*"user_phone":"9887766544321",
           "current_date":"23-10-2022",
        "EMPNO": "E16622",
                "ENAME": "SHEIK MOHAMMED SHAHINSHA B",
                "DATE": "12-10-1995",
                "FN": "LL",
                "AN": "",
                "PER_IN_HR": "1",
                "PER_OFF": "Office",
                "REASON": "for Testing Process"*/
        SubordActivityFormReqest subordActivityFormReqest = new SubordActivityFormReqest();
        subordActivityFormReqest.setUser_phone(user_phone);
        subordActivityFormReqest.setCurrent_date(thisDate);
        subordActivityFormReqest.setData(Data);
        Log.w(TAG, "data_store_management/create_Request " + new Gson().toJson(subordActivityFormReqest));
        return subordActivityFormReqest;
    }


    private void getfetchatendancelist() {
        dialog = new Dialog(SubordActivityForm.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        //Creating an object of our api interface
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetFetchAttendanceListResponse> call = apiInterface.getfetchattendanceListResponseCall(RestUtils.getContentType(), getFieldListRequest());
        Log.w(TAG,"phone--"+phone_number);
        call.enqueue(new Callback<GetFetchAttendanceListResponse>() {
            @Override
            public void onResponse(Call<GetFetchAttendanceListResponse> call, Response<GetFetchAttendanceListResponse> response) {
                if(response.body()!= null)
                {
                    dialog.dismiss();
                    Log.w(TAG, "Submitted_status ---" +response.body().getCode());
                    if (response.body().getData() != null)
                    {
                        dataBeanList = response.body().getData();
                        Log.w(TAG, "dataBeanList Size : " + dataBeanList.size());
                        rv_rowdatalist.setLayoutManager(new LinearLayoutManager(SubordActivityForm.this));
                        rv_rowdatalist.setAdapter(new AttendanceListAdapter(SubordActivityForm.this,dataBeanList,Data));


//
                    }
                }else
                {
                    dialog.dismiss();
                    Log.w(TAG, "Submitted_status else--> " +response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetFetchAttendanceListResponse> call, Throwable t) {

            }
        });
    }

    private GetFetchAttendanceResponse getFieldListRequest() {
        /*"phone_number": "9043456963"*/
        GetFetchAttendanceResponse getFetchAttendanceResponse = new GetFetchAttendanceResponse();
        getFetchAttendanceResponse.setPhone_number(user_phone);

        return getFetchAttendanceResponse;
    }

    private void showSubmittedSuccessful() {
        Log.w(TAG, "showSubmittedSuccessful -->+");
        submittedSuccessfulalertdialog = new Dialog(SubordActivityForm.this);
        submittedSuccessfulalertdialog.setCancelable(false);
        submittedSuccessfulalertdialog.setContentView(R.layout.alert_success);
        Button btn_goback = submittedSuccessfulalertdialog.findViewById(R.id.btn_goback);
        TextView txt_success_msg = submittedSuccessfulalertdialog.findViewById(R.id.txt_success_msg);
        TextView txt_success_msg1 = submittedSuccessfulalertdialog.findViewById(R.id.txt_success_msg1);
        txt_success_msg.setText("Subordinate Attendance");
        txt_success_msg1.setText("Submitted Successfully");
        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submittedSuccessfulalertdialog.dismiss();
                Intent intent = new Intent(SubordActivityForm.this, GeneralActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);
                finish();

            }
        });
        Objects.requireNonNull(submittedSuccessfulalertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        submittedSuccessfulalertdialog.show();
    }
}