package com.triton.johnsonapp.Forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.AllJobListActivity;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.activitybased.ActivityJobListActivity;
import com.triton.johnsonapp.adapter.ImageBasedArrayListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.ImageBasedStroeDataRequest;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageBasedInputFormActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_c1)
    TextView txt_c1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_c2)
    TextView txt_c2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_c3)
    TextView txt_c3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_c4)
    TextView txt_c4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_icl)
    TextView txt_icl;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_icr)
    TextView txt_icr;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_icb)
    TextView txt_icb;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    Button btn_save;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.footerView)
    LinearLayout footerView;

    private String TAG ="ImageBasedInputFormActivity";

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id,group_detail_name,job_detail_no;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_exit)
    TextView txt_exit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_datalist)
    RecyclerView rv_datalist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tl_header)
    TableLayout tl_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_job_no)
    TextView txt_job_no;


    private Dialog alertdialog;

    private Dialog dialog;
    private String userid;
    private String status;
    private String fromactivity;
    List<ImageBasedStroeDataRequest.DataBean> Data = new ArrayList<>();
    private String networkStatus = "";
    private String fromto;
    private String UKEY;
    private String UKEY_DESC;
    private int new_count;
    private int pause_count;

    SharedPreferences sharedPreferences;
    String s1;
    Context context;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_input_form);
        context = this;
        Log.w(TAG,"Oncreate -->");

        Log.e("Hi Nish","Image Based Input");
        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);


        SharedPreferences sh2 = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String pending = sh2.getString("pending", "");
        Log.e("pending", pending);
        s1 = sh2.getString("test", "activity");
        Log.e("test", s1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            group_id = extras.getString("group_id");
            group_detail_name = extras.getString("group_detail_name");

            activity_id = extras.getString("activity_id");

            job_id = extras.getString("job_id");
           // Log.e("JobID" , " " + job_id);
            job_detail_no = extras.getString("job_detail_no");
            //  UKEY = extras.getString("ESPD-ACTIMF");
            UKEY_DESC = extras.getString("UKEY_DESC");

            subgroup_id= extras.getString("subgroup_id");
            status= extras.getString("status");
            fromactivity = extras.getString("fromactivity");
            fromto = extras.getString("fromto");

            new_count = extras.getInt("new_count");
            pause_count = extras.getInt("pause_count");



            Log.w(TAG,"UKEY -->"+UKEY);
            Log.e(TAG,"JobID -->"+job_id);
            Log.w(TAG,"fromactivity -->"+fromactivity);
            Log.w(TAG,"fromto -->"+fromto);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

            if(group_detail_name != null){
                txt_toolbar_title.setText(group_detail_name);
            }

        }
        if(job_id != null){
            txt_job_no.setText("Job No : "+job_id);
        }

        if(fromactivity != null && fromactivity.equalsIgnoreCase("ABCustomerDetailsActivity")){
            if(UKEY_DESC != null){
                txt_toolbar_title.setText(UKEY_DESC);
            }
            if(job_detail_no != null){
                txt_job_no.setText("Job No : "+job_detail_no);
            }
        }

        tl_header.setVisibility(View.GONE);
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningExit();
            }
        });

        txt_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("C1");
            }
        });
        txt_c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("C2");
            }
        });
        txt_c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("C3");
            }
        });
        txt_c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("C4");
            }
        });
        txt_icl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("ICL");
            }
        });
        txt_icr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("ICR");
            }
        });
        txt_icb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupInputForm("ICB");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi", "ResourceAsColor"})
            @Override
            public void onClick(View view) {
                if(Data != null && Data.size()>0){
                    networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
                    if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
                        Snackbar snackbar = Snackbar
                                .make(footerView, "No internet connection!", Snackbar.LENGTH_LONG)
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
                    }else{
                        imageBasedStroeDataRequestCall();
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "please enter all required data", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.getView().setBackgroundTintList(ColorStateList.valueOf(R.color.warning));
                    toast.show();
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        showWarningExit();
    }

    private void showWarningExit() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        if(fromactivity != null && fromactivity.equalsIgnoreCase("ABCustomerDetailsActivity")){
                            Intent intent = new Intent(ImageBasedInputFormActivity.this, ActivityJobListActivity.class);
                            intent.putExtra("activity_id", activity_id);
                            intent.putExtra("job_id", job_id);
                            intent.putExtra("status", status);
                            intent.putExtra("UKEY", UKEY);
                            intent.putExtra("new_count", new_count);
                            intent.putExtra("pause_count", pause_count);
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
                            finish();
 //                       }
//                        else{
//                            Intent intent = new Intent(ImageBasedInputFormActivity.this, GroupListActivity.class);
//                            intent.putExtra("activity_id", activity_id);
//                            intent.putExtra("job_id", job_id);
//                            intent.putExtra("status", status);
//                            intent.putExtra("fromactivity", fromactivity);
//                            intent.putExtra("UKEY", UKEY);
//                            intent.putExtra("new_count", new_count);
//                            intent.putExtra("pause_count", pause_count);
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
//                            finish();
//                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    private void showPopupInputForm(String value) {

        try {
            alertdialog = new Dialog(ImageBasedInputFormActivity.this);
            alertdialog.setContentView(R.layout.imagebased_popup_layout);

            TextView txt_header_title = alertdialog.findViewById(R.id.txt_header_title);
            txt_header_title.setText(value);
            ImageView img_close = alertdialog.findViewById(R.id.img_close);
            EditText edt_inputValue1 = alertdialog.findViewById(R.id.edt_inputValue1);
            EditText edt_inputValue2 = alertdialog.findViewById(R.id.edt_inputValue2);
            Button btn_save = alertdialog.findViewById(R.id.btn_save);

            btn_save.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"NewApi", "ResourceAsColor"})
                @Override
                public void onClick(View view) {
                    if(edt_inputValue1.getText().toString().equals("")||edt_inputValue2.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "please enter all required data", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.getView().setBackgroundTintList(ColorStateList.valueOf(R.color.warning));
                        toast.show();
                    }else{
                        ImageBasedStroeDataRequest.DataBean dataBean = new ImageBasedStroeDataRequest.DataBean();
                        dataBean.setTitle(txt_header_title.getText().toString());
                        dataBean.setValue_a(edt_inputValue1.getText().toString());
                        dataBean.setValue_b(edt_inputValue2.getText().toString());
                        Data.add(dataBean);
                        Log.w(TAG,"DATA LIST : "+new Gson().toJson(Data));
                        alertdialog.dismiss();
                        if(Data != null && Data.size()>0){
                            tl_header.setVisibility(View.VISIBLE);
                            setView();
                        }else{
                            tl_header.setVisibility(View.GONE);
                        }
                    }


                }
            });

            img_close.setOnClickListener(v -> alertdialog.dismiss());
            Objects.requireNonNull(alertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertdialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    @SuppressLint("LogNotTimber")
    private void imageBasedStroeDataRequestCall() {
        dialog = new Dialog(ImageBasedInputFormActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FormDataStoreResponse> call = apiInterface.imageBasedStroeDataRequestCall(RestUtils.getContentType(), imageBasedStroeDataRequest());
        Log.w(TAG, "getformdataListResponseCall url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<FormDataStoreResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FormDataStoreResponse> call, @NonNull Response<FormDataStoreResponse> response) {

                Log.w(TAG, "getformdataListResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                            dialog.dismiss();

                            if (s1.equals("Job")){
                                Intent intent = new Intent(ImageBasedInputFormActivity.this, AllJobListActivity.class);
                                intent.putExtra("activity_id", activity_id);
                                intent.putExtra("job_id", job_id);
                                intent.putExtra("status", status);
                                intent.putExtra("UKEY", UKEY);
                                intent.putExtra("UKEY_DESC", UKEY_DESC);
                                intent.putExtra("new_count", new_count);
                                intent.putExtra("pause_count", pause_count);
                                startActivity(intent);
                                overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                finish();
                                dialog.dismiss();
                            }
                            else{

                                Intent intent = new Intent(ImageBasedInputFormActivity.this, ActivityJobListActivity.class);
                                intent.putExtra("activity_id", activity_id);
                                intent.putExtra("job_id", job_id);
                                intent.putExtra("status", status);
                                intent.putExtra("UKEY", UKEY);
                                intent.putExtra("new_count", new_count);
                                intent.putExtra("pause_count", pause_count);
                                startActivity(intent);
                                overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                finish();
                            }

//                            if(fromactivity != null && fromactivity.equalsIgnoreCase("ABCustomerDetailsActivity")){

//                            }
//                            else{
//                                Intent intent = new Intent(ImageBasedInputFormActivity.this, GroupListActivity.class);
//                                intent.putExtra("activity_id", activity_id);
//                                intent.putExtra("job_id", job_id);
//                                intent.putExtra("status", status);
//                                intent.putExtra("fromactivity", fromactivity);
//                                intent.putExtra("UKEY", UKEY);
//                                intent.putExtra("new_count", new_count);
//                                intent.putExtra("pause_count", pause_count);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.new_right, R.anim.new_left);
//                                finish();
//                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();


                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FormDataStoreResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("LongLogTag")
    private ImageBasedStroeDataRequest imageBasedStroeDataRequest() {

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


        ImageBasedStroeDataRequest imageBasedStroeDataRequest = new ImageBasedStroeDataRequest();
        imageBasedStroeDataRequest.setUser_id(userid);
        Log.e("UserId",userid);
        imageBasedStroeDataRequest.setActivity_id(activity_id);
        imageBasedStroeDataRequest.setJob_id(job_id);
        imageBasedStroeDataRequest.setGroup_id(group_id);
        imageBasedStroeDataRequest.setData(Data);
        imageBasedStroeDataRequest.setStart_time("");
        imageBasedStroeDataRequest.setPause_time("");
        imageBasedStroeDataRequest.setStop_time("");
        imageBasedStroeDataRequest.setStorage_status("");
        imageBasedStroeDataRequest.setDate_of_create("");
        imageBasedStroeDataRequest.setDate_of_update("");
        imageBasedStroeDataRequest.setCreated_by("ESPD-ACTIMF");
        imageBasedStroeDataRequest.setUpdated_by("");
        imageBasedStroeDataRequest.setUpdate_reason("");
        Log.e("JobID","" + job_id);

        Log.e("data_store_management/create_Request " ,"" + new Gson().toJson(imageBasedStroeDataRequest));

        Log.w(TAG, "data_store_management/create_Request " + new Gson().toJson(imageBasedStroeDataRequest));
        return imageBasedStroeDataRequest;
    }

    private void setView() {
        rv_datalist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_datalist.setItemAnimator(new DefaultItemAnimator());
        ImageBasedArrayListAdapter imageBasedArrayListAdapter = new ImageBasedArrayListAdapter(this, Data);
        rv_datalist.setAdapter(imageBasedArrayListAdapter);
    }



}