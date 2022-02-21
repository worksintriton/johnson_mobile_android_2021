package com.triton.johnsonapp.Forms;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.ActivityBasedActivity;
import com.triton.johnsonapp.adapter.FormFiveListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.interfaces.GetAcceptQtyListner;
import com.triton.johnsonapp.interfaces.GetDamageQtyListner;
import com.triton.johnsonapp.interfaces.GetExcessListner;
import com.triton.johnsonapp.interfaces.GetRemarksListner;
import com.triton.johnsonapp.interfaces.GetShortListner;
import com.triton.johnsonapp.requestpojo.FormFiveDataRequest;
import com.triton.johnsonapp.responsepojo.FormFiveDataResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputFormFiveActivity extends AppCompatActivity implements GetAcceptQtyListner, GetDamageQtyListner, GetExcessListner, GetShortListner, GetRemarksListner {

    private final String TAG ="InputFormFiveActivity";

    String _id,activity_id,job_detail_id;


    String userid,username;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_formfive)
    RecyclerView rv_formfive;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.footerView)
    LinearLayout footerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;





    Dialog dialog;
    Dialog alertdialog;

    String networkStatus = "",message,job_id;

    int number=0;
    private final String search_string = "";

    private final FormFiveDataResponse.DataBean Data = new FormFiveDataResponse.DataBean();
    List<FormFiveDataResponse.DataBean.MaterialDetailsBean> dataBeanList;
    private String Remarks = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_five);
        ButterKnife.bind(this);

        Log.w(TAG,"Oncreate -->");

        footerView.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("_id");
            activity_id = extras.getString("activity_id");
            job_detail_id = extras.getString("job_detail_id");

            Log.w(TAG,"_id -->"+_id);
            Log.w(TAG,"activity_id -->"+activity_id);
            Log.w(TAG,"job_detail_id " + job_detail_id);


        }



        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_USERNAME);
        Log.w(TAG,"userid -->"+userid);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            formFiveDataResponseCall();
        }



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Remarks != null && !Remarks.isEmpty()){
                    Data.setRemarks(Remarks);
                    Data.setMaterial_details(dataBeanList);
                    formFiveStroeDataRequestCall();
                }else{
                    Toasty.warning(getApplicationContext(),"Please enter the remarks",Toasty.LENGTH_LONG).show();

                }

            }
        });



    }

    @SuppressLint("LogNotTimber")
    private void formFiveDataResponseCall() {
        dialog = new Dialog(InputFormFiveActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FormFiveDataResponse> call = apiInterface.formFiveDataResponseCall(RestUtils.getContentType(), formFiveDataRequest());
        Log.w(TAG,"FormFiveDataResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FormFiveDataResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FormFiveDataResponse> call, @NonNull Response<FormFiveDataResponse> response) {

                Log.w(TAG,"FormFiveDataResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            dialog.dismiss();

                           dataBeanList = response.body().getData().getMaterial_details();

                           if(dataBeanList != null && dataBeanList.size()>0){

                                footerView.setVisibility(View.VISIBLE);
                                rv_formfive.setVisibility(View.VISIBLE);
                                setView(dataBeanList);
                                txt_no_records.setVisibility(View.GONE);
                            }
                            else {
                                footerView.setVisibility(View.GONE);
                                rv_formfive.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Groups Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<FormFiveDataResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("FormFiveDataResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private FormFiveDataRequest formFiveDataRequest() {
        /*
         * job_no : 111
         * user_id : dddf
         */

        FormFiveDataRequest formFiveDataRequest = new FormFiveDataRequest();
        formFiveDataRequest.setUser_id(userid);
        formFiveDataRequest.setJob_no(job_detail_id);

        Log.w(TAG,"formFiveDataRequest "+ new Gson().toJson(formFiveDataRequest));
        return formFiveDataRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<FormFiveDataResponse.DataBean.MaterialDetailsBean> dataBeanList) {
        rv_formfive.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_formfive.setItemAnimator(new DefaultItemAnimator());
        FormFiveListAdapter formFiveListAdapter = new FormFiveListAdapter(this, dataBeanList,activity_id,job_id,this,this,this,this,this);
        rv_formfive.setAdapter(formFiveListAdapter);
    }

    @SuppressLint("LogNotTimber")
    private void formFiveStroeDataRequestCall() {
        dialog = new Dialog(InputFormFiveActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.formFiveStroeDataRequestCall(RestUtils.getContentType(), formFiveDataResponse());
        Log.w(TAG, "formFiveStroeDataRequestCall url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG, "formFiveStroeDataRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                            startActivity(new Intent(InputFormFiveActivity.this, ActivityBasedActivity.class));

                        }


                    } else {
                        dialog.dismiss();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
            }
        });

    }
    private FormFiveDataResponse formFiveDataResponse() {

        /*
         * user_id : 123456
         * cat_id : 123456
         * service_id : 123456
         * job_no : 123456
         * data_store : [{"__v":0,"_id":"61aa033385104f58378b3b1d","cat_id":"61a8b8752d9a15335c1e511f","created_by":"Admin","date_of_create":"12/3/2021, 5:14:51 PM","date_of_update":"12/3/2021, 5:14:51 PM","field_comments":"filed Length should be 10 digit","field_length":"10","field_name":"Field 1","field_type":"String","field_update_reason":"","field_value":"","updated_by":"Admin"}]
         * start_time : 23-10-2021 11:00 AM
         * pause_time : 23-10-2021 11:00 AM
         * stop_time : 23-10-2021 11:00 AM
         * storage_status : Temp
         * date_of_create : 23-10-2021 11:00 AM
         * date_of_update :
         * created_by : 123456
         * updated_by :
         * update_reason :
         */


        FormFiveDataResponse formFiveDataResponse  = new FormFiveDataResponse();
        formFiveDataResponse.setUser_id(userid);
        formFiveDataResponse.setActivity_id(activity_id);
        formFiveDataResponse.setJob_id(job_id);
        formFiveDataResponse.setGroup_id("");
        formFiveDataResponse.setData(Data);
        formFiveDataResponse.setStart_time("");
        formFiveDataResponse.setPause_time("");
        formFiveDataResponse.setStop_time("");
        formFiveDataResponse.setStorage_status("");
        formFiveDataResponse.setDate_of_create("");
        formFiveDataResponse.setDate_of_update("");
        formFiveDataResponse.setCreated_by("");
        formFiveDataResponse.setUpdated_by("");
        formFiveDataResponse.setUpdate_reason("");

        Log.w(TAG, "data_store_management/create_Request form 5 " + new Gson().toJson(formFiveDataResponse));
        return formFiveDataResponse;
    }

    @Override
    public void getAcceptQtyListner(EditText edt_number, String s, int position) {
        Log.w(TAG,"getAcceptQtyListner : "+edt_number+" s : "+s+" position : "+position);
        int qty = 0;

        try {
            qty = Integer.parseInt(s);
        dataBeanList.get(position).setAccepts(qty);
        Log.w(TAG,"getAcceptQtyListner : "+"dataBeanList : "+new Gson().toJson(dataBeanList));
        }catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

    }

    @Override
    public void getDamageQtyListner(EditText edt_number, String s, int position) {

        Log.w(TAG,"getDamageQtyListner : "+edt_number+" s : "+s+" position : "+position);
        int qty = 0;

        try {
            qty = Integer.parseInt(s);
            dataBeanList.get(position).setDemage(qty);
            Log.w(TAG,"getDamageQtyListner : "+"dataBeanList : "+new Gson().toJson(dataBeanList));
        }catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

    }

    @Override
    public void getExcessListner(EditText edt_number, String s, int position) {
        Log.w(TAG,"getExcessListner : "+edt_number+" s : "+s+" position : "+position);
        int qty = 0;

        try {
            qty = Integer.parseInt(s);
        dataBeanList.get(position).setExcess(qty);
        Log.w(TAG,"getExcessListner : "+"dataBeanList : "+new Gson().toJson(dataBeanList));
        }catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    @Override
    public void getShortListner(EditText edt_number, String s, int position) {

        Log.w(TAG,"getShortListner : "+edt_number+" s : "+s+" position : "+position);
        int qty = 0;

        try {
            qty = Integer.parseInt(s);
        dataBeanList.get(position).setShortage(qty);
        Log.w(TAG,"getShortListner : "+"dataBeanList : "+new Gson().toJson(dataBeanList));
        }catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    @Override
    public void getRemarksListner(String s) {
        Remarks = s;
    }
}