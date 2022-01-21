package com.triton.johnsonapp.Forms;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.adapter.RowBasedArrayListAdapter;
import com.triton.johnsonapp.model.RowDataFormModel;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RowBasedInputFormActivity extends AppCompatActivity {


    private String TAG ="RowBasedInputFormActivity";
/*
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_load)
    ImageView img_load;*/
/*
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_info)
    ImageView img_info;*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_sno)
    EditText edt_sno;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimx1)
    EditText edt_dimx1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimx2)
    EditText edt_dimx2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimx3)
    EditText edt_dimx3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimx4)
    EditText edt_dimx4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimy1)
    EditText edt_dimy1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_dimy2)
    EditText edt_dimy2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_rem)
    EditText edt_rem;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_add)
    ImageView img_add;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_load)
    ImageView img_load;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_rowdatalist)
    RecyclerView rv_rowdatalist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_exit)
    TextView txt_exit;

    ArrayList<RowDataFormModel> rowdatalist = new ArrayList<>();

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id,group_detail_name;

    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_based_input_form);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            group_id = extras.getString("group_id");
            group_detail_name = extras.getString("group_detail_name");

            activity_id = extras.getString("activity_id");

            job_id = extras.getString("job_id");

            subgroup_id= extras.getString("subgroup_id");

            Log.w(TAG,"activity_id -->"+activity_id);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

            if(group_detail_name != null){
                txt_toolbar_title.setText(group_detail_name);
            }

        }
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningExit();

            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if(edt_dimx1.getText().toString().equals("")||edt_dimx2.getText().toString().equals("")

                ||edt_dimx3.getText().toString().equals("")||edt_dimx4.getText().toString().equals("")||edt_dimy1.getText().toString().equals("")||edt_dimy2.getText().toString().equals("")

                ||edt_rem.getText().toString().equals("")){

                    Toasty.warning(getApplicationContext(),"Plz enter all fields",Toasty.LENGTH_LONG).show();
                }

                else {
*/
                    i=i+1;

                    RowDataFormModel  rowDataFormModel = new RowDataFormModel();

                    rowDataFormModel.setSno(String.valueOf(i));

                    rowDataFormModel.setDimx1(edt_dimx1.getText().toString());

                    rowDataFormModel.setDimx2(edt_dimx2.getText().toString());

                    rowDataFormModel.setDimx3(edt_dimx3.getText().toString());

                    rowDataFormModel.setDimx4(edt_dimx4.getText().toString());

                    rowDataFormModel.setDimy1(edt_dimy1.getText().toString());

                    rowDataFormModel.setDimy2(edt_dimy2.getText().toString());

                    rowDataFormModel.setRem(edt_rem.getText().toString());

                    rowdatalist.add(rowDataFormModel);

                    Log.w(TAG,"rowdatalist" + new Gson().toJson(rowdatalist));

              //      edt_sno.setText("");
                    edt_dimx1.setText("");
                    edt_dimx2.setText("");
                    edt_dimx3.setText("");
                    edt_dimx4.setText("");
                    edt_dimy1.setText("");
                    edt_dimy2.setText("");
                    edt_rem.setText("");

                    setView(rowdatalist);
               /* }*/

            }
        });

        img_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> uriString = new ArrayList<>();

                uriString.add("https://kubalubra.is/wp-content/uploads/2017/11/default-thumbnail.jpg");
                Intent fullImageIntent = new Intent(RowBasedInputFormActivity.this, FullScreenImageViewActivity.class);
// uriString is an ArrayList<String> of URI of all images
                fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, uriString);
// pos is the position of image will be showned when open
                fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);
                startActivity(fullImageIntent);
            }
        });
    }

    @SuppressLint("LogNotTimber")
    private void setView(ArrayList<RowDataFormModel> rowdatalist) {


        rv_rowdatalist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_rowdatalist.setItemAnimator(new DefaultItemAnimator());
        RowBasedArrayListAdapter jobDetailListAdapter = new RowBasedArrayListAdapter(this, rowdatalist);
        rv_rowdatalist.setAdapter(jobDetailListAdapter);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        showWarningExit();
    }

    private void showWarningExit() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(RowBasedInputFormActivity.this, GroupListActivity.class);
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

}