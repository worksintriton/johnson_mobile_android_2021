package com.triton.johnsonapp.activity;

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

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImage;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.FieldListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.interfaces.GetDateTimeListener;
import com.triton.johnsonapp.interfaces.GetFileUploadListener;
import com.triton.johnsonapp.interfaces.GetNumberListener;
import com.triton.johnsonapp.interfaces.GetSpinnerListener;
import com.triton.johnsonapp.interfaces.GetStringListener;
import com.triton.johnsonapp.interfaces.GetTextAreaListener;
import com.triton.johnsonapp.responsepojo.FileUploadResponse;
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

public class FieldListActivity extends AppCompatActivity implements GetStringListener, GetTextAreaListener, GetSpinnerListener, GetNumberListener, GetDateTimeListener, GetFileUploadListener {



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
    @BindView(R.id.btn_prev)
    Button btn_prev;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    Button btn_next;

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


    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    MultipartBody.Part filePart;

    ImageView img_file_upload; ImageView img_close;

    CardView cv_img;

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
        setContentView(R.layout.activity_field_list);

        ButterKnife.bind(this);

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

                currentPage += 1;
                List<GetFieldListResponse.DataBean> dataBeanListS = new ArrayList<>();
                int startItem=currentPage*ITEMS_PER_PAGE;
                int condition;
                int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
                int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
                if(currentPage==LAST_PAGE){
                    condition = startItem+ITEMS_REMAINING;
                }
                else {
                    condition = startItem+ITEMS_PER_PAGE;
                }
                for (int i=startItem;i<condition;i++)
                {
                    Log.w(TAG,"generatePage: dataBeanList" + new Gson().toJson(dataBeanList.get(i)));

                    dataBeanListS.add(dataBeanList.get(i));

                }
                setView(dataBeanListS,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
                // enableDisableButtons();
               // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));
                toggleButtons();
                Log.w(TAG,"dataBeanListN" + new Gson().toJson(dataBeanList));
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                List<GetFieldListResponse.DataBean> dataBeanListS = new ArrayList<>();
                int startItem=currentPage*ITEMS_PER_PAGE;
                int condition;
                int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
                int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
                if(currentPage==0){
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
                setView(dataBeanListS,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
               // rv.setAdapter(new MyAdapter(MainActivity.this, p.generatePage(currentPage)));

                toggleButtons();
            }
        });



    }


    private void toggleButtons() {
        if (currentPage == 0) {
            btn_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_next.setTextColor(getResources().getColor(R.color.white));
            btn_next.setEnabled(true);
            btn_prev.setEnabled(false);
            btn_prev.setBackgroundResource(R.drawable.edit_background_with_border);
            btn_prev.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (currentPage == totalPages) {
            btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_prev.setTextColor(getResources().getColor(R.color.white));
            btn_prev.setEnabled(true);
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.drawable.edit_background_with_border);
            btn_next.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (currentPage >= 1 && currentPage <= totalPages) {
            btn_next.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_next.setTextColor(getResources().getColor(R.color.white));
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
            btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
            btn_prev.setTextColor(getResources().getColor(R.color.white));
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
        rv_fieldlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_fieldlist.setItemAnimator(new DefaultItemAnimator());
        FieldListAdapter FieldListAdapter = new FieldListAdapter(getApplicationContext(), dataBeanList,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS,this,this,this,this,this,this);
        rv_fieldlist.setAdapter(FieldListAdapter);
    }

    @Override
    public void getStringListener(EditText edt_string, int position, String field_length) {

        dataBeanList.get(position).setField_update_reason(edt_string.getText().toString());

    }


    @Override
    public void getTextAreaListener(EditText edt_textarea, int position, String field_length) {

        dataBeanList.get(position).setField_update_reason(edt_textarea.getText().toString());

    }

    @Override
    public void getNumberListener(EditText edt_number, int position, String field_length) {

        dataBeanList.get(position).setField_update_reason(edt_number.getText().toString());
    }

    @Override
    public void getSpinnerListener(Spinner spr_dropdown, int position, String field_length) {

        dataBeanList.get(position).setField_update_reason(spr_dropdown.getSelectedItem().toString());

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

                chooseImage();

                img_file_upload = img_file_uploads;

                img_close = img_closes;

                imagepos = position;

                cv_img=cv_image;
            }
        });

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

            dataBeanList.get(datetimepos).setField_update_reason(edt_datetime.getText().toString());

        }
    };



    private void chooseImage() {


            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
            }


            else
            {


                CropImage.activity().start(FieldListActivity.this);

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

                        String filePath = getFilePathFromURI(FieldListActivity.this, resultUri);

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

                            filePart = MultipartBody.Part.createFormData("sampleFile", userid + currentDateandTime + filename, RequestBody.create(MediaType.parse("image/*"), file));

                            uploadImage();

                        }


                    } else {

                        Toasty.warning(FieldListActivity.this, "Image Error!!Please upload Some other image", Toasty.LENGTH_LONG).show();
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
                                Glide.with(FieldListActivity.this)
                                        .load(uploadimagepath)
                                        .into(img_file_upload);

                            }

                            dataBeanList.get(imagepos).setField_update_reason(uploadimagepath);

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

                CropImage.activity().start(FieldListActivity.this);

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


}