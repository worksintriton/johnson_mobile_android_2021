package com.triton.johnson_tap_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.ActivityGetListNumberRequest;
import com.triton.johnson_tap_app.responsepojo.ActivityGetListNumberResponse;
import com.triton.johnsonapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleActivity extends AppCompatActivity {

    private String TAG ="ActivityBasedActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_activitybasedlist)
    RecyclerView rv_activitybasedlist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_clearsearch)
    ImageView img_clearsearch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    Dialog dialog;

    String networkStatus = "",message;

    int number=0;

    private String search_string = "";

    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;
    List<ActivityGetListNumberResponsee.DataBean> dataBeanList;
    ActivityBasedListAdapter activityBasedListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ButterKnife.bind(this);
        activityGetListNumberResponseCall();

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                   search_string = textView.getText().toString();
//                    activityGetListNumberResponseCall();
//                    return true;
//                }
//                return false;
                img_clearsearch.setVisibility(View.VISIBLE);
                String Searchvalue = edt_search.getText().toString();
                Log.w(TAG,"Search Value---"+Searchvalue);
                filter(Searchvalue);
                return false;
            }
        });

        img_clearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
                rv_activitybasedlist.setVisibility(View.VISIBLE);
                activityGetListNumberResponseCall();
                img_clearsearch.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void filter(String s) {
        List<ActivityGetListNumberResponsee.DataBean> filteredlist = new ArrayList<>();
        for(ActivityGetListNumberResponsee.DataBean item : dataBeanList)
        {
            if(item.getFA_BSD_UTRNO().toLowerCase().contains(s.toLowerCase()))
            {
                Log.w(TAG,"filter----"+item.getFA_BSD_UTRNO().toLowerCase().contains(s.toLowerCase()));
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty())
        {
            Toast.makeText(this,"No Data Found ... ",Toast.LENGTH_SHORT).show();
            rv_activitybasedlist.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
            txt_no_records.setText("No Parts Available");
        }else
        {
            activityBasedListAdapter.filterList(filteredlist);
        }
    }

    private void activityGetListNumberResponseCall() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<ActivityGetListNumberResponsee> call = apiInterface.filterPageInfoResponseCall(RestUtils.getContentType());
        Log.w(TAG,"ActivityListManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ActivityGetListNumberResponsee>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ActivityGetListNumberResponsee> call, @NonNull Response<ActivityGetListNumberResponsee> response) {

                Log.w(TAG,"ActivityListManagementResponse" + new Gson().toJson(response.body()));

                setView(dataBeanList);
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if(response.body().getData() != null){
//
//                            dialog.dismiss();
//
//                            dataBeanList = response.body().getData();
//
//                            if(dataBeanList != null && dataBeanList.size()>0){
//                                rv_activitybasedlist.setVisibility(View.VISIBLE);
//                                txt_no_records.setVisibility(View.GONE);
//                                setView(dataBeanList);
//                            }
//
//                            else {
//                                rv_activitybasedlist.setVisibility(View.GONE);
//                                txt_no_records.setVisibility(View.VISIBLE);
//                                txt_no_records.setText("No Activity Available");
//                            }
//
//                        }
//
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();
//
//                        //showErrorLoading(response.body().getMessage());
//                    }
//                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<ActivityGetListNumberResponsee> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("ActivityListManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setView(List<ActivityGetListNumberResponsee.DataBean> dataBeanList) {


        rv_activitybasedlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_activitybasedlist.setItemAnimator(new DefaultItemAnimator());
        activityBasedListAdapter = new ActivityBasedListAdapter(this, dataBeanList);
        rv_activitybasedlist.setAdapter(activityBasedListAdapter);
    }
}