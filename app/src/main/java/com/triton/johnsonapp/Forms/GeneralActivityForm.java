package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GeneralActivity;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.GetLeaveFieldListResponse;
import com.triton.johnsonapp.responsepojo.LeaveFormDataStoreResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralActivityForm extends AppCompatActivity  {

    String userid, username;
    private String userrole = "";
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateFRMView,dateTOView;
    private int year, month, day;

    Spinner leavecode_drp;
    RadioButton radio_ffn,radio_fan,radio_afn,radio_aan;
    EditText no_of_days,empno,reason;
    Button btn_success;
    Dialog dialog;
    Dialog alertdialog;
    Dialog submittedSuccessfulalertdialog;
    float days = 0;
    private String status ="X";
    private String source = "JLSMART";
    private String TAG = "GeneralActivityForm";
    private Dialog alertDialog;
    String selectedvalue;
    String networkStatus = "",message;
    boolean isAllFieldsChecked = false;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_form);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        userrole = user.get(SessionManager.KEY_USERROLE);


        Log.w(TAG,"userrole  : "+userrole);

        username = user.get(SessionManager.KEY_USERNAME);
        Log.w(TAG,"Username"+username);

        leavecode_drp = findViewById(R.id.leavecode_drp);
        String [] leavecode = {"Select Value","ACC","ANL","CLV","HOL","AHL","JUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leavecode);
        leavecode_drp.setAdapter(adapter);
        empno = (EditText) findViewById(R.id.empno);
        dateFRMView = (TextView) findViewById(R.id.edt_frmdatetime);
        dateTOView = (TextView) findViewById(R.id.todt_datetime);
        btn_success = (Button) findViewById(R.id.btn_success);
        no_of_days = (EditText) findViewById(R.id.no_of_days);
        img_back = findViewById(R.id.img_back);
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        radio_ffn = (RadioButton) findViewById(R.id.radio_ffn);
        radio_fan = (RadioButton) findViewById(R.id.radio_fan);
        radio_afn = (RadioButton) findViewById(R.id.radio_afn);
        radio_aan = (RadioButton) findViewById(R.id.radio_aan);

        reason = (EditText) findViewById(R.id.reason);

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        EditText leavedate = (EditText) findViewById(R.id.leavedate);
        leavedate.setText(thisDate);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }




        dateFRMView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
                //datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
            }

        });





        radio_ffn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                switch(view.getId()) {
                    case R.id.radio_ffn:
                        if (checked)
                            radio_fan.setChecked(false);
                            radio_afn.setChecked(true);
                            radio_aan.setChecked(false);

                            setRadioBtn();
                        break;
                    case R.id.radio_fan:
                        if (checked)
                            radio_ffn.setChecked(false);
                            radio_aan.setChecked(true);
                            radio_afn.setChecked(false);

                            setRadioBtn();
                        break;
                }
                Log.w(TAG,"Radio Clicked");
            }
        });

        radio_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch(view.getId()) {
                    case R.id.radio_ffn:
                        if (checked)
                            radio_fan.setChecked(false);
                            radio_afn.setChecked(true);
                            radio_aan.setChecked(false);
                            setRadioBtn();
                        break;
                    case R.id.radio_fan:
                        if (checked)
                            radio_ffn.setChecked(false);
                            radio_aan.setChecked(true);
                            radio_afn.setChecked(false);
                            setRadioBtn();
                        break;
                }
                Log.w(TAG,"Radio Clicked");
            }
        });

        radio_afn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch(view.getId()) {
                    case R.id.radio_afn:
                        if (checked)
                            radio_afn.setChecked(true);
                            radio_aan.setChecked(false);


                        break;
                    case R.id.radio_aan:
                        if (checked)
                            radio_aan.setChecked(true);
                            radio_afn.setChecked(false);


                        break;
                }
                Log.w(TAG,"Radio Clicked");
            }
        });

        radio_aan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch(view.getId()) {
                    case R.id.radio_ffn:
                        if (checked)

                        radio_afn.setChecked(true);
                        radio_aan.setChecked(false);
                        Toast.makeText(getBaseContext(), "sdfgfsdg"+radio_afn.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_aan:
                        if (checked)

                        radio_aan.setChecked(true);
                        radio_afn.setChecked(false);

                        break;
                }
                Log.w(TAG,"Radio Clicked");
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empno.getText().toString().equals("") || leavecode_drp.getSelectedItem().toString().equals("")
                    || no_of_days.getText().toString().equals("") || dateFRMView.getText().toString().equals(""))
                {
                    //checkSession();

                    Toast toast = Toast.makeText(getApplicationContext(), "please enter all required data", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);

                    toast.show();

                }else
                {
                    saveleaveapplication();
                }


            }
        });

        no_of_days.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String noofdays = charSequence.toString();
                String doubleAsString = String.valueOf(noofdays);
                if(!noofdays.equals("")) {
                    days = Float.parseFloat(noofdays);
                }
                else{
                    Toast.makeText(GeneralActivityForm.this,"Please give proper number value in days !!",Toast.LENGTH_LONG).show();
                }
                int datevalue = Math.round(days);
                Log.w(TAG,"Radio Value11111"+(days-datevalue));
                if(((days-datevalue)+"").equals("-0.5"))
                {
                    Log.w(TAG,"Radio Value1111122----"+(days-datevalue));
                }else if(((days-datevalue)+"").equals("0.0"))
                {

                    Log.w(TAG,"Radio Value11----"+(days-datevalue));
                }else
                {
                    no_of_days.getText().clear();
                    Toast.makeText(GeneralActivityForm.this,"Please Proper No.of.days It's only accept(0.5) !!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

//    private void checkSession() {
//        if(radio_ffn.isChecked())
//        {
//            Toast.makeText(getApplicationContext(), "Please enter all required data", Toast.LENGTH_SHORT).show();
//        }else
//        {
//            Toast.makeText(getApplicationContext(), "Please enter all required data", Toast.LENGTH_SHORT).show();
//        }
//    }

    private boolean CheckAllFields() {
        if (empno.length() == 0) {
            showErrorLoading();
        }

        return true;
    }

    public void showErrorLoading(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("please enter all required data");
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    private void saveleaveapplication() {
        dialog = new Dialog(GeneralActivityForm.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LeaveFormDataStoreResponse> call = apiInterface.leaveRequestCall(RestUtils.getContentType(), checkLeaveRequest());
        Log.w(TAG,"startWorkRequestCall url  :%s"+" "+ call.request().url().toString());
        Log.w(TAG,"Empno"+empno.getText().toString());
        Log.w(TAG,"LVCODE"+leavecode_drp.getSelectedItem().toString());

        call.enqueue(new Callback<LeaveFormDataStoreResponse>() {
            @Override
            public void onResponse(Call<LeaveFormDataStoreResponse> call, Response<LeaveFormDataStoreResponse> response) {
                Log.w(TAG,"startWorkRequestCall----" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        String Submitted_status = response.body().getStatus();
                        if(Submitted_status !=null && Submitted_status.equalsIgnoreCase("Success"))
                        {
                            dialog.dismiss();
                            showSubmittedSuccessful();
                        }

                    }
                    else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }

                }
            }

            @Override
            public void onFailure(Call<LeaveFormDataStoreResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private GetLeaveFieldListResponse checkLeaveRequest() {
        GetLeaveFieldListResponse checkLeaveRequest = new GetLeaveFieldListResponse();

        checkLeaveRequest.setPA_LAH_EMPNO(empno.getText().toString());
        checkLeaveRequest.setPA_LAH_LVCODE(leavecode_drp.getSelectedItem().toString());
        checkLeaveRequest.setPA_LAH_NOFDYS(no_of_days.getText().toString());
        checkLeaveRequest.setPA_LAH_FRMDT(dateFRMView.getText().toString());
        if(radio_ffn.isChecked())
        {
            selectedvalue = "FN";
            Log.w(TAG,"RADIO VALUE"+selectedvalue);
            checkLeaveRequest.setPA_LAH_FRMSES(selectedvalue);
        }else
        {
            selectedvalue = "AN";
            Log.w(TAG,"RADIO VALUE"+selectedvalue);
            checkLeaveRequest.setPA_LAH_FRMSES(selectedvalue);

        }

        checkLeaveRequest.setPA_LAH_TODT(dateTOView.getText().toString());
        if(radio_afn.isChecked())
        {
            selectedvalue = "FN";
            Log.w(TAG,"RADIO VALUE"+selectedvalue);
            checkLeaveRequest.setPA_LAH_TOSES(selectedvalue);
        }else
        {
            selectedvalue = "AN";
            Log.w(TAG,"RADIO VALUE"+selectedvalue);
            checkLeaveRequest.setPA_LAH_TOSES(selectedvalue);

        }
        checkLeaveRequest.setPA_LAH_REASON(reason.getText().toString());
        checkLeaveRequest.setPA_LAH_STATUS(status);
        checkLeaveRequest.setPA_LAH_ENTRYBY(username);
        checkLeaveRequest.setPA_LAH_SOURCE(source);
        Log.w(TAG, "data_store_management/create_Request " + new Gson().toJson(checkLeaveRequest));
        return checkLeaveRequest;

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            return dialog;
//            return new DatePickerDialog(this,
//                    myDateListener, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    final Calendar calender = Calendar.getInstance();
                    calender.set(Calendar.YEAR,arg1);
                    calender.set(Calendar.MONTH,arg2);
                    calender.set(Calendar.DAY_OF_MONTH,arg3);
                    String noofdays = no_of_days.getText().toString();
                    String doubleAsString = String.valueOf(noofdays);
                    days = Float.parseFloat(noofdays);

                    setRadioBtn();
                    //Log.w(TAG,"iNTEGERPART"+doubleAsString.substring(0, indexOfDecimal));
                    calender.add(Calendar.DAY_OF_MONTH,  Math.round(days));
                    Date s = calender.getTime();

                    dateTOView.setText(sdf.format(s));
                    //setDate(arg1, arg2+1, arg3);






                }
            };




    private void showDate(int year, int month, int day) {
        dateFRMView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void setRadioBtn(){
        String noofdays = no_of_days.getText().toString();
        String doubleAsString = String.valueOf(noofdays);
        if(!noofdays.equals("")) {
            days = Float.parseFloat(noofdays);
        }
        else{
            Toast.makeText(GeneralActivityForm.this,"Please give proper number value in days !!",Toast.LENGTH_LONG).show();
        }
        int datevalue = Math.round(days);
        Log.w(TAG,"Radio Value"+(days-datevalue));
        if(((days-datevalue)+"").equals("-0.5"))
        {
            if(radio_fan.isChecked())
            {
                radio_aan.setChecked(false);
                radio_afn.setChecked(true);

            }else
            {
                radio_afn.setChecked(false);
                radio_aan.setChecked(true);

            }

        }else
        {
            if(radio_fan.isChecked())
            {
                radio_aan.setChecked(true);
                radio_afn.setChecked(false);

            }else
            {
                radio_afn.setChecked(true);
                radio_aan.setChecked(false);

            }
        }
    }

    private void showSubmittedSuccessful() {
        Log.w(TAG, "showSubmittedSuccessful -->+");
        submittedSuccessfulalertdialog = new Dialog(GeneralActivityForm.this);
        submittedSuccessfulalertdialog.setCancelable(false);
        submittedSuccessfulalertdialog.setContentView(R.layout.alert_success);
        Button btn_goback = submittedSuccessfulalertdialog.findViewById(R.id.btn_goback);
        TextView txt_success_msg = submittedSuccessfulalertdialog.findViewById(R.id.txt_success_msg);
        TextView txt_success_msg1 = submittedSuccessfulalertdialog.findViewById(R.id.txt_success_msg1);
        txt_success_msg.setText("Leave request");
        txt_success_msg1.setText("submitted successfully");
        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submittedSuccessfulalertdialog.dismiss();
                Intent intent = new Intent(GeneralActivityForm.this, GeneralActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);
                finish();

            }
        });
        Objects.requireNonNull(submittedSuccessfulalertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        submittedSuccessfulalertdialog.show();






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}

