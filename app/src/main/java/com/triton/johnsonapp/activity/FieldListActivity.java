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
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.FieldListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
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

public class FieldListActivity extends AppCompatActivity {



    private String TAG ="FieldListActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_fieldlist)
    RecyclerView rv_fieldlist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog dialog;

    String networkStatus = "";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_prev)
    LinearLayout ll_prev;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_next)
    LinearLayout ll_next;

    private int totalPages;
    private int currentPage = 0;

    public  int TOTAL_NUM_ITEMS = 0;
    public  int ITEMS_PER_PAGE=6;
    public  int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
    public  int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
    List<GetFieldListResponse.DataBean> dataBeanList;

    public  List<GetFieldListResponse.DataBean> generatePage(int currentPage)
    {
        int startItem=currentPage*ITEMS_PER_PAGE+1;
        int numOfData=ITEMS_PER_PAGE;
        List<GetFieldListResponse.DataBean> pageData=new ArrayList<>();

        if (currentPage==LAST_PAGE && ITEMS_REMAINING>0)
        {
            for (int i=startItem;i<startItem+ITEMS_REMAINING;i++)
            {
                pageData.add(dataBeanList.get(i));
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                pageData.add(dataBeanList.get(i));
            }
        }
        return pageData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_list);

        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        ll_prev.setBackgroundResource(R.drawable.edit_background_with_border);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {

            getfieldListResponseCall();
        }

        //NAVIGATE
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPage += 1;
                List<GetFieldListResponse.DataBean> dataBeanList = generatePage(currentPage);
                setView(dataBeanList);
                // enableDisableButtons();
               // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));
                toggleButtons();

            }
        });
        ll_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                List<GetFieldListResponse.DataBean> dataBeanList = generatePage(currentPage);
                setView(dataBeanList);
               // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));

                toggleButtons();
            }
        });



    }

    private void toggleButtons() {
        if (currentPage == totalPages) {
            ll_next.setBackgroundResource(R.drawable.edit_background_with_border);
            ll_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        } else if (currentPage == 0) {
            ll_prev.setBackgroundResource(R.drawable.edit_background_with_border);
            ll_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        } else if (currentPage >= 1 && currentPage <= totalPages) {
            ll_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            ll_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        }
    }


    // default back button action
    public void onBackPressed() {
        Intent intent = new Intent(FieldListActivity.this,ServiceListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);

        //super.onBackPressed();
    }

    @SuppressLint("LogNotTimber")
    public void getfieldListResponseCall(){
        dialog = new Dialog(FieldListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        //Creating an object of our api interface
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetFieldListResponse> call = apiInterface.getfieldListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<GetFieldListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<GetFieldListResponse> call, @NonNull Response<GetFieldListResponse> response) {
                dialog.dismiss();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"GetFieldListResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData()!= null){

                             dataBeanList = response.body().getData();

                            if(dataBeanList != null && dataBeanList.size()>0){

                               totalPages = dataBeanList.size() / 6;

                                List<GetFieldListResponse.DataBean> dataBeanLists = generatePage(currentPage);

                                setView(dataBeanLists);
                            }
                        }


                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<GetFieldListResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.w(TAG,"GetFieldListResponse flr"+t.getMessage());
            }
        });

    }


    @SuppressLint("LogNotTimber")
    private void setView(List<GetFieldListResponse.DataBean> dataBeanList) {

        rv_fieldlist.setNestedScrollingEnabled(true);
        rv_fieldlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_fieldlist.setItemAnimator(new DefaultItemAnimator());
        FieldListAdapter FieldListAdapter = new FieldListAdapter(getApplicationContext(), dataBeanList);
        rv_fieldlist.setAdapter(FieldListAdapter);
    }

}