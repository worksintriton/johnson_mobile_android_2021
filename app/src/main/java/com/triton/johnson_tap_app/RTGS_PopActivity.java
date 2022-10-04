package com.triton.johnson_tap_app;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triton.johnson_tap_app.activity.Daily_Collection_DetailsActivity;
import com.triton.johnson_tap_app.activity.MainActivity;
import com.triton.johnson_tap_app.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RTGS_PopActivity extends AppCompatActivity {

    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    ImageView iv_back;
    ArrayList<ModelRecycler> modelRecyclerArrayList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_clearsearch)
    ImageView img_clearsearch;

    private String search_string ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rtgs_pop);

        ButterKnife.bind(this);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        recyclerView = findViewById(R.id.recycler1);

        fetchJSON();

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent send = new Intent(RTGS_PopActivity.this, Daily_Collection_DetailsActivity.class);
                send.putExtra("Radio_button","RTGS");
                startActivity(send);
            }
        });

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                img_clearsearch.setVisibility(View.VISIBLE);
                String Searchvalue = edt_search.getText().toString();
                Log.w(TAG,"Search Value---"+Searchvalue);
                filter(Searchvalue);
                return false;
            }
        });

    }

    private void fetchJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecyclerInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RecyclerInterface api = retrofit.create(RecyclerInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeRecycler(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.optString("Status").equals("Success")){

                modelRecyclerArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("Data");

                for (int i = 0; i < dataArray.length(); i++) {

                    ModelRecycler modelRecycler = new ModelRecycler();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    modelRecycler.setUrtno(dataobj.getString("FA_BSD_UTRNO"));
                    modelRecycler.setBank_details(dataobj.getString("FA_BSD_BANKDT"));
                    modelRecycler.setAmount(dataobj.getString("FA_BSD_AMOUNT"));
                    modelRecycler.setCustomer_name(dataobj.getString("FA_BSD_CUSACNM"));
                    modelRecycler.setIfsc_code(dataobj.getString("FA_BSD_IFSCCD"));
                    modelRecycler.setBalance_amt(dataobj.getString("FA_BSD_BALAMT"));

                    modelRecyclerArrayList.add(modelRecycler);

                }

                retrofitAdapter = new RetrofitAdapter(this,modelRecyclerArrayList);
                recyclerView.setAdapter(retrofitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            }else {
                Toast.makeText(RTGS_PopActivity.this, obj.optString("Message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void filter(String text) {
     //   ArrayList<CourseModal> filteredlist = new ArrayList<>();
        modelRecyclerArrayList = new ArrayList<>();
        Log.d("moddd", modelRecyclerArrayList.toString() + "---->" +modelRecyclerArrayList.size());

        for (ModelRecycler item : modelRecyclerArrayList) {
            if (item.getUrtno().toLowerCase().contains(text.toLowerCase()) || item.getAmount().toLowerCase().contains(text.toLowerCase())) {
                modelRecyclerArrayList.add(item);
            }
        }
        if (modelRecyclerArrayList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            retrofitAdapter.filterList(modelRecyclerArrayList);
        }
    }

}