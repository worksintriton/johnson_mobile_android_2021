package com.triton.johnsonapp.api;

import com.google.android.gms.common.internal.GetServiceRequest;
import com.triton.johnsonapp.requestpojo.ActivityListManagementRequest;
import com.triton.johnsonapp.requestpojo.FormDataStoreRequest;
import com.triton.johnsonapp.requestpojo.GetFieldListRequest;
import com.triton.johnsonapp.requestpojo.GroupDetailManagementRequest;
import com.triton.johnsonapp.requestpojo.JobNoManagementRequest;
import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.requestpojo.SubGroupDetailManagementRequest;
import com.triton.johnsonapp.responsepojo.ActivityListManagementResponse;
import com.triton.johnsonapp.responsepojo.FileUploadResponse;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.responsepojo.GroupDetailManagementResponse;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.LoginResponse;
import com.triton.johnsonapp.responsepojo.SubGroupDetailManagementResponse;

import java.io.ObjectInputStream;

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

    /*job_no_managmentt list*/
    @POST("job_no_managment/getlist_id")
    Call<JobNoManagementResponse> jobnomanagmentResponseCall(@Header("Content-Type") String type, @Body JobNoManagementRequest jobNoManagementRequest);

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


    /*Image upload*/
    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file); /*Image upload*/


    /*form store*/
    @POST("data_store_management/create")
    Call<FormDataStoreResponse> getformdataListResponseCall(@Header("Content-Type") String type, @Body FormDataStoreRequest formDataStoreRequest);

}
