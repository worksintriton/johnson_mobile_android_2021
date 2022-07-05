package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.Forms.ImageBasedInputFormActivity;
import com.triton.johnsonapp.Forms.InputValueFormListActivity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activitybased.ABCustomerDetailsActivity;
import com.triton.johnsonapp.activitybased.ActivityJobListActivity;
import com.triton.johnsonapp.adapter.FieldListAdapter;
import com.triton.johnsonapp.adapter.SelectEngineerAdapter;
import com.triton.johnsonapp.adapter.ServiceListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.JoinInspecCheckStatusRequest;
import com.triton.johnsonapp.requestpojo.SelectEngineerRequest;
import com.triton.johnsonapp.requestpojo.SubGroupDetailManagementRequest;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.responsepojo.JoinInspectionCheckStatusResponse;
import com.triton.johnsonapp.responsepojo.SelectEnginnerResponse;
import com.triton.johnsonapp.responsepojo.SubGroupDetailManagementResponse;
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

public class SubGroupListActivity extends AppCompatActivity {


    private final int jsoncode = 1;
    private static ProgressDialog mProgressDialog;
    private ArrayList<String> names = new ArrayList<String>();
    Dialog submittedSuccessfulalertdialog;
    SelectEngineerAdapter selectEngineerAdapter;

    private String TAG = "SubGroupListActivity";

    String userid, username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_subgrouplist)
    RecyclerView rv_subgrouplist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_job_no)
    TextView txt_job_no;

    Dialog dialog;
    Dialog alertdialog;
    String networkStatus = "", message, activity_id, job_id, group_id, EmpNo, fromto, Ukey_desk, form_type, Customer_address;
    String status;
    String work_status;
    int number = 0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

    public static String responsemessage;
    String Ukey;
    String submit;
    SelectEnginnerResponse selectenginnerresponse = new SelectEnginnerResponse();
    private AlertDialog.Builder alertDialogBuilder;
    private String search_string = "";
    private String fromactivity;
    private String group_detail_name,back_address;
    String pending;
    private String job_detail_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_group_list);
        ButterKnife.bind(this);
        Log.w(TAG, "Oncreate -->");
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            status = extras.getString("status");
            group_id = extras.getString("group_id");
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            Ukey = extras.getString("UKEY");
            work_status = extras.getString("work_status");
            fromactivity = extras.getString("fromactivity");
            group_detail_name = extras.getString("group_detail_name");
            back_address = extras.getString("back_address");
            Log.w(TAG, "activity_id -->" + activity_id);
            Log.w(TAG, "group_id -->" + group_id);
            Log.w(TAG, "job_id -->" + job_id);
            Log.w(TAG, "ukey.int" + Ukey);
            Log.w(TAG, "fromactivity -->" + fromactivity);
            Log.w(TAG, "group_detail_name -->" + group_detail_name);
            Log.w(TAG, "status -->" + status);
            Log.w(TAG, "caddress -->" + back_address);
            Log.w(TAG, "workstatusss -->" + work_status);

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("UKEY", Ukey);
            myEdit.commit();

            SharedPreferences sharedPreferences4 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit4 = sharedPreferences4.edit();
            myEdit4.putString("work_s", work_status);
            myEdit4.commit();
            Log.w(TAG, "worksttusss -->" + work_status);


            //joininspectcheckstatus();
            if (Ukey.equals("OP-ACT8")) {
                txt_toolbar_title.setText("Joint Inspection-Testing");
            }

            if (Ukey.equals("OP-ACT8S")) {
                txt_toolbar_title.setText("Joint Inspection-Service");
            }

        }
        if (job_id != null) {
            txt_job_no.setText("Job No : " + job_id);
        }
        // Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show

        String s1 = sh.getString("name", "");
        Log.e("s1", s1);

        SharedPreferences sp = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdi1 = sp.edit();
        myEdi1.putString("jobid", job_id);
        myEdi1.commit();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("name", s1);
        myEdit.commit();


        if (Ukey != null && Ukey.equalsIgnoreCase("OP-ACT8S")) {
            btn_submit.setVisibility(View.GONE);
        }
        SharedPreferences sp1 = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp1.edit();
        ed.putString("job_id", job_id);
        Log.e("jobid", job_id);
        myEdit.commit();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();

//                    Intent intent = new Intent(SubGroupListActivity.this, ABCustomerDetailsActivity.class);
//                    intent.putExtra("activity_id", activity_id);
//                    intent.putExtra("job_id", job_id);
//                    intent.putExtra("status", status);
//                    intent.putExtra("job_detail_no", job_detail_no);
//                    intent.putExtra("fromto", fromto);
//                    intent.putExtra("back_address", back_address);
//                    intent.putExtra("back_address1", "back_address1");
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
            }
        });

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(), "No Internet", Toasty.LENGTH_LONG).show();

        } else {

            subgroupdetailmanagmentResponseCall();

           /* List<GetServiceListResponse.DataBean> dataBeanList = new ArrayList<>();


            for(int i=0;i<=3;i++){

                number++;
                GetServiceListResponse.DataBean dataBean = new  GetServiceListResponse.DataBean();

                Log.w(TAG,"number "+ number);

                dataBean.setService_name("Sub Group "+number);

                dataBeanList.add(dataBean);
            }


            if(dataBeanList != null && dataBeanList.size()>0){
                setView(dataBeanList);
            }*/
        }

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_string = textView.getText().toString();
                    subgroupdetailmanagmentResponseCall();
                    return true;
                }
                return false;
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupSelectEngineer();
            }
        });
        //SelectEngineerResponseCall();
        // joininspectcheckstatus();

    }

    @SuppressLint("LogNotTimber")
    private void joininspectcheckstatus() {
        Dialog dialog = new Dialog(SubGroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JoinInspectionCheckStatusResponse> call = apiInterface.JoinInspectionCheckstatusResponseCall(RestUtils.getContentType(), joinInspecCheckStatusRequest());
        Log.w(TAG, "getjoininspectioncheckstatusrequest url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<JoinInspectionCheckStatusResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JoinInspectionCheckStatusResponse> call, @NonNull Response<JoinInspectionCheckStatusResponse> response) {

                Log.w(TAG, "getjoininspectioncheckststusresponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    status = response.body().getStatus();
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        showSubmittedSuccessful();
                        // job_id = response.body().getData().getJob_no();
                        // Ukey = response.body().getData().getUkey();
                        //userrole = response.body().getData().getUser_number();


                    } else {
                        dialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<JoinInspectionCheckStatusResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }

    private JoinInspecCheckStatusRequest joinInspecCheckStatusRequest() {


        JoinInspecCheckStatusRequest joinInspecCheckStatusRequest = new JoinInspecCheckStatusRequest();
        joinInspecCheckStatusRequest.setJob_no(job_id);
        joinInspecCheckStatusRequest.setUser_number(userid);


        Log.w(TAG, "update_join_inspect_hdr/create_Request " + new Gson().toJson(joinInspecCheckStatusRequest));
        return joinInspecCheckStatusRequest;
    }

    private void showSubmittedSuccessful() {
        Log.w(TAG, "showSubmittedSuccessful -->+");
        submittedSuccessfulalertdialog = new Dialog(SubGroupListActivity.this);
        submittedSuccessfulalertdialog.setCancelable(false);
        submittedSuccessfulalertdialog.setContentView(R.layout.alert_sucess_clear);
        Button btn_goback = submittedSuccessfulalertdialog.findViewById(R.id.btn_goback);
        TextView txt_success_msg = submittedSuccessfulalertdialog.findViewById(R.id.txt_success_msg);
        txt_success_msg.setText("All data submitted successfully.");
        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submittedSuccessfulalertdialog.dismiss();
                Intent intent = new Intent(SubGroupListActivity.this, ABCustomerDetailsActivity.class);
                intent.putExtra("activity_id", activity_id);

                intent.putExtra("job_id", job_id);
                intent.putExtra("group_id", group_id);
                intent.putExtra("status", status);
                intent.putExtra("fromactivity", fromactivity);
                dialog.dismiss();
                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);
                finish();

            }
        });
        Objects.requireNonNull(submittedSuccessfulalertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        submittedSuccessfulalertdialog.show();


    }

    public void OnBackPressed() {
        if (fromactivity != null && fromactivity.equalsIgnoreCase("CustomerDetailsActivity")) {
            Intent intent = new Intent(SubGroupListActivity.this, ABCustomerDetailsActivity.class);
            intent.putExtra("activity_id", activity_id);
            intent.putExtra("job_id", job_id);
            intent.putExtra("status", status);
            intent.putExtra("job_detail_no", job_detail_no);
            intent.putExtra("fromto", fromto);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
        }
    }

    private void showPopupSelectEngineer() {
        Intent intent = new Intent(SubGroupListActivity.this, ActivitySelectEngineerPopup.class);
        startActivity(intent);


    }

          /*  img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                    Intent intent = new Intent(SubGroupListActivity.this, AllJobListActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("status",status);
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
                }







    }








    // default back button action
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubGroupListActivity.this, GroupListActivity.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("job_id",job_id);
        intent.putExtra("status",status);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }
   /* private void SelectEngineerResponseCall() {
        dialog = new Dialog(SubGroupListActivity.
                this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        //Call<SelectEnginnerResponse> call = apiInterface.selectengineerResponseCall(RestUtils.getContentType(), selectEngineerRequest());
        Log.w(TAG,"SelectEnginnerResponse url  :%s"+" "+ call.request().url().toString());


        call.enqueue(new Callback<SelectEnginnerResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SelectEnginnerResponse> call, @NonNull Response<SelectEnginnerResponse> response) {

                Log.w(TAG, "SelectEnginnerResponsecall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                             Log.w(TAG,"SelectEnginnerResponse" + new Gson().toJson(response.body()));
                            List<SelectEnginnerResponse.DataBean> dataBeanList = response.body().getData();


                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<SelectEnginnerResponse> call, Throwable t) {
                        dialog.dismiss();
                        Log.e(TAG, "--->" + t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }*/


    @SuppressLint("LogNotTimber")
    private void subgroupdetailmanagmentResponseCall() {
        dialog = new Dialog(SubGroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubGroupDetailManagementResponse> call = apiInterface.subgroupdetailmanagmentResponseCall(RestUtils.getContentType(), SubGroupDetailManagementRequest());
        Log.w(TAG, "SubGroupDetailManagementResponse url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<SubGroupDetailManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SubGroupDetailManagementResponse> call, @NonNull Response<SubGroupDetailManagementResponse> response) {

                Log.w(TAG, "SubGroupDetailManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            dialog.dismiss();
                            List<SubGroupDetailManagementResponse.DataBean> dataBeanList = response.body().getData();


                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                rv_subgrouplist.setVisibility(View.VISIBLE);
                                setView(dataBeanList);
                                txt_no_records.setVisibility(View.GONE);
                            } else {
                                rv_subgrouplist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Sub-Groups Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SubGroupDetailManagementResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("SubGroupDetailManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private SelectEngineerRequest selectEngineerRequest() {
        /* "EMPNO": "E9814",*/

        SelectEngineerRequest selectEngineerRequest = new SelectEngineerRequest();
        selectEngineerRequest.setJob_id(job_id);
        Log.w(TAG, "selectEngineerRequest " + new Gson().toJson(selectEngineerRequest));
        return selectEngineerRequest;
    }

    private SubGroupDetailManagementRequest SubGroupDetailManagementRequest() {
        /*
         * group_id : 61c1e5e09934282617679543
         * search_string
         */
        SubGroupDetailManagementRequest SubGroupDetailManagementRequest = new SubGroupDetailManagementRequest();
        SubGroupDetailManagementRequest.setGroup_id(group_id);
        SubGroupDetailManagementRequest.setSearch_string(search_string);
        Log.w(TAG, "SubGroupDetailManagementRequest " + new Gson().toJson(SubGroupDetailManagementRequest));
        return SubGroupDetailManagementRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<SubGroupDetailManagementResponse.DataBean> dataBeanList) {
        rv_subgrouplist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_subgrouplist.setItemAnimator(new DefaultItemAnimator());
        ServiceListAdapter serviceListAdapter = new ServiceListAdapter(this, dataBeanList, activity_id, job_id, group_id, TAG, status,Ukey, work_status);
        rv_subgrouplist.setAdapter(serviceListAdapter);
    }

}