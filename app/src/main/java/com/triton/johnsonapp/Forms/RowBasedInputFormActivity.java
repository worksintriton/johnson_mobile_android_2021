package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.JobDetailListAdapter;
import com.triton.johnsonapp.adapter.RowBasedArrayListAdapter;
import com.triton.johnsonapp.model.RowDataFormModel;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class RowBasedInputFormActivity extends AppCompatActivity {


    private String TAG ="RowBasedInputFormActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_load)
    ImageView img_load;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_sno)
    EditText edt_sno;

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
    @BindView(R.id.rv_rowdatalist)
    RecyclerView rv_rowdatalist;

    ArrayList<RowDataFormModel> rowdatalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_based_input_form);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_sno.getText().toString().equals("")||edt_dimx1.getText().toString().equals("")||edt_dimx2.getText().toString().equals("")

                ||edt_dimx3.getText().toString().equals("")||edt_dimx4.getText().toString().equals("")||edt_dimy1.getText().toString().equals("")||edt_dimy2.getText().toString().equals("")

                ||edt_rem.getText().toString().equals("")){

                    Toasty.warning(getApplicationContext(),"Plz enter all fields",Toasty.LENGTH_LONG).show();
                }

                else {

                    RowDataFormModel  rowDataFormModel = new RowDataFormModel();

                    rowDataFormModel.setSno(edt_sno.getText().toString());

                    rowDataFormModel.setDimx1(edt_dimx1.getText().toString());

                    rowDataFormModel.setDimx2(edt_dimx2.getText().toString());

                    rowDataFormModel.setDimx3(edt_dimx3.getText().toString());

                    rowDataFormModel.setDimx4(edt_dimx4.getText().toString());

                    rowDataFormModel.setDimy1(edt_dimy1.getText().toString());

                    rowDataFormModel.setDimy2(edt_dimy2.getText().toString());

                    rowDataFormModel.setRem(edt_rem.getText().toString());

                    rowdatalist.add(rowDataFormModel);

                    Log.w(TAG,"rowdatalist" + new Gson().toJson(rowdatalist));


                    setView(rowdatalist);
                }

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
}