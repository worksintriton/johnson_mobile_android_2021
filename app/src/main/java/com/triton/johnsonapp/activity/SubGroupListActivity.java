package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.ServiceListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.SubGroupDetailManagementRequest;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.responsepojo.SubGroupDetailManagementResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubGroupListActivity extends AppCompatActivity {



    private String TAG ="SubGroupListActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_subgrouplist)
    RecyclerView rv_subgrouplist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog  dialog;

    String networkStatus = "",message,activity_id,job_id,group_id;

    int number=0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    private String search_string ="";
    private String fromactivity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_group_list);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            group_id = extras.getString("group_id");
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"activity_id -->"+activity_id);
            Log.w(TAG,"group_id -->"+group_id);
            Log.w(TAG,"job_id -->"+job_id);
            Log.w(TAG,"fromactivity -->"+fromactivity);

        }


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



        }


    // default back button action
    public void onBackPressed() {


        super.onBackPressed();
        Intent intent = new Intent(SubGroupListActivity.this, GroupListActivity.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("job_id",job_id);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }

    @SuppressLint("LogNotTimber")
    private void subgroupdetailmanagmentResponseCall() {
        dialog = new Dialog(SubGroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubGroupDetailManagementResponse> call = apiInterface.subgroupdetailmanagmentResponseCall(RestUtils.getContentType(), SubGroupDetailManagementRequest());
        Log.w(TAG,"SubGroupDetailManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SubGroupDetailManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SubGroupDetailManagementResponse> call, @NonNull Response<SubGroupDetailManagementResponse> response) {

                Log.w(TAG,"SubGroupDetailManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            dialog.dismiss();
                            List<SubGroupDetailManagementResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                                rv_subgrouplist.setVisibility(View.VISIBLE);
                                setView(dataBeanList);
                                txt_no_records.setVisibility(View.GONE);
                            }
                           else  {
                                rv_subgrouplist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Sub-Groups Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

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
    private SubGroupDetailManagementRequest SubGroupDetailManagementRequest() {



        /**
         * group_id : 61c1e5e09934282617679543
         * search_string
         */
        SubGroupDetailManagementRequest SubGroupDetailManagementRequest = new SubGroupDetailManagementRequest();
        SubGroupDetailManagementRequest.setGroup_id(group_id);
        SubGroupDetailManagementRequest.setSearch_string(search_string);



        Log.w(TAG,"SubGroupDetailManagementRequest "+ new Gson().toJson(SubGroupDetailManagementRequest));
        return SubGroupDetailManagementRequest;
    }
    @SuppressLint("LogNotTimber")
    private void setView(List<SubGroupDetailManagementResponse.DataBean> dataBeanList) {


        rv_subgrouplist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_subgrouplist.setItemAnimator(new DefaultItemAnimator());
        ServiceListAdapter serviceListAdapter = new ServiceListAdapter(this, dataBeanList,activity_id,job_id, group_id,TAG);
        rv_subgrouplist.setAdapter(serviceListAdapter);
    }

}