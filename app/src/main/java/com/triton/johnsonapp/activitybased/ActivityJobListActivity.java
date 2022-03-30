package com.triton.johnsonapp.activitybased;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.ActivityStatusActivity;
import com.triton.johnsonapp.activity.StatusActivity;
import com.triton.johnsonapp.adapter.ABJobDetailListAdapter;
import com.triton.johnsonapp.adapter.JobDetailListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.ActivityListManagementRequest;
import com.triton.johnsonapp.requestpojo.AttendanceLogoutRequest;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
import com.triton.johnsonapp.service.GPSTracker;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityJobListActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String TAG ="ActivityJobListActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_jobdetaillist)
    RecyclerView rv_jobdetaillist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog dialog;

    String networkStatus = "",message,activity_id;

    int number=0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    private String search_string ="";
    SessionManager session;
    private String status ="";

    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job_list);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        googleApiConnected();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            status = extras.getString("status");
            Log.w(TAG,"activity_id -->"+activity_id+" status : "+status);


        }

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_string = textView.getText().toString();
                    jobnomanagmentgetlistallResponseCall();
                    return true;
                }
                return false;
            }
        });



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {

            jobnomanagmentgetlistallResponseCall();

           /* List<GetServiceListResponse.DataBean> dataBeanList = new ArrayList<>();


            for(int i=0;i<=3;i++){

                number++;
                GetServiceListResponse.DataBean dataBean = new  GetServiceListResponse.DataBean();

                Log.w(TAG,"number "+ number);

                dataBean.setService_name("Job Detail "+number);

                dataBeanList.add(dataBean);
            }


            if(dataBeanList != null && dataBeanList.size()>0){
                setView(dataBeanList);
            }*/
        }



    }


    // default back button action
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityJobListActivity.this, ActivityStatusActivity.class);
        intent.putExtra("status",status);
        intent.putExtra("activity_id",activity_id);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }


    @SuppressLint("LogNotTimber")
    private void jobnomanagmentgetlistallResponseCall() {
        dialog = new Dialog(ActivityJobListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobNoManagementResponse> call = apiInterface.jobnomanagmentgetlistallResponseCall(RestUtils.getContentType(), ActivityListManagementRequest());
        Log.w(TAG,"JobNoManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<JobNoManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobNoManagementResponse> call, @NonNull Response<JobNoManagementResponse> response) {

                Log.w(TAG,"JobNoManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            dialog.dismiss();
                            List<JobNoManagementResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                                rv_jobdetaillist.setVisibility(View.VISIBLE);
                                setView(dataBeanList);

                                txt_no_records.setVisibility(View.GONE);
                            }

                            else {
                                rv_jobdetaillist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);

                                txt_no_records.setText("No Job Detail Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<JobNoManagementResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("JobNoManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ActivityListManagementRequest ActivityListManagementRequest() {
        /*
         * user_id : 1234456789
         * search_string:""
         * request_type
         */


        ActivityListManagementRequest ActivityListManagementRequest = new ActivityListManagementRequest();
        ActivityListManagementRequest.setUser_id(userid);
        ActivityListManagementRequest.setSearch_string(search_string);
        ActivityListManagementRequest.setRequest_type(status);

        Log.w(TAG,"ActivityListManagementRequest "+ new Gson().toJson(ActivityListManagementRequest));
        return ActivityListManagementRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<JobNoManagementResponse.DataBean> dataBeanList) {
        rv_jobdetaillist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_jobdetaillist.setItemAnimator(new DefaultItemAnimator());
        ABJobDetailListAdapter abJobDetailListAdapter = new ABJobDetailListAdapter(this, dataBeanList,status,TAG);
        rv_jobdetaillist.setAdapter(abJobDetailListAdapter);
    }

    public void logOutUser(View view) {
        if(latitude != 0 && longitude != 0){
            attendanceLogoutRequestCall();
        }else{
            googleApiConnected();
        }

    }
    @SuppressLint("LogNotTimber")
    private void attendanceLogoutRequestCall() {
        dialog = new Dialog(ActivityJobListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.attendanceLogoutRequestCall(RestUtils.getContentType(), attendanceLogoutRequest());
        Log.w(TAG,"attendanceLogoutRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"attendanceLogoutRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        session.logoutUser();

                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.e("SuccessResponse flr", "--->" + t.getMessage());
            }
        });

    }
    private AttendanceLogoutRequest attendanceLogoutRequest() {

          /*{
            "_id": "621c8149a032672a740353ba",
            "attendance_end_date": "23-10-2022",
            "attendance_end_time": "23-10-2022 11:00 AM",
            "attendance_end_lat": 18.009090,
            "attendance_end_long": 79.990090

}*/


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String attendance_end_time = sdf.format(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String endDate = simpleDateFormat.format(new Date());

        AttendanceLogoutRequest attendanceLogoutRequest = new AttendanceLogoutRequest();
        attendanceLogoutRequest.set_id(userid);
        attendanceLogoutRequest.setAttendance_end_date(endDate);
        attendanceLogoutRequest.setAttendance_end_time(attendance_end_time);
        attendanceLogoutRequest.setAttendance_end_lat(latitude);
        attendanceLogoutRequest.setAttendance_end_long(longitude);
        Log.w(TAG,"attendanceLogoutRequest "+ new Gson().toJson(attendanceLogoutRequest));
        return attendanceLogoutRequest;
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                case Activity.RESULT_CANCELED:
                    getMyLocation();
                    break;
            }
        }



        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_schedule);
        if (fragment != null) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull String @org.jetbrains.annotations.NotNull [] permissions, @org.jetbrains.annotations.NotNull int @org.jetbrains.annotations.NotNull [] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                }
            } else {
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void getLatandLong() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivityJobListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                GPSTracker gps = new GPSTracker(getApplicationContext());
                // Check if GPS enabled
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if(latitude != 0 && longitude != 0){
                        LatLng latLng = new LatLng(latitude,longitude);
                        Log.w(TAG,"getLatandLong latitude : "+latitude+" longitude : "+longitude);


                    }




                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void googleApiConnected() {
        googleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getApplicationContext())).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();

    }
    private void checkLocation() {
        try {
            LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }

            if (!gps_enabled && !network_enabled) {

                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getMyLocation();
                }

            } else {
                getLatandLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();







    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissionChecking();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {


    }
    private void permissionChecking() {
        if (getApplicationContext() != null) {
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(ActivityJobListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);

            } else {

                checkLocation();
            }
        }
    }
    public void getMyLocation() {
        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                if(getApplicationContext() != null){
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                }

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(result1 -> {
                    Status status = result1.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                            Handler handler = new Handler();
                            int delay = 1000; //milliseconds

                            handler.postDelayed(new Runnable() {
                                @SuppressLint({"LongLogTag", "LogNotTimber"})
                                public void run() {
                                    //do something
                                    if(getApplicationContext() != null) {
                                        if(latitude != 0 && longitude != 0) {
                                            LatLng latLng = new LatLng(latitude,longitude);

                                            Log.w(TAG,"getMyLocation latitude : "+latitude+" longitude : "+longitude);

                                        }
                                    }
                                }
                            }, delay);


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(ActivityJobListActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
            }


        }
    }
}