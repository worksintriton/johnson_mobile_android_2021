package com.triton.johnsonapp.Forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.triton.johnsonapp.activity.ActivityBasedActivity;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.adapter.ImageBasedArrayListAdapter;
import com.triton.johnsonapp.adapter.RowBasedArrayListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.model.RowDataFormModel;
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

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id,group_detail_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_exit)
    TextView txt_exit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_datalist)
    RecyclerView rv_datalist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tl_header)
    TableLayout tl_header;


    private Dialog alertdialog;

    private Dialog dialog;
    private String userid;
    private String status;
    List<ImageBasedStroeDataRequest.DataBean> Data = new ArrayList<>();
    private String networkStatus = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_input_form);
        Log.w(TAG,"Oncreate -->");
        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            group_id = extras.getString("group_id");
            group_detail_name = extras.getString("group_detail_name");

            activity_id = extras.getString("activity_id");

            job_id = extras.getString("job_id");

            subgroup_id= extras.getString("subgroup_id");
            status= extras.getString("status");

            Log.w(TAG,"activity_id -->"+activity_id);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

            if(group_detail_name != null){
                txt_toolbar_title.setText(group_detail_name);
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
                    Toasty.warning(getApplicationContext(),"Please enter all fields",Toasty.LENGTH_LONG).show();

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
                        Intent intent = new Intent(ImageBasedInputFormActivity.this, GroupListActivity.class);
                        intent.putExtra("activity_id", activity_id);
                        intent.putExtra("job_id", job_id);
                        startActivity(intent);
                        overridePendingTransition(R.anim.new_right, R.anim.new_left);
                        finish();
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
                @Override
                public void onClick(View view) {
                    if(edt_inputValue1.getText().toString().equals("")||edt_inputValue2.getText().toString().equals("")){
                        Toasty.warning(getApplicationContext(),"Please enter all fields",Toasty.LENGTH_LONG).show();
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
                            Intent intent = new Intent(ImageBasedInputFormActivity.this, GroupListActivity.class);
                            intent.putExtra("activity_id",activity_id);
                            intent.putExtra("job_id",job_id);
                            intent.putExtra("status",status);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
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
        imageBasedStroeDataRequest.setCreated_by("");
        imageBasedStroeDataRequest.setUpdated_by("");
        imageBasedStroeDataRequest.setUpdate_reason("");

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