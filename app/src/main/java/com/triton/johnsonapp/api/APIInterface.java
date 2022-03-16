package com.triton.johnsonapp.api;

import com.triton.johnsonapp.requestpojo.ActivityListManagementRequest;
import com.triton.johnsonapp.requestpojo.AttendanceCreateRequest;
import com.triton.johnsonapp.requestpojo.AttendanceLogoutRequest;
import com.triton.johnsonapp.requestpojo.CheckDataStoreRequest;
import com.triton.johnsonapp.requestpojo.CheckLocationRequest;
import com.triton.johnsonapp.requestpojo.FetchRecordByUserIDRequest;
import com.triton.johnsonapp.requestpojo.FormDataStoreRequest;
import com.triton.johnsonapp.requestpojo.FormFiveBasedStroeDataRequest;
import com.triton.johnsonapp.requestpojo.FormFiveDataRequest;
import com.triton.johnsonapp.requestpojo.GetFieldListRequest;
import com.triton.johnsonapp.requestpojo.GroupDetailManagementRequest;
import com.triton.johnsonapp.requestpojo.ImageBasedStroeDataRequest;
import com.triton.johnsonapp.requestpojo.JobFetchAddressRequest;
import com.triton.johnsonapp.requestpojo.JobNoManagementRequest;
import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.requestpojo.PauseJobRequest;
import com.triton.johnsonapp.requestpojo.ResumeJobRequest;
import com.triton.johnsonapp.requestpojo.RowBasedStroeDataRequest;
import com.triton.johnsonapp.requestpojo.StartWorkRequest;
import com.triton.johnsonapp.requestpojo.StopJobRequest;
import com.triton.johnsonapp.requestpojo.SubGroupDetailManagementRequest;
import com.triton.johnsonapp.responsepojo.ActivityListManagementResponse;
import com.triton.johnsonapp.responsepojo.CheckDataStoreResponse;
import com.triton.johnsonapp.responsepojo.FetchRecordByUserIDResponse;
import com.triton.johnsonapp.responsepojo.FileUploadResponse;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.FormFiveDataResponse;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.responsepojo.GroupDetailManagementResponse;
import com.triton.johnsonapp.responsepojo.JobFetchAddressResponse;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.LoginResponse;
import com.triton.johnsonapp.responsepojo.SubGroupDetailManagementResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface APIInterface {

    /*login*/
    @POST("user_management/mobile/login_page")
    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);

    /*activity list*/
    @POST("activity_list_management/getlist_id")
    Call<ActivityListManagementResponse> activityListResponseCall(@Header("Content-Type") String type, @Body ActivityListManagementRequest activityListManagementRequest);


    /*activity list*/
    @POST("activedetail_management/getlist_id")
    Call<ActivityListManagementResponse> activedetailmanagementResponseCall(@Header("Content-Type") String type, @Body ActivityListManagementRequest activityListManagementRequest);

    /*job_no_managmentt list*/
    @POST("job_no_managment/getlist_id1")
    Call<JobNoManagementResponse> jobnomanagmentResponseCall(@Header("Content-Type") String type, @Body JobNoManagementRequest jobNoManagementRequest);

    /*job_no_managmentt get list all*/
    @POST("job_no_managment/getlist_all")
    Call<JobNoManagementResponse> jobnomanagmentgetlistallResponseCall(@Header("Content-Type") String type, @Body ActivityListManagementRequest activityListManagementRequest);

    /*group_detail_managment list*/
    @POST("group_detail_managment/getlist_id")
    Call<GroupDetailManagementResponse> groupdetailmanagmentResponseCall(@Header("Content-Type") String type, @Body GroupDetailManagementRequest groupDetailManagementRequest);

    /*sub_group_detail_managment list*/
    @POST("sub_group_detail_managment/getlist_id")
    Call<SubGroupDetailManagementResponse> subgroupdetailmanagmentResponseCall(@Header("Content-Type") String type, @Body SubGroupDetailManagementRequest subGroupDetailManagementRequest);


    /*service screen list*/
    @GET("service_management/getlist")
    Call<GetServiceListResponse> getServiceListResponseCall(@Header("Content-Type") String type);

    /*field screen list*/
    @POST("field_management/getlist_id")
    Call<GetFieldListResponse> getfieldListResponseCall(@Header("Content-Type") String type, @Body GetFieldListRequest getFieldListRequest);

    /*joint inspection field screen list*/
    @POST("joininspection/getlist_id")
    Call<GetFieldListResponse> joinInspectionGetFieldListResponseCall(@Header("Content-Type") String type, @Body GetFieldListRequest getFieldListRequest);


    /*Image upload*/
    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file); /*Image upload*/


    /*form store*/
    @POST("data_store_management/create")
    Call<FormDataStoreResponse> getformdataListResponseCall(@Header("Content-Type") String type, @Body FormDataStoreRequest formDataStoreRequest); /*form store*/

    /*form store*/
    @POST("data_store_management/create")
    Call<FormDataStoreResponse> getformdataListResponseCall1(@Header("Content-Type") String type, @Body GetFieldListResponse getFieldListResponse);

    /*join inspection form store*/
    @POST("joininspection/create")
    Call<FormDataStoreResponse> joinInspectionCreateRequestCall(@Header("Content-Type") String type, @Body GetFieldListResponse getFieldListResponse);


    /*ImageBased form store*/
    @POST("data_store_management/create")
    Call<FormDataStoreResponse> imageBasedStroeDataRequestCall(@Header("Content-Type") String type, @Body ImageBasedStroeDataRequest imageBasedStroeDataRequest);


    /*Row Based form store*/
    @POST("data_store_management/create")
    Call<FormDataStoreResponse> rowBasedStroeDataRequestCall(@Header("Content-Type") String type, @Body RowBasedStroeDataRequest rowBasedStroeDataRequest);


    /*form store*/
    @POST("data_store_management/fetch_record_byuserid")
    Call<FetchRecordByUserIDResponse> fetch_record_byuseridResponseCall(@Header("Content-Type") String type, @Body FetchRecordByUserIDRequest fetchRecordByUserIDRequest);


    /*Check Data Store*/
    @POST("data_store_management/check_data_store")
    Call<CheckDataStoreResponse> checkDataStoreResponseCall(@Header("Content-Type") String type, @Body CheckDataStoreRequest checkDataStoreRequest);

   /*Stark work request*/
    @POST("data_store_management/start_work")
    Call<SuccessResponse> startWorkRequestCall(@Header("Content-Type") String type, @Body StartWorkRequest starkWorkRequest);


   /*Pause job request*/
    @POST("data_store_management/pause_job")
    Call<SuccessResponse> pausejobRequestCall(@Header("Content-Type") String type, @Body PauseJobRequest pauseJobRequest);


    /*Resume job request*/
    @POST("data_store_management/resume_job")
    Call<SuccessResponse> resumejobRequestCall(@Header("Content-Type") String type, @Body ResumeJobRequest resumeJobRequest);


    /*Stop job request*/
    @POST("data_store_management/stop_job")
    Call<SuccessResponse> stopjobRequestCall(@Header("Content-Type") String type, @Body StopJobRequest stopJobRequest);

    /*Stop job request*/
    @POST("field_management/getlist_datas")
    Call<FormFiveDataResponse> formFiveDataResponseCall(@Header("Content-Type") String type, @Body FormFiveDataRequest formFiveDataRequest);


    /*form  five store*/
    @POST("data_store_management/form_5_create")
    Call<SuccessResponse> formFiveStroeDataRequestCall(@Header("Content-Type") String type, @Body FormFiveDataResponse formFiveDataResponse);



    /*Attendance create*/
    @POST("attendance/create")
    Call<SuccessResponse> attendanceCreateRequestCall(@Header("Content-Type") String type, @Body AttendanceCreateRequest attendanceCreateRequest);

    /*Attendance logout */
    @POST("attendance/logout")
    Call<SuccessResponse> attendanceLogoutRequestCall(@Header("Content-Type") String type, @Body AttendanceLogoutRequest attendanceLogoutRequest);


    /*Attendance logout */
    @POST("data_store_management/check_location")
    Call<SuccessResponse> checkLocationRequestCall(@Header("Content-Type") String type, @Body CheckLocationRequest checkLocationRequest);

    /*fetch address */
    @POST("job_no_managment/fetch_address")
    Call<JobFetchAddressResponse> JobFetchAddressRequestCall(@Header("Content-Type") String type, @Body JobFetchAddressRequest jobFetchAddressRequest);





}
