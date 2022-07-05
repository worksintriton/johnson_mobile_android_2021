package com.triton.johnsonapp.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.interfaces.GetSpinnerListener;
import com.triton.johnsonapp.requestpojo.SelectEngineerRequest;
import com.triton.johnsonapp.requestpojo.SubordActivityFormReqest;
import com.triton.johnsonapp.responsepojo.GetFetchAttendanceListResponse;
import com.triton.johnsonapp.responsepojo.SelectEnginnerResponse;

import java.util.List;

public class SelectEngineerAdapter extends RecyclerView.Adapter<SelectEngineerAdapter.ViewHolder>{

    private Spinner EMPNo,EMPName;
     Activity context;
    private List<SelectEnginnerResponse.DataBean> dataBeanList;
    GetSpinnerListener getSpinnerListener;
    float emphours = 0;


    @NonNull
    @Override
    public SelectEngineerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_for_attendanc_adapter, parent, false);
        return new SelectEngineerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

     public void onBindViewHolder(@NonNull AttendanceListAdapter.ViewHolder holder, int position) {
        Log.w("SubordActivityForm", "dataBeanList Size : " + dataBeanList.size());
        Log.w("SubordActivityForm", "dataBeanList Size : " + dataBeanList.get(position).getEMPNO());
      //  holder..setText(dataBeanList.get(position).getEMPNO());
     //   holder.empName.setText(dataBeanList.get(position).getENAME());
      //  SelectEngineerRequest.DataBean SelectEngineerRequest = new SelectEngineerRequest.DataBean();
       // SelectEngineerRequest.setEmpNo(holder.empId.getText().toString());
        //SelectEngineerRequest.setENAME(holder.empName.getText().toString());

       // SelectEngineerRequest.setPER_OFF(holder.empPER_OFF.getSelectedItem().toString());

       /* holder..setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.w(TAG," s---> : "+parent.getItemAtPosition(pos).toString());
                //getSpinnerListener.getSpinnerListener(holder.empFN,parent.getItemAtPosition(pos).toString());
                subordActivityFormReqest.setFN(parent.getItemAtPosition(pos).toString());


            }
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });*/








      //  Data.add(SelectEngineerRequest);


    }

    @Override
    public int getItemCount() {
        return (dataBeanList != null) ? dataBeanList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView empId, empName,empREASON;
        private EditText empPER_IN_HR;
        private Spinner EmpNo,EmpName;

        public ViewHolder(View itemView) {
            super(itemView);
          //  empId = itemView.findViewById(R.id.empId);
          //  empName = itemView.findViewById(R.id.empName);
            EmpNo = itemView.findViewById(R.id.leavecode_drp);
            EmpName = itemView.findViewById(R.id.leavecode_drp);

            String [] forenoon = {"Select Value","PP","LL"};
            ArrayAdapter<String> adapterfore = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_item, forenoon);
          //  empFN.setAdapter(adapterfore);
          //  empAN.setAdapter(adapterfore);
            String [] peroff = {"Select Value","P","O"};
            ArrayAdapter<String> adapterperoff = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_item, peroff);
            //empPER_OFF.setAdapter(adapterperoff);



        }
    }
}


