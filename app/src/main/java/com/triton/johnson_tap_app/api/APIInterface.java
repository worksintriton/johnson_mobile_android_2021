package com.triton.johnson_tap_app.api;

import com.triton.johnson_tap_app.ActivityGetListNumberResponsee;
import com.triton.johnson_tap_app.BreedTypeRequest1;
import com.triton.johnson_tap_app.BreedTypeResponse1;
import com.triton.johnson_tap_app.FilterPageInfoResponse;
import com.triton.johnson_tap_app.GetFetchLatestVersionResponse;
import com.triton.johnson_tap_app.JobFindRequest;
import com.triton.johnson_tap_app.JobnoFindResponse;
import com.triton.johnson_tap_app.SubmitDailyRequest;
import com.triton.johnson_tap_app.SubmitDailyResponse;
import com.triton.johnson_tap_app.data.form3submit.Form3SubmitIP;
import com.triton.johnson_tap_app.requestpojo.ActivityGetListNumberRequest;
import com.triton.johnson_tap_app.requestpojo.AttendanceCreateRequest;
import com.triton.johnson_tap_app.requestpojo.GetFieldListRequest;
import com.triton.johnson_tap_app.requestpojo.LoginRequest;
import com.triton.johnson_tap_app.responsepojo.ActivityGetListNumberResponse;
import com.triton.johnson_tap_app.responsepojo.FileUploadResponse;
import com.triton.johnson_tap_app.responsepojo.FormDataStoreResponse;
import com.triton.johnson_tap_app.responsepojo.GetFieldListResponse;
import com.triton.johnson_tap_app.responsepojo.LoginResponse;
import com.triton.johnson_tap_app.responsepojo.SuccessResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface APIInterface {

    @GET("activity/tab_getlatest_version")
    Call<GetFetchLatestVersionResponse> getlatestversionrequestcall();

//    /*login*/
//    @POST("user_management/mobile/login_page")
//    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);

    /*login*/
    @POST("tab_usermanager/mobile/login_page")
    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);

    /*Attendance create*/
    @POST("attendance/create")
    Call<SuccessResponse> attendanceCreateRequestCall(@Header("Content-Type") String type, @Body AttendanceCreateRequest attendanceCreateRequest);

//    /*Email OTP */
    @POST("activity/form3_rtgs_jobno_find")
    Call<JobnoFindResponse> JobnoFindResponseCall(@Header("Content-Type") String type, @Body JobFindRequest emailOTPRequest);

    /*Image upload*/
    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file);

    @POST("activity/get_joins_user_list")
    Call<BreedTypeResponse1> breedTypeResponseByPetIdCall(@Header("Content-Type") String type, @Body BreedTypeRequest1 breedTypeRequest);

    @POST("activity/form3_submit")
    Call<SubmitDailyResponse> locationAddResponseCall(@Header("Content-Type") String type, @Body Form3SubmitIP locationAddRequest);

//    @GET("activity/form3_rtgs_list")
//    Call<FilterPageInfoResponse> filterPageInfoResponseCall(@Header("Content-Type") String type);

//    @GET("product_details/filter_conditions")
//    Call<FilterPageInfoResponse> filterPageInfoResponseCall(@Header("Content-Type") String type);

    @POST("activity/getlist_number")
    Call<ActivityGetListNumberResponse> activityGetListNumberResponseCall(@Header("Content-Type") String type, @Body ActivityGetListNumberRequest activityGetListNumberRequest);

    @GET("activity/form3_rtgs_list")
    Call<ActivityGetListNumberResponsee> filterPageInfoResponseCall(@Header("Content-Type") String type);
}
