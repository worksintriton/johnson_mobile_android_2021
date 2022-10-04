package com.triton.johnson_tap_app.activity;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.DrawableClickListener;
import com.triton.johnson_tap_app.ImageUploadActivity;
import com.triton.johnson_tap_app.JobFindListAdapter;
import com.triton.johnson_tap_app.JobFindListAdapter1;
import com.triton.johnson_tap_app.JobFindListAdapter2;
import com.triton.johnson_tap_app.JobFindListAdapter3;
import com.triton.johnson_tap_app.JobFindListAdapter4;
import com.triton.johnson_tap_app.JobFindListAdapter5;
import com.triton.johnson_tap_app.JobFindListAdapter6;
import com.triton.johnson_tap_app.JobFindListAdapter7;
import com.triton.johnson_tap_app.JobFindListAdapter8;
import com.triton.johnson_tap_app.JobFindListAdapter9;
import com.triton.johnson_tap_app.JobFindRequest;
import com.triton.johnson_tap_app.JobnoFindResponse;
import com.triton.johnson_tap_app.PetAppointment;
import com.triton.johnson_tap_app.PetAppointmentCreateRequest;
import com.triton.johnson_tap_app.PetBreedTypeSelectListener;
import com.triton.johnson_tap_app.PetCurrentImageListAdapter;
import com.triton.johnson_tap_app.RTGS_PopActivity;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.SubmitDailyRequest;
import com.triton.johnson_tap_app.SubmitDailyResponse;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.data.form3submit.Form3SubmitIP;
import com.triton.johnson_tap_app.data.form3submit.JobDetail;
import com.triton.johnson_tap_app.data.form3submit.UploadedFile;
import com.triton.johnson_tap_app.responsepojo.FileUploadResponse;
import com.triton.johnson_tap_app.utils.FileUtil;
import com.triton.johnsonapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class Daily_Collection_DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PetBreedTypeSelectListener {

    Spinner spinner, spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9, other, other1;
    String[] courses = {"ADVANCE", "PART", "FINAL", "AS PER TERM"};
    String[] other_item = {"SD", "L.CESS", "WITH HELD", "PENALTY"};
    ImageView iv_back;
    static EditText f_date,f_date1,f_date2,f_date3,f_date4,f_date5,f_date6,f_date7,f_date8,f_date9;
    static EditText t_date,t_date1,t_date2,t_date3,t_date4,t_date5,t_date6,t_date7,t_date8,t_date9;
    EditText date, chq_date, chq_no, rtgs_no, chq_amt, bank_name, utr_no, pay_amt, pay_amt1, pay_amt2, pay_amt3, pay_amt4, pay_amt5, pay_amt6, pay_amt7, pay_amt8, pay_amt9, pay_amt_total, agent_code, tds_it, tds_gst, remark;
    EditText job_no, job_no1, job_no2, job_no3, job_no4, job_no5, job_no6, job_no7, job_no8, job_no9, edt_other, edt_other1, contact_no, contact_no1, contact_no2, contact_no3, contact_no4, contact_no5, contact_no6, contact_no7, contact_no8, contact_no9;
    DatePickerDialog datepicker;
    String s_cust_name,s_cust_name1,s_cust_name2,s_cust_name3,s_cust_name4,s_cust_name5,s_cust_name6,s_cust_name7,s_cust_name8,s_cust_name9, s_cont_no,s_cont_no1,s_cont_no2,s_cont_no3,s_cont_no4,s_cont_no5,s_cont_no6,s_cont_no7,s_cont_no8,s_cont_no9;
    RadioGroup rg, rg1;
    RadioButton rb_chq, rb_rtgs, rb_yes, rb_no;
    LinearLayout lin_chq_no, lin_rtgs_no, lin_chq_amt, lin_utr_no, lin_chq_date;
    ArrayList<PetAppointment> PetAppointmentCreateRequestList = new ArrayList<>();
    private List<SubmitDailyResponse.DataBean.JobDetail> JobDetailsBeanList;
    private List<JobnoFindResponse.DataBean> breedTypedataBeanList;
    JobFindListAdapter petBreedTypesListAdapter;
    JobFindListAdapter1 petBreedTypesListAdapter1;
    JobFindListAdapter2 petBreedTypesListAdapter2;
    JobFindListAdapter3 petBreedTypesListAdapter3;
    JobFindListAdapter4 petBreedTypesListAdapter4;
    JobFindListAdapter5 petBreedTypesListAdapter5;
    JobFindListAdapter6 petBreedTypesListAdapter6;
    JobFindListAdapter7 petBreedTypesListAdapter7;
    JobFindListAdapter8 petBreedTypesListAdapter8;
    JobFindListAdapter9 petBreedTypesListAdapter9;
    private String PetBreedType = "";
    String check_chq;
    private String Collection_type, Current_date, Agent_code, Cheq_no, Rtgs_no, Cheq_amount, Cheq_date, Bank_name,UTR_No, Ifsc_code, Third_party_chq, Ded_it, Ded_gst, Ded_other_one_type, Ded_other_one_value, Ded_other_two_type, Ded_other_two_value, Remarks, Created_by, uploaded_file_s, Pay_Total;
    TextView name_date, name_upload, name_agent, name_chq_no, name_rtgs_no, name_chq_date, name_chq_amt, name_bank, name_party, name_urt;
    Button submit;
    String s_pay_amt = "", s_pay_amt1 = "0.0", s_pay_amt2 = "0.0", s_pay_amt3 = "0.0", s_pay_amt4 = "0.0", s_pay_amt5 = "0.0", s_pay_amt6 = "0.0", s_pay_amt7 = "0.0", s_pay_amt8 = "0.0", s_pay_amt9;
    Float n_chq_amt, n_tds_it, n_tds_gst, n_other_value, n_other_value1, n_sum, tot_sum;
   int num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, sum;
    RecyclerView rv_breedtype,rv_breedtype1,rv_breedtype2,rv_breedtype3,rv_breedtype4,rv_breedtype5,rv_breedtype6,rv_breedtype7,rv_breedtype8,rv_breedtype9;
    private Object PetAppointment;
    EditText s_no, s_no1, s_no2, s_no3, s_no4, s_no5, s_no6, s_no7, s_no8, s_no9, cust_name, cust_name1, cust_name2, cust_name3, cust_name4, cust_name5, cust_name6, cust_name7, cust_name8, cust_name9;
    String str_sno, str_sno1, str_sno2, str_sno3, str_sno4, str_sno5, str_sno6, str_sno7, str_sno8, str_sno9, str_jobno, str_jobno1, str_jobno2, str_jobno3, str_jobno4, str_jobno5, str_jobno6, str_jobno7, str_jobno8, str_jobno9;
    String str_cus_name, str_cus_name1, str_cus_name2, str_cus_name3, str_cus_name4, str_cus_name5, str_cus_name6, str_cus_name7, str_cus_name8, str_cus_name9, str_contr_no, str_contr_no1, str_contr_no2, str_contr_no3, str_contr_no4, str_contr_no5, str_contr_no6, str_contr_no7, str_contr_no8, str_contr_no9;
    String str_pay_type, str_pay_type1, str_pay_type2, str_pay_type3, str_pay_type4, str_pay_type5, str_pay_type6, str_pay_type7, str_pay_type8, str_pay_type9, str_f_date, str_f_date1, str_f_date2, str_f_date3, str_f_date4, str_f_date5, str_f_date6, str_f_date7, str_f_date8, str_f_date9;
    String str_to_date, str_to_date1, str_to_date2, str_to_date3, str_to_date4, str_to_date5, str_to_date6, str_to_date7, str_to_date8, str_to_date9;
    String ss_urtno ="", ss_bank="", ss_amt="", ss_custname, ss_ifsc="", ss_balamt, ss_radio_button="", back ="";
    AlertDialog alertDialog;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_pet_pics)
    RelativeLayout rl_pet_pics;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_upload_pet_images)
    RecyclerView rv_upload_pet_images;

    int PERMISSION_CLINIC = 1;
    int PERMISSION_CERT = 2;
    int PERMISSION_GOVT = 3;
    int PERMISSION_PHOTO = 4;
    MultipartBody.Part filePart;
    private String uploadimagepath = "";
    List<PetAppointmentCreateRequest.PetImgBean> pet_imgList = new ArrayList();
    private String userid = "";
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @SuppressLint({"SetTextI18n", "Range"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_daily_collection_details);

        ButterKnife.bind(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        spinner8 = (Spinner) findViewById(R.id.spinner8);
        spinner9 = (Spinner) findViewById(R.id.spinner9);
        rv_breedtype = (RecyclerView) findViewById(R.id.rv_breedtype);
        rv_breedtype1 = (RecyclerView) findViewById(R.id.rv_breedtype1);
        rv_breedtype2 = (RecyclerView) findViewById(R.id.rv_breedtype2);
        rv_breedtype3 = (RecyclerView) findViewById(R.id.rv_breedtype3);
        rv_breedtype4 = (RecyclerView) findViewById(R.id.rv_breedtype4);
        rv_breedtype5 = (RecyclerView) findViewById(R.id.rv_breedtype5);
        rv_breedtype6 = (RecyclerView) findViewById(R.id.rv_breedtype6);
        rv_breedtype7 = (RecyclerView) findViewById(R.id.rv_breedtype7);
        rv_breedtype8 = (RecyclerView) findViewById(R.id.rv_breedtype8);
        rv_breedtype9 = (RecyclerView) findViewById(R.id.rv_breedtype9);
        other = (Spinner) findViewById(R.id.other);
        other1 = (Spinner) findViewById(R.id.other1);
        f_date = (EditText) findViewById(R.id.f_date);
        f_date1 = (EditText) findViewById(R.id.f_date1);
        f_date2 = (EditText) findViewById(R.id.f_date2);
        f_date3 = (EditText) findViewById(R.id.f_date3);
        f_date4 = (EditText) findViewById(R.id.f_date4);
        f_date5 = (EditText) findViewById(R.id.f_date5);
        f_date6 = (EditText) findViewById(R.id.f_date6);
        f_date7 = (EditText) findViewById(R.id.f_date7);
        f_date8 = (EditText) findViewById(R.id.f_date8);
        f_date9 = (EditText) findViewById(R.id.f_date9);
        t_date = (EditText) findViewById(R.id.t_date);
        t_date1 = (EditText) findViewById(R.id.t_date1);
        t_date2 = (EditText) findViewById(R.id.t_date2);
        t_date3 = (EditText) findViewById(R.id.t_date3);
        t_date4 = (EditText) findViewById(R.id.t_date4);
        t_date5 = (EditText) findViewById(R.id.t_date5);
        t_date6 = (EditText) findViewById(R.id.t_date6);
        t_date7 = (EditText) findViewById(R.id.t_date7);
        t_date8 = (EditText) findViewById(R.id.t_date8);
        t_date9 = (EditText) findViewById(R.id.t_date9);
        date = (EditText) findViewById(R.id.date);
        chq_no = (EditText) findViewById(R.id.chq_no);
        rtgs_no = (EditText) findViewById(R.id.rtgs_no);
        chq_amt = (EditText) findViewById(R.id.chq_amt);
        bank_name = (EditText) findViewById(R.id.bank_name);
        utr_no = (EditText) findViewById(R.id.utr_no);
        chq_date = (EditText) findViewById(R.id.chq_date);
        agent_code = (EditText) findViewById(R.id.agent_code);
        remark = (EditText) findViewById(R.id.remark);
        pay_amt = (EditText) findViewById(R.id.pay_amt);
        pay_amt1 = (EditText) findViewById(R.id.pay_amt1);
        pay_amt2 = (EditText) findViewById(R.id.pay_amt2);
        pay_amt3 = (EditText) findViewById(R.id.pay_amt3);
        pay_amt4 = (EditText) findViewById(R.id.pay_amt4);
        pay_amt5 = (EditText) findViewById(R.id.pay_amt5);
        pay_amt6 = (EditText) findViewById(R.id.pay_amt6);
        pay_amt7 = (EditText) findViewById(R.id.pay_amt7);
        pay_amt8 = (EditText) findViewById(R.id.pay_amt8);
        pay_amt9 = (EditText) findViewById(R.id.pay_amt9);
        pay_amt_total = (EditText) findViewById(R.id.pay_amt_total);
        edt_other = (EditText) findViewById(R.id.edt_other);
        edt_other1 = (EditText) findViewById(R.id.edt_other1);
        tds_it = (EditText) findViewById(R.id.tds_it);
        tds_gst = (EditText) findViewById(R.id.tds_gst);
        job_no = (EditText) findViewById(R.id.job_no);
        job_no1 = (EditText) findViewById(R.id.job_no1);
        job_no2 = (EditText) findViewById(R.id.job_no2);
        job_no3 = (EditText) findViewById(R.id.job_no3);
        job_no4 = (EditText) findViewById(R.id.job_no4);
        job_no5 = (EditText) findViewById(R.id.job_no5);
        job_no6 = (EditText) findViewById(R.id.job_no6);
        job_no7 = (EditText) findViewById(R.id.job_no7);
        job_no8 = (EditText) findViewById(R.id.job_no8);
        job_no9 = (EditText) findViewById(R.id.job_no9);
        s_no = (EditText) findViewById(R.id.s_no);
        s_no1 = (EditText) findViewById(R.id.s_no1);
        s_no2 = (EditText) findViewById(R.id.s_no2);
        s_no3 = (EditText) findViewById(R.id.s_no3);
        s_no4 = (EditText) findViewById(R.id.s_no4);
        s_no5 = (EditText) findViewById(R.id.s_no5);
        s_no6 = (EditText) findViewById(R.id.s_no6);
        s_no7 = (EditText) findViewById(R.id.s_no7);
        s_no8 = (EditText) findViewById(R.id.s_no8);
        s_no9 = (EditText) findViewById(R.id.s_no9);
        cust_name = (EditText) findViewById(R.id.customer_name);
        cust_name1 = (EditText) findViewById(R.id.customer_name1);
        cust_name2 = (EditText) findViewById(R.id.customer_name2);
        cust_name3 = (EditText) findViewById(R.id.customer_name3);
        cust_name4 = (EditText) findViewById(R.id.customer_name4);
        cust_name5 = (EditText) findViewById(R.id.customer_name5);
        cust_name6 = (EditText) findViewById(R.id.customer_name6);
        cust_name7 = (EditText) findViewById(R.id.customer_name7);
        cust_name8 = (EditText) findViewById(R.id.customer_name8);
        cust_name9 = (EditText) findViewById(R.id.customer_name9);
        contact_no = (EditText) findViewById(R.id.contact_no);
        contact_no1 = (EditText) findViewById(R.id.contact_no1);
        contact_no2 = (EditText) findViewById(R.id.contact_no2);
        contact_no3 = (EditText) findViewById(R.id.contact_no3);
        contact_no4 = (EditText) findViewById(R.id.contact_no4);
        contact_no5 = (EditText) findViewById(R.id.contact_no5);
        contact_no6 = (EditText) findViewById(R.id.contact_no6);
        contact_no7 = (EditText) findViewById(R.id.contact_no7);
        contact_no8 = (EditText) findViewById(R.id.contact_no8);
        contact_no9 = (EditText) findViewById(R.id.contact_no9);
        lin_chq_no = (LinearLayout) findViewById(R.id.lin_chq_no);
        lin_rtgs_no = (LinearLayout) findViewById(R.id.lin_rtgs_no);
        lin_chq_amt = (LinearLayout) findViewById(R.id.lin_chq_amt);
        lin_utr_no = (LinearLayout) findViewById(R.id.lin_utr_no);
        lin_chq_date = (LinearLayout) findViewById(R.id.lin_chq_date);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg = (RadioGroup) findViewById(R.id.rg);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rb_chq = (RadioButton) findViewById(R.id.rb_chq);
        rb_rtgs = (RadioButton) findViewById(R.id.rb_rtgs);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
        name_date = (TextView) findViewById(R.id.name_date);
        name_upload = (TextView) findViewById(R.id.name_upload);
        name_agent = (TextView) findViewById(R.id.name_agent);
        name_chq_no = (TextView) findViewById(R.id.name_chq_no);
        name_rtgs_no = (TextView) findViewById(R.id.name_rtgs_no);
        name_chq_date = (TextView) findViewById(R.id.name_chq_date);
        name_chq_amt = (TextView) findViewById(R.id.name_chq_amt);
        name_bank = (TextView) findViewById(R.id.name_bank);
        name_party = (TextView) findViewById(R.id.name_party);
        name_urt = (TextView) findViewById(R.id.name_urt);
        submit = (Button) findViewById(R.id.submit);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("message_subject_intent"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver1,new IntentFilter("message_subject_intent1"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2,new IntentFilter("message_subject_intent2"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver3,new IntentFilter("message_subject_intent3"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver4,new IntentFilter("message_subject_intent4"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver5,new IntentFilter("message_subject_intent5"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver6,new IntentFilter("message_subject_intent6"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver7,new IntentFilter("message_subject_intent7"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver8,new IntentFilter("message_subject_intent8"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver9,new IntentFilter("message_subject_intent9"));


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ss_urtno = extras.getString("urt_no");
            utr_no.setText(ss_urtno);
        }
        if (extras != null) {
            ss_bank = extras.getString("bank_details");
        }
        if (extras != null) {
            ss_amt = extras.getString("amt");
            chq_amt.setText(ss_amt);
        }
        if (extras != null) {
            ss_custname = extras.getString("customer_name");
        }
        if (extras != null) {
            ss_ifsc = extras.getString("ifsc_code");
            bank_name.setText(ss_ifsc);
        }
        if (extras != null) {
            ss_balamt = extras.getString("balance_amt");
        }
        if (extras != null) {
            ss_radio_button = extras.getString("Radio_button");
        }

        if (extras != null) {
            back = extras.getString("Back");
        }

        if(ss_radio_button.equals("RTGS")){
            rb_rtgs.setChecked(true);
            rb_chq.setChecked(false);
        }
        else {
            rb_chq.setChecked(true);
            rb_rtgs.setChecked(false);
        }

        Spannable name_Date = new SpannableString("Date : ");
        name_Date.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_date.setText(name_Date);
        Spannable name_Date1 = new SpannableString("*");
        name_Date1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Date1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_date.append(name_Date1);

        Spannable name_Upload = new SpannableString("Upload cheque : ");
        name_Upload.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_upload.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_upload.append(name_Upload1);

        Spannable name_Agent = new SpannableString("Agent Code : ");
        name_Agent.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Agent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_agent.setText(name_Agent);
        Spannable name_Agent1 = new SpannableString("*");
        name_Agent1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Agent1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_agent.append(name_Agent1);

        Spannable name_Bank = new SpannableString("Bank Name / IFSC Code : ");
        name_Bank.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Bank.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_bank.setText(name_Bank);
        Spannable name_Bank1 = new SpannableString("*");
        name_Bank1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Bank1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_bank.append(name_Bank1);

        Spannable name_Party = new SpannableString("Third party Cheque : ");
        name_Party.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Party.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_party.setText(name_Party);
        Spannable name_Party1 = new SpannableString("*");
        name_Party1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Party1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name_party.append(name_Party1);


        SimpleDateFormat currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            currentDate = new SimpleDateFormat("dd/MM/yyyy");
        }
        Date todayDate = new Date();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String thisDate = currentDate.format(todayDate);
            date.setText(thisDate);
        }

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(ad1);

        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(ad2);

        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter ad3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(ad3);

        spinner4.setOnItemSelectedListener(this);
        ArrayAdapter ad4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(ad4);

        spinner5.setOnItemSelectedListener(this);
        ArrayAdapter ad5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(ad5);

        spinner6.setOnItemSelectedListener(this);
        ArrayAdapter ad6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(ad6);

        spinner7.setOnItemSelectedListener(this);
        ArrayAdapter ad7 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(ad7);

        spinner8.setOnItemSelectedListener(this);
        ArrayAdapter ad8 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8.setAdapter(ad8);

        spinner9.setOnItemSelectedListener(this);
        ArrayAdapter ad9 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner9.setAdapter(ad9);

        other.setOnItemSelectedListener(this);
        ArrayAdapter oth = new ArrayAdapter(this, android.R.layout.simple_spinner_item, other_item);
        oth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        other.setAdapter(oth);

        other1.setOnItemSelectedListener(this);
        ArrayAdapter oth1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, other_item);
        oth1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        other1.setAdapter(oth1);

        rl_pet_pics.setOnClickListener(v -> choosePetImage());

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent send = new Intent(Daily_Collection_DetailsActivity.this, MainActivity.class);

                startActivity(send);
            }
        });

        pay_amt.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if (s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt4.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt5.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt6.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt7.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt8.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();

                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        pay_amt9.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                s_pay_amt = pay_amt.getText().toString();
                s_pay_amt1 = pay_amt1.getText().toString();
                s_pay_amt2 = pay_amt2.getText().toString();
                s_pay_amt3 = pay_amt3.getText().toString();
                s_pay_amt4 = pay_amt4.getText().toString();
                s_pay_amt5 = pay_amt5.getText().toString();
                s_pay_amt6 = pay_amt6.getText().toString();
                s_pay_amt7 = pay_amt7.getText().toString();
                s_pay_amt8 = pay_amt8.getText().toString();
                s_pay_amt9 = pay_amt9.getText().toString();


                if(s_pay_amt.equals("")){
                    num1 = 0;
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt1.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = 0;
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

                else if(s_pay_amt2.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = 0;
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt3.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = 0;
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt4.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = 0;
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt5.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = 0;
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt6.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = 0;
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt7.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = 0;
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt8.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = 0;
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else if(s_pay_amt9.equals("")){
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = 0;
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }
                else
                {
                    num1 = Integer.parseInt(s_pay_amt);
                    num2 = Integer.parseInt(s_pay_amt1);
                    num3 = Integer.parseInt(s_pay_amt2);
                    num4 = Integer.parseInt(s_pay_amt3);
                    num5 = Integer.parseInt(s_pay_amt4);
                    num6 = Integer.parseInt(s_pay_amt5);
                    num7 = Integer.parseInt(s_pay_amt6);
                    num8 = Integer.parseInt(s_pay_amt7);
                    num9 = Integer.parseInt(s_pay_amt8);
                    num10 = Integer.parseInt(s_pay_amt9);
                    sum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10 ;
                    pay_amt_total.setText(Integer.toString(sum));
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Current_date = date.getText().toString();
                Agent_code = "AG-" + agent_code.getText().toString();
                Cheq_no = chq_no.getText().toString();
                Rtgs_no = rtgs_no.getText().toString();
                Cheq_date = chq_date.getText().toString();
                Cheq_amount = chq_amt.getText().toString();
                Bank_name = bank_name.getText().toString();
                Ded_it = tds_it.getText().toString();
                Ded_gst = tds_gst.getText().toString();
                Remarks = remark.getText().toString();
                Ded_other_one_type = other.getSelectedItem().toString();
                Ded_other_two_type = other1.getSelectedItem().toString();
                Ded_other_one_value = edt_other.getText().toString();
                Ded_other_two_value = edt_other1.getText().toString();

                // */  job details */

                str_sno = s_no.getText().toString();
                str_sno1 = s_no1.getText().toString();
                str_sno2 = s_no2.getText().toString();
                str_sno3 = s_no3.getText().toString();
                str_sno4 = s_no4.getText().toString();
                str_sno5 = s_no5.getText().toString();
                str_sno6 = s_no6.getText().toString();
                str_sno7 = s_no7.getText().toString();
                str_sno8 = s_no8.getText().toString();
                str_sno9 = s_no9.getText().toString();
                str_jobno = job_no.getText().toString();
                str_jobno1 = job_no1.getText().toString();
                str_jobno2 = job_no2.getText().toString();
                str_jobno3 = job_no3.getText().toString();
                str_jobno4 = job_no4.getText().toString();
                str_jobno5 = job_no5.getText().toString();
                str_jobno6 = job_no6.getText().toString();
                str_jobno7 = job_no7.getText().toString();
                str_jobno8 = job_no8.getText().toString();
                str_jobno9 = job_no9.getText().toString();
                str_cus_name = cust_name.getText().toString();
                str_cus_name1 = cust_name1.getText().toString();
                str_cus_name2 = cust_name2.getText().toString();
                str_cus_name3 = cust_name3.getText().toString();
                str_cus_name4 = cust_name4.getText().toString();
                str_cus_name5 = cust_name5.getText().toString();
                str_cus_name6 = cust_name6.getText().toString();
                str_cus_name7 = cust_name7.getText().toString();
                str_cus_name8 = cust_name8.getText().toString();
                str_cus_name9 = cust_name9.getText().toString();
                str_contr_no = contact_no.getText().toString();
                str_contr_no1 = contact_no1.getText().toString();
                str_contr_no2 = contact_no2.getText().toString();
                str_contr_no3 = contact_no3.getText().toString();
                str_contr_no4 = contact_no4.getText().toString();
                str_contr_no5 = contact_no5.getText().toString();
                str_contr_no6 = contact_no6.getText().toString();
                str_contr_no7 = contact_no7.getText().toString();
                str_contr_no8 = contact_no8.getText().toString();
                str_contr_no9 = contact_no9.getText().toString();
                str_pay_type = spinner.getSelectedItem().toString();
                str_pay_type1 = spinner1.getSelectedItem().toString();
                str_pay_type2 = spinner2.getSelectedItem().toString();
                str_pay_type3 = spinner3.getSelectedItem().toString();
                str_pay_type4 = spinner4.getSelectedItem().toString();
                str_pay_type5 = spinner5.getSelectedItem().toString();
                str_pay_type6 = spinner6.getSelectedItem().toString();
                str_pay_type7 = spinner7.getSelectedItem().toString();
                str_pay_type8 = spinner8.getSelectedItem().toString();
                str_pay_type9 = spinner9.getSelectedItem().toString();
                str_f_date = f_date.getText().toString();
                str_f_date1 = f_date1.getText().toString();
                str_f_date2 = f_date2.getText().toString();
                str_f_date3 = f_date3.getText().toString();
                str_f_date4 = f_date4.getText().toString();
                str_f_date5 = f_date5.getText().toString();
                str_f_date6 = f_date6.getText().toString();
                str_f_date7 = f_date7.getText().toString();
                str_f_date8 = f_date8.getText().toString();
                str_f_date9 = f_date9.getText().toString();
                str_to_date = t_date.getText().toString();
                str_to_date1 = t_date1.getText().toString();
                str_to_date2 = t_date2.getText().toString();
                str_to_date3 = t_date3.getText().toString();
                str_to_date4 = t_date4.getText().toString();
                str_to_date5 = t_date5.getText().toString();
                str_to_date6 = t_date6.getText().toString();
                str_to_date7 = t_date7.getText().toString();
                str_to_date8 = t_date8.getText().toString();
                str_to_date9 = t_date9.getText().toString();
                Pay_Total = pay_amt_total.getText().toString();

             //   locationAddResponseCall();

                Log.d("button", Collection_type + "," + Agent_code + "," + Cheq_no + "," + Rtgs_no + "," + Cheq_date + "," + Cheq_amount + "," + Bank_name + "," + Ded_it + "," + Ded_gst + "," + Remarks + "," + Ded_other_one_type + "," + Ded_other_two_type + "," + Ded_other_one_value + "," +  Ded_other_two_value + "," + Third_party_chq + "," + Pay_Total);

                if (rb_chq.isChecked()) {

                    if (Current_date.equals("") || Agent_code.equals("") || Cheq_date.equals("") || Cheq_no.equals("") || Cheq_amount.equals("") ||Bank_name.equals("")){

                        alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)

                                .setMessage("Please Fill the All Mandatory Values")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    else{

                        n_chq_amt = Float.parseFloat(Cheq_amount);
                        n_tds_it = Float.parseFloat(Ded_it);
                        n_tds_gst = Float.parseFloat(Ded_gst);
                        n_other_value = Float.parseFloat(Ded_other_one_value);
                        n_other_value1 = Float.parseFloat(Ded_other_two_value);
                        n_sum = n_chq_amt + n_tds_it + n_tds_gst + n_other_value + n_other_value1;
                        tot_sum = Float.valueOf(pay_amt_total.getText().toString());

                        if (!tot_sum.equals(n_sum)) {
                          alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)

                                    .setMessage("Amount Mismatch of total amount  with cheq amount + gst")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                           alertDialog.dismiss();
                                        }
                                    })
                                    .show();
                        } else {

                            locationAddResponseCall();
                        }

                    }

                } else {

                    if (Current_date.equals("") || Agent_code.equals("") || Rtgs_no.equals("") || Bank_name.equals("") || UTR_No.equals("")){

                        alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)

                                .setMessage("Please Fill the All Mandatory Values")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    else {
                        n_chq_amt = Float.parseFloat(Cheq_amount);
                        n_tds_it = Float.parseFloat(Ded_it);
                        n_tds_gst = Float.parseFloat(Ded_gst);
                        n_other_value = Float.parseFloat(Ded_other_one_value);
                        n_other_value1 = Float.parseFloat(Ded_other_two_value);
                        n_sum = n_chq_amt + n_tds_it + n_tds_gst + n_other_value + n_other_value1;
                        tot_sum = Float.valueOf(pay_amt_total.getText().toString());

                        if (!tot_sum.equals(n_sum)) {
                           alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)

                                    .setMessage("Amount Mismatch of total amount  with cheq amount + gst")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            alertDialog.dismiss();
                                        }
                                    })
                                    .show();
                        } else {
                            locationAddResponseCall();
                        }

                    }
                }

            }
        });
        if (rb_chq.isChecked()) {
            Collection_type = "Chq";
            utr_no.setFocusable(false);
            utr_no.setClickable(false);
            utr_no.setCursorVisible(false);
            utr_no.setText("");
            utr_no.setFocusableInTouchMode(false);
            rtgs_no.setFocusable(false);
            rtgs_no.setClickable(false);
            rtgs_no.setCursorVisible(false);
            rtgs_no.setFocusableInTouchMode(false);
            rtgs_no.setText("");
            chq_no.setFocusable(true);
            chq_no.setClickable(true);
            chq_no.setCursorVisible(true);
            chq_no.setFocusableInTouchMode(true);
            chq_date.setFocusable(false);
            chq_date.setClickable(true);
            chq_date.setCursorVisible(false);
            chq_date.setFocusableInTouchMode(false);
            chq_amt.setFocusable(true);
            chq_amt.setClickable(true);
            chq_amt.setCursorVisible(true);
            chq_amt.setFocusableInTouchMode(true);

            Spannable name_URT_no = new SpannableString("UTR number : ");
            name_URT_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_URT_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_urt.setText(name_URT_no);
            Spannable name_URT_no1 = new SpannableString("*");
            name_URT_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_URT_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_urt.append(name_URT_no1);

            Spannable name_RTGS_no = new SpannableString("RTGS No : ");
            name_RTGS_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_RTGS_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_rtgs_no.setText(name_RTGS_no);
            Spannable name_RTGS_no1 = new SpannableString("*");
            name_RTGS_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_RTGS_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_rtgs_no.append(name_RTGS_no1);

            Spannable name_Chq_no = new SpannableString("Cheque No : ");
            name_Chq_no.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_no.setText(name_Chq_no);
            Spannable name_Chq_no1 = new SpannableString("*");
            name_Chq_no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_no.append(name_Chq_no1);

            Spannable name_Chq_date = new SpannableString("Cheque Date : ");
            name_Chq_date.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_date.setText(name_Chq_date);
            Spannable name_Chq_date1 = new SpannableString("*");
            name_Chq_date1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_date1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_date.append(name_Chq_date1);

            Spannable name_Chq_amt = new SpannableString("Cheque Amount : ");
            name_Chq_amt.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_amt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_amt.setText(name_Chq_amt);
            Spannable name_Chq_amt1 = new SpannableString("*");
            name_Chq_amt1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_amt1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_amt.append(name_Chq_amt1);


        } else {
            Collection_type = "RTGS";
            chq_no.setFocusable(false);
            chq_no.setClickable(false);
            chq_no.setCursorVisible(false);
            chq_no.setFocusableInTouchMode(false);
            chq_no.setText("");
            chq_date.setFocusable(false);
            chq_date.setClickable(false);
            chq_date.setCursorVisible(false);
            chq_date.setFocusableInTouchMode(false);
            chq_date.setText("");
            chq_amt.setFocusable(false);
            chq_amt.setClickable(false);
            chq_amt.setCursorVisible(false);
            chq_amt.setFocusableInTouchMode(false);
            chq_amt.setText("");
            utr_no.setFocusable(true);
            utr_no.setClickable(true);
            utr_no.setCursorVisible(true);
            utr_no.setFocusableInTouchMode(true);
            rtgs_no.setFocusable(true);
            rtgs_no.setClickable(true);
            rtgs_no.setCursorVisible(true);
            rtgs_no.setFocusableInTouchMode(true);
            Spannable name_URT_noo = new SpannableString("UTR number : ");
            name_URT_noo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_URT_noo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_urt.setText(name_URT_noo);
            Spannable name_URT_noo1 = new SpannableString("*");
            name_URT_noo1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_URT_noo1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_urt.append(name_URT_noo1);

            Spannable name_RTGS_no = new SpannableString("RTGS No : ");
            name_RTGS_no.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_RTGS_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_rtgs_no.setText(name_RTGS_no);
            Spannable name_RTGS_no1 = new SpannableString("*");
            name_RTGS_no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_RTGS_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_rtgs_no.append(name_RTGS_no1);

            Spannable name_Chq_no = new SpannableString("Cheque No : ");
            name_Chq_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_no.setText(name_Chq_no);
            Spannable name_Chq_no1 = new SpannableString("*");
            name_Chq_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_no.append(name_Chq_no1);

            Spannable name_Chq_date = new SpannableString("Cheque Date : ");
            name_Chq_date.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_date.setText(name_Chq_date);
            Spannable name_Chq_date1 = new SpannableString("*");
            name_Chq_date1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_date1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_date.append(name_Chq_date1);

            Spannable name_Chq_amt = new SpannableString("Cheque Amount : ");
            name_Chq_amt.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_amt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_amt.setText(name_Chq_amt);
            Spannable name_Chq_amt1 = new SpannableString("*");
            name_Chq_amt1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_amt1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_chq_amt.append(name_Chq_amt1);

        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb_chq.isChecked()) {


//                    alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)
//
//                            .setMessage("Are you sure you want to change the collection type ?  If you change it will clear all the entered data.")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                   utr_no.setText("");
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    alertDialog.dismiss();
//                                    rb_rtgs.setChecked(true);
//                                }
//                            })
//                            .show();

                    Collection_type = "Chq";
                    utr_no.setFocusable(false);
                    utr_no.setClickable(false);
                    utr_no.setCursorVisible(false);
                    utr_no.setFocusableInTouchMode(false);
                    rtgs_no.setFocusable(false);
                    rtgs_no.setClickable(false);
                    rtgs_no.setCursorVisible(false);
                    rtgs_no.setFocusableInTouchMode(false);
                    chq_no.setFocusable(true);
                    chq_no.setClickable(true);
                    chq_no.setCursorVisible(true);
                    chq_no.setFocusableInTouchMode(true);
                    chq_date.setFocusable(false);
                    chq_date.setClickable(true);
                    chq_date.setCursorVisible(false);
                    chq_date.setFocusableInTouchMode(false);
                    chq_amt.setFocusable(true);
                    chq_amt.setClickable(true);
                    chq_amt.setCursorVisible(true);
                    chq_amt.setFocusableInTouchMode(true);
                    Spannable name_URT_no = new SpannableString("UTR number : ");
                    name_URT_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_URT_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_urt.setText(name_URT_no);
                    Spannable name_URT_no1 = new SpannableString("*");
                    name_URT_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_URT_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_urt.append(name_URT_no1);

                    Spannable name_RTGS_no = new SpannableString("RTGS No : ");
                    name_RTGS_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_RTGS_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_rtgs_no.setText(name_RTGS_no);
                    Spannable name_RTGS_no1 = new SpannableString("*");
                    name_RTGS_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_RTGS_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_rtgs_no.append(name_RTGS_no1);

                    Spannable name_Chq_no = new SpannableString("Cheque No : ");
                    name_Chq_no.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_no.setText(name_Chq_no);
                    Spannable name_Chq_no1 = new SpannableString("*");
                    name_Chq_no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_no.append(name_Chq_no1);

                    Spannable name_Chq_date = new SpannableString("Cheque Date : ");
                    name_Chq_date.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_date.setText(name_Chq_date);
                    Spannable name_Chq_date1 = new SpannableString("*");
                    name_Chq_date1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_date1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_date.append(name_Chq_date1);

                    Spannable name_Chq_amt = new SpannableString("Cheque Amount : ");
                    name_Chq_amt.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_Chq_amt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_amt.setText(name_Chq_amt);
                    Spannable name_Chq_amt1 = new SpannableString("*");
                    name_Chq_amt1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Chq_amt1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_amt.append(name_Chq_amt1);


                } else {

                    alertDialog = new AlertDialog.Builder(Daily_Collection_DetailsActivity.this)

                            .setMessage("Are you sure you want to change the collection type ?  If you change it will clear all the entered data!!!!!.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    chq_no.setText("");
                                    chq_date.setText("");
                                    chq_amt.setText("");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {

                                   alertDialog.dismiss();
                                   rb_chq.setChecked(true);
                                    chq_no.setFocusable(true);
                                    chq_no.setClickable(true);
                                    chq_no.setCursorVisible(true);
                                    chq_no.setFocusableInTouchMode(true);
                                    chq_date.setFocusable(false);
                                    chq_date.setClickable(true);
                                    chq_date.setCursorVisible(false);
                                    chq_date.setFocusableInTouchMode(false);
                                    chq_amt.setFocusable(true);
                                    chq_amt.setClickable(true);
                                    chq_amt.setCursorVisible(true);
                                    chq_amt.setFocusableInTouchMode(true);
                                }
                            })
                            .show();

                    Collection_type = "RTGS";
                    chq_no.setFocusable(false);
                    chq_no.setClickable(false);
                    chq_no.setCursorVisible(false);
                    chq_no.setFocusableInTouchMode(false);
                    chq_date.setFocusable(false);
                    chq_date.setClickable(false);
                    chq_date.setCursorVisible(false);
                    chq_date.setFocusableInTouchMode(false);
                    chq_amt.setFocusable(false);
                    chq_amt.setClickable(false);
                    chq_amt.setCursorVisible(false);
                    chq_amt.setFocusableInTouchMode(false);
                    utr_no.setFocusable(true);
                    utr_no.setClickable(true);
                    utr_no.setCursorVisible(true);
                    utr_no.setFocusableInTouchMode(true);
                    rtgs_no.setFocusable(true);
                    rtgs_no.setClickable(true);
                    rtgs_no.setCursorVisible(true);
                    rtgs_no.setFocusableInTouchMode(true);
                    Spannable name_URT_noo = new SpannableString("UTR number : ");
                    name_URT_noo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_URT_noo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_urt.setText(name_URT_noo);
                    Spannable name_URT_noo1 = new SpannableString("*");
                    name_URT_noo1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_URT_noo1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_urt.append(name_URT_noo1);

                    Spannable name_RTGS_no = new SpannableString("RTGS No : ");
                    name_RTGS_no.setSpan(new ForegroundColorSpan(Color.BLACK), 0, name_RTGS_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_rtgs_no.setText(name_RTGS_no);
                    Spannable name_RTGS_no1 = new SpannableString("*");
                    name_RTGS_no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_RTGS_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_rtgs_no.append(name_RTGS_no1);

                    Spannable name_Chq_no = new SpannableString("Cheque No : ");
                    name_Chq_no.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_no.setText(name_Chq_no);
                    Spannable name_Chq_no1 = new SpannableString("*");
                    name_Chq_no1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_no.append(name_Chq_no1);

                    Spannable name_Chq_date = new SpannableString("Cheque Date : ");
                    name_Chq_date.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_date.setText(name_Chq_date);
                    Spannable name_Chq_date1 = new SpannableString("*");
                    name_Chq_date1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_date1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_date.append(name_Chq_date1);

                    Spannable name_Chq_amt = new SpannableString("Cheque Amount : ");
                    name_Chq_amt.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, name_Chq_amt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_amt.setText(name_Chq_amt);
                    Spannable name_Chq_amt1 = new SpannableString("*");
                    name_Chq_amt1.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD4D4")), 0, name_Chq_amt1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name_chq_amt.append(name_Chq_amt1);

                }
            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb_yes.isChecked()) {
                    Third_party_chq = "Yes";
                    Toast.makeText(Daily_Collection_DetailsActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                } else {
                    Third_party_chq = "No";
                    Toast.makeText(Daily_Collection_DetailsActivity.this, "No", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rtgs_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (rb_chq.isChecked()) {
                        rtgs_no.setFocusable(false);
                        rtgs_no.setClickable(false);
                        rtgs_no.setCursorVisible(false);
                        rtgs_no.setFocusableInTouchMode(false);
                        // Toast.makeText(Daily_Collection_DetailsActivity.this, "No", Toast.LENGTH_SHORT).show();
                    } else {

                        rtgs_no.setFocusable(true);
                        rtgs_no.setClickable(true);
                        rtgs_no.setFocusableInTouchMode(true);
                        rtgs_no.setCursorVisible(false);
                        Intent send = new Intent(Daily_Collection_DetailsActivity.this, RTGS_PopActivity.class);
                        startActivity(send);
                    }
                }
            }
        });

        job_no.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no) {
            public boolean onDrawableClick() {

                String s_jobno = job_no.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name.setText("");
                    contact_no.setText("");
                }
                else {
                    jobFindResponseCall(s_jobno);
                }
                return true;
            }
        });

        job_no1.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no1) {
            public boolean onDrawableClick() {

                String s_jobno = job_no1.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name1.setText("");
                    contact_no1.setText("");
                }
                else {
                    jobFindResponseCall1(s_jobno);
                }
                return true;
            }
        });
        job_no2.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no2) {
            public boolean onDrawableClick() {

                String s_jobno = job_no2.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name2.setText("");
                    contact_no2.setText("");
                }
                else {
                    jobFindResponseCall2(s_jobno);
                }
                return true;
            }
        });

        job_no3.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no3) {
            public boolean onDrawableClick() {

                String s_jobno = job_no3.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name3.setText("");
                    contact_no3.setText("");
                }
                else {
                    jobFindResponseCall3(s_jobno);
                }
                return true;
            }
        });

        job_no4.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no4) {
            public boolean onDrawableClick() {

                String s_jobno = job_no4.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name4.setText("");
                    contact_no4.setText("");
                }
                else {
                    jobFindResponseCall4(s_jobno);
                }
                return true;
            }
        });
        job_no5.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no5) {
            public boolean onDrawableClick() {

                String s_jobno = job_no5.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name5.setText("");
                    contact_no5.setText("");
                }
                else {
                    jobFindResponseCall5(s_jobno);
                }
                return true;
            }
        });
        job_no6.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no6) {
            public boolean onDrawableClick() {

                String s_jobno = job_no6.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name6.setText("");
                    contact_no6.setText("");
                }
                else {
                    jobFindResponseCall6(s_jobno);
                }
                return true;
            }
        });
        job_no7.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no7) {
            public boolean onDrawableClick() {

                String s_jobno = job_no7.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name7.setText("");
                    contact_no7.setText("");
                }
                else {
                    jobFindResponseCall7(s_jobno);
                }
                return true;
            }
        });
        job_no8.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no8) {
            public boolean onDrawableClick() {

                String s_jobno = job_no8.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name8.setText("");
                    contact_no8.setText("");
                }
                else {
                    jobFindResponseCall8(s_jobno);
                }
                return true;
            }
        });
        job_no9.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(job_no9) {
            public boolean onDrawableClick() {

                String s_jobno = job_no9.getText().toString();

                if(s_jobno.equals("")) {
                    cust_name9.setText("");
                    contact_no9.setText("");
                }
                else {
                    jobFindResponseCall9(s_jobno);
                }
                return true;
            }
        });

        f_date.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date) {
            public boolean onDrawableClick() {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });


        f_date1.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date1) {
            public boolean onDrawableClick() {
                f_date1.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment1();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date2.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date2) {
            public boolean onDrawableClick() {
                f_date2.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment2();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date3.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date3) {
            public boolean onDrawableClick() {
                f_date3.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment3();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date4.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date4) {
            public boolean onDrawableClick() {
                f_date4.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment4();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date5.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date5) {
            public boolean onDrawableClick() {
                f_date5.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment5();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date6.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date6) {
            public boolean onDrawableClick() {
                f_date6.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment6();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });
        f_date7.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date7) {
            public boolean onDrawableClick() {
                f_date7.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment7();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        f_date8.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date8) {
            public boolean onDrawableClick() {
                f_date8.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment8();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });
        f_date9.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(f_date9) {
            public boolean onDrawableClick() {
                f_date9.setCursorVisible(false);
                DialogFragment newFragment = new DatePickerFragment9();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            }
        });

        t_date.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date) {
            @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
            public boolean onDrawableClick() {
                t_date.setCursorVisible(false);

                if(f_date.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {

                    DialogFragment newFragment = new ToDatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date1.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date1) {
            public boolean onDrawableClick() {
                t_date1.setCursorVisible(false);
                if(f_date1.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment1();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date2.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date2) {
            public boolean onDrawableClick() {
                t_date2.setCursorVisible(false);
                if(f_date2.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment2();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date3.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date3) {
            public boolean onDrawableClick() {
                t_date3.setCursorVisible(false);
                if(f_date3.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment3();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date4.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date4) {
            public boolean onDrawableClick() {
                t_date4.setCursorVisible(false);
                if(f_date4.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment4();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date5.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date5) {
            public boolean onDrawableClick() {
                t_date5.setCursorVisible(false);
                if(f_date5.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment5();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date6.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date6) {
            public boolean onDrawableClick() {
                t_date6.setCursorVisible(false);
                if(f_date6.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment6();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date7.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date7) {
            public boolean onDrawableClick() {
                t_date7.setCursorVisible(false);
                if(f_date7.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment7();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date8.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date8) {
            public boolean onDrawableClick() {
                t_date8.setCursorVisible(false);
                if(f_date8.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment8();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        t_date9.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(t_date9) {
            public boolean onDrawableClick() {
                t_date9.setCursorVisible(false);
                if(f_date9.getText().toString().equals("")){
                    Toast.makeText(Daily_Collection_DetailsActivity.this,"Please Selected From Date" ,Toast.LENGTH_LONG).show();
                }
                else {
                    DialogFragment newFragment = new ToDatePickerFragment9();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
                return true;
            }
        });

        chq_date.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(chq_date) {
            public boolean onDrawableClick() {
                if (rb_chq.isChecked()) {
                    // Toast.makeText(Daily_Collection_DetailsActivity.this, "No", Toast.LENGTH_SHORT).show();
                    chq_date.setCursorVisible(false);
                    chq_date.setFocusable(false);
                    chq_date.setFocusableInTouchMode(false);
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    datepicker = new DatePickerDialog(Daily_Collection_DetailsActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    chq_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                }
                            }, year, month, day);
                    datepicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datepicker.show();

                } else {
                    chq_date.setFocusable(false);
                    chq_date.setClickable(false);
                    chq_date.setCursorVisible(false);
                    chq_date.setFocusableInTouchMode(false);
                }
                return true;
            }
        });
    }

    private void jobFindResponseCall(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView(breedTypedataBeanList);

                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jobFindResponseCall1(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView1(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jobFindResponseCall2(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView2(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jobFindResponseCall3(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView3(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jobFindResponseCall4(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView4(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jobFindResponseCall5(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView5(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void jobFindResponseCall6(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView6(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void jobFindResponseCall7(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView7(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void jobFindResponseCall8(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView8(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void jobFindResponseCall9(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobnoFindResponse> call = apiInterface.JobnoFindResponseCall(RestUtils.getContentType(), JobnoFindRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobnoFindResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobnoFindResponse> call, @NonNull Response<JobnoFindResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();
                            setBreedTypeView9(breedTypedataBeanList);
                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobnoFindResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private JobFindRequest JobnoFindRequest(String job_no) {
        JobFindRequest JobnoFindRequest = new JobFindRequest();
        JobnoFindRequest.setJOBNO(job_no);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(JobnoFindRequest));
        return JobnoFindRequest;
    }

    private void setBreedTypeView(List<JobnoFindResponse.DataBean> breedTypedataBeanList) {
        rv_breedtype.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new JobFindListAdapter(getApplicationContext(), breedTypedataBeanList,this);
        rv_breedtype.setAdapter(petBreedTypesListAdapter);
    }
    private void setBreedTypeView1(List<JobnoFindResponse.DataBean> breedTypedataBeanList1) {
        rv_breedtype1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype1.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter1 = new JobFindListAdapter1(getApplicationContext(), breedTypedataBeanList1,this);
        rv_breedtype1.setAdapter(petBreedTypesListAdapter1);
    }
    private void setBreedTypeView2(List<JobnoFindResponse.DataBean> breedTypedataBeanList2) {
        rv_breedtype2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype2.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter2 = new JobFindListAdapter2(getApplicationContext(), breedTypedataBeanList2,this);
        rv_breedtype1.setAdapter(petBreedTypesListAdapter2);
    }
    private void setBreedTypeView3(List<JobnoFindResponse.DataBean> breedTypedataBeanList3) {
        rv_breedtype3.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype3.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter3 = new JobFindListAdapter3(getApplicationContext(), breedTypedataBeanList3,this);
        rv_breedtype3.setAdapter(petBreedTypesListAdapter3);
    }
    private void setBreedTypeView4(List<JobnoFindResponse.DataBean> breedTypedataBeanList4) {
        rv_breedtype4.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype4.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter4 = new JobFindListAdapter4(getApplicationContext(), breedTypedataBeanList4,this);
        rv_breedtype4.setAdapter(petBreedTypesListAdapter4);
    }
    private void setBreedTypeView5(List<JobnoFindResponse.DataBean> breedTypedataBeanList5) {
        rv_breedtype5.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype5.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter5 = new JobFindListAdapter5(getApplicationContext(), breedTypedataBeanList5,this);
        rv_breedtype5.setAdapter(petBreedTypesListAdapter5);
    }
    private void setBreedTypeView6(List<JobnoFindResponse.DataBean> breedTypedataBeanList6) {
        rv_breedtype6.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype6.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter6 = new JobFindListAdapter6(getApplicationContext(), breedTypedataBeanList6,this);
        rv_breedtype6.setAdapter(petBreedTypesListAdapter6);
    }
    private void setBreedTypeView7(List<JobnoFindResponse.DataBean> breedTypedataBeanList7) {
        rv_breedtype7.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype7.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter7 = new JobFindListAdapter7(getApplicationContext(), breedTypedataBeanList7,this);
        rv_breedtype7.setAdapter(petBreedTypesListAdapter7);
    }
    private void setBreedTypeView8(List<JobnoFindResponse.DataBean> breedTypedataBeanList8) {
        rv_breedtype8.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype8.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter8 = new JobFindListAdapter8(getApplicationContext(), breedTypedataBeanList8,this);
        rv_breedtype8.setAdapter(petBreedTypesListAdapter8);
    }
    private void setBreedTypeView9(List<JobnoFindResponse.DataBean> breedTypedataBeanList9) {
        rv_breedtype9.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_breedtype9.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter9 = new JobFindListAdapter9(getApplicationContext(), breedTypedataBeanList9,this);
        rv_breedtype9.setAdapter(petBreedTypesListAdapter9);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Toast.makeText(getApplicationContext(), courses[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = intent.getStringExtra("cust_name");
                s_cont_no = intent.getStringExtra("cont_no");
                s_cust_name1 = intent.getStringExtra("cust1");
                s_cont_no1 = intent.getStringExtra("cont_no1");
//                s_cust_name1 = intent.getStringExtra("cust1");
//                s_cont_no1 = intent.getStringExtra("cont_no1");
                cust_name.setText(s_cust_name);
                contact_no.setText(s_cont_no);
        }
    };

    public BroadcastReceiver mMessageReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cont_no = contact_no.getText().toString();
                s_cust_name1 = intent.getStringExtra("cust1");
                s_cont_no = intent.getStringExtra("cont_no1");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no);
        }
    };

    public BroadcastReceiver mMessageReceiver2 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = intent.getStringExtra("cust2");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = intent.getStringExtra("cont_no2");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
        }
    };

    public BroadcastReceiver mMessageReceiver3 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = intent.getStringExtra("cust3");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = intent.getStringExtra("cont_no3");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
        }
    };

    public BroadcastReceiver mMessageReceiver4 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = intent.getStringExtra("cust4");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = contact_no3.getText().toString();
                s_cont_no4 = intent.getStringExtra("cont_no4");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
        }
    };

    public BroadcastReceiver mMessageReceiver5 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = cust_name4.getText().toString();
                s_cust_name5 = intent.getStringExtra("cust5");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = contact_no3.getText().toString();
                s_cont_no4 = contact_no4.getText().toString();
                s_cont_no5 = intent.getStringExtra("cont_no5");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                cust_name5.setText(s_cust_name5);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
                contact_no5.setText(s_cont_no5);
        }
    };

    public BroadcastReceiver mMessageReceiver6 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = cust_name4.getText().toString();
                s_cust_name5 = cust_name5.getText().toString();
                s_cust_name6 = intent.getStringExtra("cust6");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = contact_no3.getText().toString();
                s_cont_no4 = contact_no4.getText().toString();
                s_cont_no5 = contact_no5.getText().toString();
                s_cont_no6 = intent.getStringExtra("cont_no6");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                cust_name5.setText(s_cust_name5);
                cust_name6.setText(s_cust_name6);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
                contact_no5.setText(s_cont_no5);
                contact_no6.setText(s_cont_no6);
        }
    };

    public BroadcastReceiver mMessageReceiver7 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = cust_name4.getText().toString();
                s_cust_name5 = cust_name5.getText().toString();
                s_cust_name6 = cust_name6.getText().toString();
                s_cust_name7 = intent.getStringExtra("cust7");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = contact_no3.getText().toString();
                s_cont_no4 = contact_no4.getText().toString();
                s_cont_no5 = contact_no5.getText().toString();
                s_cont_no6 = contact_no6.getText().toString();
                s_cont_no7 = intent.getStringExtra("cont_no7");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                cust_name5.setText(s_cust_name5);
                cust_name6.setText(s_cust_name6);
                cust_name7.setText(s_cust_name7);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
                contact_no5.setText(s_cont_no5);
                contact_no6.setText(s_cont_no6);
                contact_no7.setText(s_cont_no7);
        }
    };

    public BroadcastReceiver mMessageReceiver8 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = cust_name4.getText().toString();
                s_cust_name5 = cust_name5.getText().toString();
                s_cust_name6 = cust_name6.getText().toString();
                s_cust_name7 = cust_name7.getText().toString();
                s_cust_name8 = intent.getStringExtra("cust8");
                s_cust_name = contact_no.getText().toString();
                s_cust_name1 = contact_no1.getText().toString();
                s_cust_name2 = contact_no2.getText().toString();
                s_cust_name3 = contact_no3.getText().toString();
                s_cust_name4 = contact_no4.getText().toString();
                s_cust_name5 = contact_no5.getText().toString();
                s_cust_name6 = contact_no6.getText().toString();
                s_cust_name7 = contact_no7.getText().toString();
                s_cust_name8 = intent.getStringExtra("cont_no8");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                cust_name5.setText(s_cust_name5);
                cust_name6.setText(s_cust_name6);
                cust_name7.setText(s_cust_name7);
                cust_name8.setText(s_cust_name8);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
                contact_no5.setText(s_cont_no5);
                contact_no6.setText(s_cont_no6);
                contact_no7.setText(s_cont_no7);
                contact_no8.setText(s_cont_no8);
        }
    };

    public BroadcastReceiver mMessageReceiver9 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

                s_cust_name = cust_name.getText().toString();
                s_cust_name1 = cust_name1.getText().toString();
                s_cust_name2 = cust_name2.getText().toString();
                s_cust_name3 = cust_name3.getText().toString();
                s_cust_name4 = cust_name4.getText().toString();
                s_cust_name5 = cust_name5.getText().toString();
                s_cust_name6 = cust_name6.getText().toString();
                s_cust_name7 = cust_name7.getText().toString();
                s_cust_name8 = cust_name8.getText().toString();
                s_cust_name9 = intent.getStringExtra("cust9");
                s_cont_no = contact_no.getText().toString();
                s_cont_no1 = contact_no1.getText().toString();
                s_cont_no2 = contact_no2.getText().toString();
                s_cont_no3 = contact_no3.getText().toString();
                s_cont_no4 = contact_no4.getText().toString();
                s_cont_no5 = contact_no5.getText().toString();
                s_cont_no6 = contact_no6.getText().toString();
                s_cont_no7 = contact_no7.getText().toString();
                s_cont_no8 = contact_no8.getText().toString();
                s_cont_no9 = intent.getStringExtra("cont_no9");
                cust_name.setText(s_cust_name);
                cust_name1.setText(s_cust_name1);
                cust_name2.setText(s_cust_name2);
                cust_name3.setText(s_cust_name3);
                cust_name4.setText(s_cust_name4);
                cust_name5.setText(s_cust_name5);
                cust_name6.setText(s_cust_name6);
                cust_name7.setText(s_cust_name7);
                cust_name8.setText(s_cust_name8);
                cust_name9.setText(s_cust_name9);
                contact_no.setText(s_cont_no);
                contact_no1.setText(s_cont_no1);
                contact_no2.setText(s_cont_no2);
                contact_no3.setText(s_cont_no3);
                contact_no4.setText(s_cont_no4);
                contact_no5.setText(s_cont_no5);
                contact_no6.setText(s_cont_no6);
                contact_no7.setText(s_cont_no7);
                contact_no8.setText(s_cont_no8);
                contact_no9.setText(s_cont_no9);
        }
    };

    @Override
    public void petBreedTypeSelectListener(String petbreedtitle, String petbreedid) {
        PetBreedType = petbreedtitle;
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment1 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date1.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment2 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date2.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment3 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date3.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment4 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date4.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment5 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date5.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment6 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date6.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment7 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date7.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment8 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date8.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class DatePickerFragment9 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            f_date9.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

                String getfromdate = f_date.getText().toString().trim();
                String getfrom[] = getfromdate.split("/");
                int year, month, day;
                year = Integer.parseInt(getfrom[2]);
                month = Integer.parseInt(getfrom[1]);
                day = Integer.parseInt(getfrom[0]);
                final Calendar c = Calendar.getInstance();
                c.set(year, month, day + 1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                return datePickerDialog;
            }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment1 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date1.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date1.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment2 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date2.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date2.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment3 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date3.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date3.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment4 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date4.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date4.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment5 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date5.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date5.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment6 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date6.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date6.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment7 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date7.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date7.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment8 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date8.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date8.setText(day + "/" + month + "/" + year);
        }
    }

    public static class ToDatePickerFragment9 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String getfromdate = f_date9.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            t_date9.setText(day + "/" + month + "/" + year);
        }
    }

    public void locationAddResponseCall(){
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubmitDailyResponse> call = apiInterface.locationAddResponseCall(RestUtils.getContentType(),submitDailyRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SubmitDailyResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NotNull Call<SubmitDailyResponse> call, @NotNull Response<SubmitDailyResponse> response) {

                Log.w(TAG, "AddLocationResponse" + new Gson().toJson(response.body()));
                Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                if (response.body() != null) {

                    if(response.body().getCode() == 200){
                        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                        Log.w(TAG,"dddd %s"+" "+ response.body().getData().getJobDetails());

                      //  Toast.makeText(getApplicationContext(),"dddd %s :    " + response.body().getData().getJobDetails()  + "\n" + call.request().url().toString() + "\n" + new Gson().toJson(response.body()) ,Toast.LENGTH_LONG).show();


//                        if (response.body().getData() != null && response.body().getData().getJob_details() != null) {
//                            JobDetailsBeanList = response.body().getData().getJob_details();
//                        }

                        Toasty.success(getApplicationContext(),"Submitted Successfully", Toast.LENGTH_SHORT, true).show();

//                        Intent i = new Intent(AddMyAddressActivity.this, CustomerDashboardActivity.class);
//                        startActivity(i);

                    }else{
                      //  showErrorLoading(response.body().getMessage());

                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitDailyResponse> call, @NotNull Throwable t) {
                Log.w(TAG,"AddLocationResponseflr"+t.getMessage());
            }
        });

    }
    private Form3SubmitIP submitDailyRequest() {
        /*
         * user_id : 5fb36ca169f71e30a0ffd3f7
         * location_state : asdfasdfasd
         * location_country : asdfasdfasd
         * location_city : asdfasdfasd
         * location_pin : asdfasdfasd
         * location_address : asdfasdfasd
         * location_lat : 18.90123
         * location_long : 12.09123
         * location_title : 23-10-1996 12:09 AM
         * location_nickname : 123
         * default_status : true
         * date_and_time : 23-10-1996 12:09 AM
         */

       // Log.w(TAG,"AddLocationRequest--->"+"latitude"+latitude+" "+"longtitude :"+longtitude);

        Form3SubmitIP submitDailyRequest = new Form3SubmitIP();
        submitDailyRequest.setCollectionType(Collection_type);
        submitDailyRequest.setCurrentDate(Current_date);
        submitDailyRequest.setAgentCode(Agent_code);
        submitDailyRequest.setCheqNo(Cheq_no);
        submitDailyRequest.setRtgsNo(Rtgs_no);
        submitDailyRequest.setCheqAmount(Integer.valueOf(Cheq_amount));
        submitDailyRequest.setCheqDate(Cheq_date);
        submitDailyRequest.setBankName(Bank_name);
        submitDailyRequest.setIfscCode(Ifsc_code);
        submitDailyRequest.setThirdPartyChq(Third_party_chq);
        submitDailyRequest.setDedIt(Integer.valueOf(Ded_it));
        submitDailyRequest.setDedGst(Integer.valueOf(Ded_gst));
        submitDailyRequest.setDedOtherOneType(Ded_other_one_type);
        submitDailyRequest.setDedOtherOneValue(Integer.valueOf(Ded_other_one_value));
        submitDailyRequest.setDedOtherTwoType(Ded_other_two_type);
        submitDailyRequest.setDedOtherTwoValue(Integer.valueOf(Ded_other_two_value));
        submitDailyRequest.setTotal(Integer.valueOf(Pay_Total));
        submitDailyRequest.setRemarks(Remarks);
        submitDailyRequest.setCreatedBy("9874563210");
        List<UploadedFile> uploadedFile = new ArrayList<UploadedFile>();
        for(int i=0; i<pet_imgList.size();i++){
            UploadedFile t = new UploadedFile();
            t.setImage(pet_imgList.get(i).getPet_img());
            uploadedFile.add(t);
        }
        submitDailyRequest.setUploadedFile(uploadedFile);


        List<JobDetail> jobDetails = new ArrayList<JobDetail>();
//        for(int i=0; i<pet_imgList.size();i++){
            JobDetail t = new JobDetail();
            t.setsNo(Integer.valueOf(s_no.getText().toString()));
        t.setJobNo(job_no.getText().toString());
        t.setCustomerName(cust_name.getText().toString());
        t.setContractNo(contact_no.getText().toString());
        t.setPayType(spinner.getSelectedItem().toString());
        t.setFrm(f_date.getText().toString());
        t.setTo(t_date.getText().toString());
        t.setPayAmount(Integer.valueOf(pay_amt.getText().toString()));
        jobDetails.add(t);

        JobDetail s = new JobDetail();
        s.setsNo(Integer.valueOf(s_no1.getText().toString()));
        s.setJobNo(job_no1.getText().toString());
        s.setCustomerName(cust_name1.getText().toString());
        s.setContractNo(contact_no1.getText().toString());
        s.setPayType(spinner1.getSelectedItem().toString());
        s.setFrm(f_date1.getText().toString());
        s.setTo(t_date1.getText().toString());
        s.setPayAmount(Integer.valueOf(pay_amt1.getText().toString()));
        jobDetails.add(s);

        JobDetail r = new JobDetail();
        r.setsNo(Integer.valueOf(s_no2.getText().toString()));
        r.setJobNo(job_no2.getText().toString());
        r.setCustomerName(cust_name2.getText().toString());
        r.setContractNo(contact_no2.getText().toString());
        r.setPayType(spinner2.getSelectedItem().toString());
        r.setFrm(f_date2.getText().toString());
        r.setTo(t_date2.getText().toString());
        r.setPayAmount(Integer.valueOf(pay_amt2.getText().toString()));
        jobDetails.add(r);

        JobDetail a = new JobDetail();
        a.setsNo(Integer.valueOf(s_no3.getText().toString()));
        a.setJobNo(job_no3.getText().toString());
        a.setCustomerName(cust_name3.getText().toString());
        a.setContractNo(contact_no3.getText().toString());
        a.setPayType(spinner3.getSelectedItem().toString());
        a.setFrm(f_date3.getText().toString());
        a.setTo(t_date3.getText().toString());
        a.setPayAmount(Integer.valueOf(pay_amt3.getText().toString()));
        jobDetails.add(a);

        JobDetail b = new JobDetail();
        r.setsNo(Integer.valueOf(s_no4.getText().toString()));
        r.setJobNo(job_no4.getText().toString());
        r.setCustomerName(cust_name4.getText().toString());
        r.setContractNo(contact_no4.getText().toString());
        r.setPayType(spinner4.getSelectedItem().toString());
        r.setFrm(f_date4.getText().toString());
        r.setTo(t_date4.getText().toString());
        r.setPayAmount(Integer.valueOf(pay_amt4.getText().toString()));
        jobDetails.add(r);

        JobDetail c = new JobDetail();
        c.setsNo(Integer.valueOf(s_no5.getText().toString()));
        c.setJobNo(job_no5.getText().toString());
        c.setCustomerName(cust_name5.getText().toString());
        c.setContractNo(contact_no5.getText().toString());
        c.setPayType(spinner5.getSelectedItem().toString());
        c.setFrm(f_date5.getText().toString());
        c.setTo(t_date5.getText().toString());
        c.setPayAmount(Integer.valueOf(pay_amt5.getText().toString()));
        jobDetails.add(c);

        JobDetail d = new JobDetail();
        d.setsNo(Integer.valueOf(s_no6.getText().toString()));
        d.setJobNo(job_no6.getText().toString());
        d.setCustomerName(cust_name6.getText().toString());
        d.setContractNo(contact_no6.getText().toString());
        d.setPayType(spinner6.getSelectedItem().toString());
        d.setFrm(f_date6.getText().toString());
        d.setTo(t_date6.getText().toString());
        d.setPayAmount(Integer.valueOf(pay_amt6.getText().toString()));
        jobDetails.add(d);

        JobDetail e = new JobDetail();
        e.setsNo(Integer.valueOf(s_no7.getText().toString()));
        e.setJobNo(job_no7.getText().toString());
        e.setCustomerName(cust_name7.getText().toString());
        e.setContractNo(contact_no7.getText().toString());
        e.setPayType(spinner7.getSelectedItem().toString());
        e.setFrm(f_date7.getText().toString());
        e.setTo(t_date7.getText().toString());
        e.setPayAmount(Integer.valueOf(pay_amt7.getText().toString()));
        jobDetails.add(e);

        JobDetail f = new JobDetail();
        f.setsNo(Integer.valueOf(s_no8.getText().toString()));
        f.setJobNo(job_no8.getText().toString());
        f.setCustomerName(cust_name8.getText().toString());
        f.setContractNo(contact_no8.getText().toString());
        f.setPayType(spinner8.getSelectedItem().toString());
        f.setFrm(f_date8.getText().toString());
        f.setTo(t_date8.getText().toString());
        f.setPayAmount(Integer.valueOf(pay_amt8.getText().toString()));
        jobDetails.add(f);

        JobDetail g = new JobDetail();
        g.setsNo(Integer.valueOf(s_no9.getText().toString()));
        g.setJobNo(job_no9.getText().toString());
        g.setCustomerName(cust_name9.getText().toString());
        g.setContractNo(contact_no9.getText().toString());
        g.setPayType(spinner9.getSelectedItem().toString());
        g.setFrm(f_date9.getText().toString());
        g.setTo(t_date9.getText().toString());
        g.setPayAmount(Integer.valueOf(pay_amt9.getText().toString()));
        jobDetails.add(g);

        submitDailyRequest.setJobDetails(jobDetails);

        Log.w(TAG," locationAddRequest"+ new Gson().toJson(submitDailyRequest));
        return submitDailyRequest;
    }

    private void choosePetImage() {

        if (pet_imgList!=null && pet_imgList.size() >= 4) {

            Toasty.warning(getApplicationContext(), " Sorry You Can't Add More Than 4", Toast.LENGTH_SHORT).show();

        } else {

            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CLINIC);
            }

            else
            {
                CropImage.activity().start(Daily_Collection_DetailsActivity.this);
            }

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //	Toast.makeText(getActivity(),"kk",Toast.LENGTH_SHORT).show();

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = null;
                    if (result != null) {
                        resultUri = result.getUriContent();
                    }

                    if (resultUri != null) {

                        Log.w("selectedImageUri", " " + resultUri);

                        String filename = getFileName(resultUri);

                        Log.w("filename", " " + filename);

                        String filePath = getFilePathFromURI(Daily_Collection_DetailsActivity.this, resultUri);

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


                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            filePart = MultipartBody.Part.createFormData("sampleFile", userid + currentDateandTime + filename, RequestBody.create(MediaType.parse("image/*"), file));

                            uploadPetImage();

                        }


                    } else {

                        Toasty.warning(Daily_Collection_DetailsActivity.this, "Image Error!!Please upload Some other image", Toasty.LENGTH_LONG).show();
                    }


                }
            }

        }


        catch (Exception e){
            Log.w(TAG,"onActivityResult exception"+e.toString());
        }
    }

    private void uploadPetImage() {

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
                        uploadimagepath = response.body().getData();
                        PetAppointmentCreateRequest.PetImgBean petImgBean = new PetAppointmentCreateRequest.PetImgBean();
                        petImgBean.setPet_img(uploadimagepath);
                        pet_imgList.add(petImgBean);
                        if (uploadimagepath != null) {
                            setView();
                        }

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

    private void setView() {
        rv_upload_pet_images.setVisibility(View.VISIBLE);
        rv_upload_pet_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //rv_upload_pet_images.setLayoutManager(new LinearLayoutManager(this));
        rv_upload_pet_images.setItemAnimator(new DefaultItemAnimator());
        PetCurrentImageListAdapter petCurrentImageListAdapter = new PetCurrentImageListAdapter(getApplicationContext(), pet_imgList);
        rv_upload_pet_images.setAdapter(petCurrentImageListAdapter);
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
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
