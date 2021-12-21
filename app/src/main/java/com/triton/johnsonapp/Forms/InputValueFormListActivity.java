package com.triton.johnsonapp.Forms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.canhub.cropper.CropImage;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.SubGroupListActivity;
import com.triton.johnsonapp.adapter.FieldListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.interfaces.GetDateTimeListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadAddListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadClearListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadListener;
import com.triton.johnsonapp.interfaces.GetFileUploadListener;
import com.triton.johnsonapp.interfaces.GetNumberListener;
import com.triton.johnsonapp.interfaces.GetSpinnerListener;
import com.triton.johnsonapp.interfaces.GetStringListener;
import com.triton.johnsonapp.interfaces.GetTextAreaListener;
import com.triton.johnsonapp.requestpojo.FormDataStoreRequest;
import com.triton.johnsonapp.responsepojo.FileUploadResponse;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputValueFormListActivity extends AppCompatActivity implements GetStringListener, GetTextAreaListener, GetSpinnerListener, GetNumberListener, GetDateTimeListener, GetFileUploadListener, GetDigitalSignUploadListener, GetDigitalSignUploadAddListener, GetDigitalSignUploadClearListener {



    private String TAG ="InputValueFormListActivity";

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
    @BindView(R.id.btn_prev)
    Button btn_prev;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    Button btn_next;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_success)
    Button btn_success;

    int datetimepos,imagepos;
    private int totalPages;
    private int currentPage = 0;

    public  int TOTAL_NUM_ITEMS;
    public  int ITEMS_PER_PAGE=6;
    public  int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
    public  int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
    List<GetFieldListResponse.DataBean> dataBeanList;
    private int year, month, day;
    String Selecteddate = "";
    private String uploadimagepath = "";
    EditText edt_datetime;
    private static final int DATE_PICKER_ID = 0 ;

    int PERMISSION_CLINIC = 1;

    LinearLayoutManager linearlayout;

    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    MultipartBody.Part filePart,siganaturePart;

    String currentDateandTime;

    ImageView img_file_upload; ImageView img_close;

    CardView cv_img;

    public String digitalSignatureServerUrlImagePath;

    String string_value,message,service_id;

    int string_value_pos;

    public  List<GetFieldListResponse.DataBean> generatePage(int currentPage)
    {
        Log.w(TAG,"generatePage: currentPage " + currentPage);

        int startItem=currentPage*ITEMS_PER_PAGE+0;

        Log.w(TAG,"generatePage: startItem " + startItem);

        int numOfData=ITEMS_PER_PAGE;

        Log.w(TAG,"generatePage: numOfData " + numOfData);

        List<GetFieldListResponse.DataBean> pageData=new ArrayList<>();

        if (currentPage==LAST_PAGE && ITEMS_REMAINING>0)
        {
            for (int i=startItem;i<startItem+ITEMS_REMAINING;i++)
            {
                Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                pageData.add(dataBeanList.get(i));
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                pageData.add(dataBeanList.get(i));

            }
        }
        return pageData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_value_form_list);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            Log.w(TAG,"service_id" + service_id);

        }

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        btn_prev.setEnabled(false);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {

            getfieldListResponseCall();
        }

        //NAVIGATE
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_fieldlist.smoothScrollToPosition(0);
                linearlayout.scrollToPositionWithOffset(2, 20);
                currentPage += 1;
                List<GetFieldListResponse.DataBean> dataBeanListS = new ArrayList<>();
                int startItem=currentPage*ITEMS_PER_PAGE;
                int condition;
                int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
                int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
                if(currentPage==LAST_PAGE){

                   condition = startItem+ITEMS_REMAINING;

                    for (int i=startItem;i<condition;i++)
                    {
                        Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                        dataBeanListS.add(dataBeanList.get(i));


                    }

                    Log.w(TAG,"btnnext dataBeanList" + new Gson().toJson(dataBeanList));



                    setView(dataBeanListS,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
                    // enableDisableButtons();
                    // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));
                    btn_next.setEnabled(false);

                    toggleButtons();
                }

                else {

                        condition = startItem+ITEMS_PER_PAGE;

                    for (int i=startItem;i<condition;i++)
                    {
                        Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                        dataBeanListS.add(dataBeanList.get(i));


                    }

                    Log.w(TAG,"btnnext dataBeanList" + new Gson().toJson(dataBeanList));



                    setView(dataBeanListS,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
                    // enableDisableButtons();
                    // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));

                    Log.w(TAG,"dataBeanListN" + dataBeanList.get(1).getField_value());

                    Log.w(TAG,"dataBeanListN" + new Gson().toJson(dataBeanList));


                    toggleButtons();
                }


            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_fieldlist.smoothScrollToPosition(0);
                linearlayout.scrollToPositionWithOffset(2, 20);
                currentPage -= 1;
                List<GetFieldListResponse.DataBean> dataBeanListS = new ArrayList<>();
                int startItem=currentPage*ITEMS_PER_PAGE;
                int condition;
                int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
                int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
                if(currentPage==0||(currentPage >= 1 && currentPage <= totalPages)){
                    condition = startItem+ITEMS_PER_PAGE;
                }
                else {
                    condition = startItem+ITEMS_REMAINING;
                }
                for (int i=startItem;i<condition;i++)
                {
                    Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                    dataBeanListS.add(dataBeanList.get(i));

                }


                Log.w(TAG,"btnprev dataBeanList" + new Gson().toJson(dataBeanList));


                setView(dataBeanListS,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
               // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));

                toggleButtons();
            }
        });

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

                    Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

                }
                else {

                    getformdataListResponseCall();
                }

                Log.w(TAG,"dataBeanListN" + new Gson().toJson(dataBeanList));

            }
        });

    }


    private void toggleButtons() {
        if (currentPage == 0) {
            btn_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_next.setTextColor(getResources().getColor(R.color.white));
            btn_success.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_next.setEnabled(true);
            btn_prev.setEnabled(false);
            btn_prev.setBackgroundResource(R.drawable.edit_background_with_border);
            btn_prev.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (currentPage == totalPages) {
            btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_prev.setTextColor(getResources().getColor(R.color.white));
            btn_prev.setEnabled(true);
            btn_next.setVisibility(View.GONE);
            btn_success.setVisibility(View.VISIBLE);

        } else if (currentPage >= 1 && currentPage <= totalPages) {
            btn_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_next.setTextColor(getResources().getColor(R.color.white));
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
            btn_success.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_prev.setTextColor(getResources().getColor(R.color.white));
        }
    }




    @SuppressLint("LogNotTimber")
    public void getfieldListResponseCall(){
        dialog = new Dialog(InputValueFormListActivity.this, R.style.NewProgressDialog);
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


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"GetFieldListResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData()!= null){

                             dataBeanList = response.body().getData();

                            if(dataBeanList != null && dataBeanList.size()>0){

                               totalPages = dataBeanList.size() / 6;

                                TOTAL_NUM_ITEMS = dataBeanList.size();

                                dialog.dismiss();

                                setView(dataBeanList,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
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
    private void setView(List<GetFieldListResponse.DataBean> dataBeanList,int ITEMS_PER_PAGE,int TOTAL_NUM_ITEMS) {

        rv_fieldlist.setNestedScrollingEnabled(true);
        linearlayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv_fieldlist.setLayoutManager(linearlayout);
        rv_fieldlist.setItemAnimator(new DefaultItemAnimator());
        FieldListAdapter FieldListAdapter = new FieldListAdapter(getApplicationContext(), dataBeanList,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS,this,this,this,this,this,this,this,this,this,currentPage);
        rv_fieldlist.setAdapter(FieldListAdapter);
    }

    @Override
    public void getStringListener(EditText edt_string, String s, int position, String field_length) {


        dataBeanList.get(position).setField_value(s);

    }


    @Override
    public void getTextAreaListener(EditText edt_textarea, String s, int position, String field_length) {

        dataBeanList.get(position).setField_value(s);

    }

    @Override
    public void getNumberListener(EditText edt_number, String s, int position, String field_length) {

        dataBeanList.get(position).setField_value(s);
    }

    @Override
    public void getSpinnerListener(Spinner spr_dropdown, int positions, String sprvalue, String field_length) {

        dataBeanList.get(positions).setField_value(sprvalue);
    }


    @Override
    public void getDateTimeListener(EditText edtt_datetime, int position, String field_length) {
        edt_datetime = edtt_datetime;
        edt_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datetimepos = position;

                SelectDate();

            }
        });

    }

    @Override
    public void getFileUploadListener(LinearLayout ll_file_upload, int position, ImageView img_file_uploads, ImageView img_closes, String field_length, CardView cv_image) {

        ll_file_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_file_upload = img_file_uploads;

                img_close = img_closes;

                imagepos = position;

                cv_img=cv_image;

                chooseImage();

            }
        });

    }
    @Override
    public void getDigitalSignUploadListener(LinearLayout llheaderdigitalsignature, ImageView ivdigitalsignature, SignaturePad mSignaturePad, Button mSaveButton, Button mClearButton, int position, String field_length) {

        Log.w(TAG,"currentItem POS DS"+position);

        llheaderdigitalsignature.setVisibility(View.VISIBLE);


        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(DoctorBusinessInfoActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

    }


    @Override
    public void getDigitalSignUploadAddListener(LinearLayout llheaderdigitalsignature, ImageView ivdigitalsignature, SignaturePad mSignaturePad, Button mSaveButton, Button mClearButton, int position, String field_length) {

        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
        Log.w(TAG,"signatureBitmap"+signatureBitmap);
        // Bitmap getTransparentSignatureBitmap = mSignaturePad.getTransparentSignatureBitmap();
        // Log.w(TAG,"getTransparentSignatureBitmap"+getTransparentSignatureBitmap);



        // Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

        File file = new File(getFilesDir(), "DoctorSignature" + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            if (signatureBitmap != null) {
                signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image*/"), file);

        /* *************** Get Current Date and Time ************************ */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());



        siganaturePart = MultipartBody.Part.createFormData("sampleFile",  userid+file.getName(), requestFile);

        Log.w(TAG,"currentItem POS DS"+position);

        uploadDigitalSignatureImageRequest(llheaderdigitalsignature,mSignaturePad,ivdigitalsignature,position);

    }

    @Override
    public void getDigitalSignUploadClearListener(LinearLayout llheaderdigitalsignature, ImageView ivdigitalsignature, SignaturePad mSignaturePad, Button mSaveButton, Button mClearButton, int position, String field_length) {

        mSignaturePad.clear();

        Log.w(TAG,"currentItem POS DS"+position);

        dataBeanList.get(position).setField_value("");

    }

    private void SelectDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);

    }

    @SuppressLint("LogNotTimber")
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.w(TAG,"onCreateDialog id : "+id);
        if (id == DATE_PICKER_ID) {
            // open datepicker dialog.
            // set date picker for current date
            // add pickerListener listner to date picker
            // return new DatePickerDialog(this, pickerListener, year, month,day);
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }
    private final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @SuppressLint("LogNotTimber")
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            Selecteddate = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            edt_datetime.setText(Selecteddate);

            dataBeanList.get(datetimepos).setField_value(edt_datetime.getText().toString());

        }
    };



    private void chooseImage() {


            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
            }


            else
            {


                CropImage.activity().start(InputValueFormListActivity.this);

                /*CropImage.activity().start(AddYourPetImageOlduserActivity.this);*/
            }


    }


    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //	Toast.makeText(getActivity(),"kk",Toast.LENGTH_SHORT).show();

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUriContent();

                    if (resultUri != null) {

                        Log.w("selectedImageUri", " " + resultUri);

                        String filename = getFileName(resultUri);

                        Log.w("filename", " " + filename);

                        String filePath = getFilePathFromURI(InputValueFormListActivity.this, resultUri);

                        assert filePath != null;

                        File file = new File(filePath); // initialize file here

                        long length = file.length() / 1024; // Size in KB

                        Log.w("filesize", " " + length);

                        if (length > 2000) {

                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("File Size")
                                    .setContentText("Please choose file size less than 2 MB ")
                                    .setConfirmText("Ok")
                                    .show();
                        } else {


                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            filePart = MultipartBody.Part.createFormData("sampleFile", filename, RequestBody.create(MediaType.parse("image/*"), file));

                            uploadImage();

                        }


                    } else {

                        Toasty.warning(InputValueFormListActivity.this, "Image Error!!Please upload Some other image", Toasty.LENGTH_LONG).show();
                    }


                }
            }


        }


        catch (Exception e){
            Log.w(TAG,"onActivityResult exception"+e.toString());
        }





    }

    private void uploadImage() {

        APIInterface apiInterface = RetrofitClient.getImageClient().create(APIInterface.class);


        Call<FileUploadResponse> call = apiInterface.getImageStroeResponse(filePart);


        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<FileUploadResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {

                    if (response.body() != null) {
                        if (200 == response.body().getCode()) {
                        Log.w(TAG, "Profpic" + "--->" + new Gson().toJson(response.body()));

                   /*     DocBusInfoUploadRequest.ClinicPicBean clinicPicBean = new DocBusInfoUploadRequest.ClinicPicBean(response.body().getData().trim());
                        clinicPicBeans.add(clinicPicBean);*/
                        uploadimagepath = response.body().getData();

                        cv_img.setVisibility(View.VISIBLE);

                        img_file_upload.setVisibility(View.VISIBLE);


                            if (uploadimagepath!=null) {
                                Glide.with(InputValueFormListActivity.this)
                                        .load(uploadimagepath)
                                        .into(img_file_upload);

                            }

                            dataBeanList.get(imagepos).setField_value(uploadimagepath);

                        img_close.setVisibility(View.VISIBLE);

                        img_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dataBeanList.get(imagepos).setField_update_reason("");

                                img_file_upload.setVisibility(View.GONE);
                            }
                        });
                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                // avi_indicator.smoothToHide();
                Log.w(TAG, "ServerUrlImagePath" + "On failure working" + t.getMessage());
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            String path = context.getFilesDir() + "/" + "MyFirstApp/";

            //String path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath() + "/" + "MyFirstApp/";
            // Create the parent path
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fullName = path + "mylog";
            File copyFile = new File (fullName);

            /* File copyFile = new File(Environment.DIRECTORY_DOWNLOADS + File.separator + fileName);*/
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CLINIC) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                CropImage.activity().start(InputValueFormListActivity.this);

            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Permisson Required")
                        .setContentText("Please Allow Permissions for choosing Images from Gallery ")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {

                            sDialog.dismissWithAnimation();

                            if (!hasPermissions(this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
                            }

                        })
                        .setCancelButton("Cancel", sDialog -> {
                            sDialog.dismissWithAnimation();

                            showWarning(PERMISSION_CLINIC);
                        })
                        .show();

            }

        }

    }

    private void showWarning(int REQUEST_PERMISSION_CODE) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Sorry!!")
                .setContentText("You Can't proceed further unless you allow permission")
                .setConfirmText("Ok")
                .setConfirmClickListener(sDialog -> {

                    sDialog.dismissWithAnimation();


                    if (!hasPermissions(this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
                    }

                })
                .setCancelButton("Cancel", SweetAlertDialog::dismissWithAnimation)
                .show();
    }


    private void uploadDigitalSignatureImageRequest(LinearLayout llheaderdigitalsignature, SignaturePad mSignaturePad, ImageView ivdigitalsignature, int position) {

        APIInterface apiInterface = RetrofitClient.getImageClient().create(APIInterface.class);

        Call<FileUploadResponse> call = apiInterface.getImageStroeResponse(siganaturePart);
        Log.w(TAG,"url  :%s"+ call.request().url().toString());
        call.enqueue(new Callback<FileUploadResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {

                Log.w(TAG,"Profpic"+ "--->" + new Gson().toJson(response.body()));

                llheaderdigitalsignature.setVisibility(View.GONE);
                mSignaturePad.clear();
                Log.w(TAG,"DigitalSignaturepic"+ "--->" + new Gson().toJson(response.body()));

                // Log.w(TAG,"Profile"+ "status " + status);
                if (response.body() != null && response.body().getCode() == 200) {
                    if (response.body() != null) {
                        digitalSignatureServerUrlImagePath = response.body().getData();
                        Log.w(TAG, "digitalSignatureServerUrlImagePath " + digitalSignatureServerUrlImagePath);
                        ivdigitalsignature.setVisibility(View.VISIBLE);
                        if (digitalSignatureServerUrlImagePath != null && !digitalSignatureServerUrlImagePath.isEmpty()) {
                            dataBeanList.get(position).setField_value(digitalSignatureServerUrlImagePath);
                            Log.w(TAG,"digitalSignatureServerUrlImagePath pos--->"+position);

                            Log.w(TAG,"digitalSignatureServerUrlImagePath--->"+digitalSignatureServerUrlImagePath);

                            Glide
                                    .with(getApplicationContext())
                                    .load(digitalSignatureServerUrlImagePath)
                                    .apply(new RequestOptions().override(600, 200))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivdigitalsignature);



                        }
                        else{
                            Glide.with(getApplicationContext())
                                    .load(R.drawable.digital_signature)
                                    .into(ivdigitalsignature);

                        }



                    } else {
                        Log.w(TAG, "digitalSignatureServerUrlImagePath " + "response body null part wotking ");
                    }

                }



            }

            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                // avi_indicator.smoothToHide();
                Log.w(TAG,"DigitalSignaturepic"+ "On failure working"+ t.getMessage());
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @SuppressLint("LogNotTimber")
    private void getformdataListResponseCall() {
        dialog = new Dialog(InputValueFormListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FormDataStoreResponse> call = apiInterface.getformdataListResponseCall(RestUtils.getContentType(), FormDataStoreRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FormDataStoreResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FormDataStoreResponse> call, @NonNull Response<FormDataStoreResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            Toasty.success(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                            startActivity(new Intent(InputValueFormListActivity.this, SubGroupListActivity.class));

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


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
    private FormDataStoreRequest FormDataStoreRequest() {

        /**
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

        List<FormDataStoreRequest.DataStoreBean> dataStoreBeanList = new ArrayList<>();
        if(dataBeanList!=null&&dataBeanList.size()>0){

            for (int i=0;i<dataBeanList.size();i++){

                FormDataStoreRequest.DataStoreBean dataStoreBean = new FormDataStoreRequest.DataStoreBean();

                dataStoreBean.set__v(dataBeanList.get(i).get__v());
                dataStoreBean.set_id(dataBeanList.get(i).get_id());
                dataStoreBean.setCat_id(dataBeanList.get(i).getCat_id());
                dataStoreBean.setCreated_by(dataBeanList.get(i).getCreated_by());
                dataStoreBean.setDate_of_create(dataBeanList.get(i).getDate_of_create());
                dataStoreBean.setDate_of_update(dataBeanList.get(i).getDate_of_update());
                dataStoreBean.setField_comments(dataBeanList.get(i).getField_comments());
                dataStoreBean.setField_length(dataBeanList.get(i).getField_length());
                dataStoreBean.setField_name(dataBeanList.get(i).getField_name());
                dataStoreBean.setField_type(dataBeanList.get(i).getField_type());
                dataStoreBean.setField_update_reason(dataBeanList.get(i).getField_update_reason());
                dataStoreBean.setField_value(dataBeanList.get(i).getField_value());

                dataStoreBeanList.add(dataStoreBean);
            }

        }
        FormDataStoreRequest FormDataStoreRequest = new FormDataStoreRequest();
        FormDataStoreRequest.setUser_id(userid);
        FormDataStoreRequest.setCat_id("");
        FormDataStoreRequest.setJob_no("");
        FormDataStoreRequest.setService_id(service_id);
        FormDataStoreRequest.setData_store(dataStoreBeanList);
        FormDataStoreRequest.setStart_time("");
        FormDataStoreRequest.setPause_time("");
        FormDataStoreRequest.setStop_time("");
        FormDataStoreRequest.setStorage_status("");
        FormDataStoreRequest.setDate_of_create("");
        FormDataStoreRequest.setDate_of_update("");
        FormDataStoreRequest.setCreated_by("");
        FormDataStoreRequest.setUpdated_by("");
        FormDataStoreRequest.setUpdate_reason("");

        Log.w(TAG,"FormDataStoreRequest "+ new Gson().toJson(FormDataStoreRequest));
        return FormDataStoreRequest;
    }

    // default back button action
    public void onBackPressed() {

      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);*/

        //super.onBackPressed();

        Toasty.warning(getApplicationContext(),"This option is disabled ",Toasty.LENGTH_LONG).show();
    }

}