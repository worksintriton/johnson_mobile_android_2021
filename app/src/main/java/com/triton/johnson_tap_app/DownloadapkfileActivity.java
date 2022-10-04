package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.triton.johnsonapp.R;

public class DownloadapkfileActivity extends AppCompatActivity {

    private String TAG ="DownloadapkfileActivity";
    Button btn_download;

    DownloadManager manager;
    String apk_link,apk_version;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadapkfile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            apk_link = extras.getString("apk_link");

            Log.w(TAG,"activity_id -->"+apk_link);

        }

        if (extras != null) {
            apk_version = extras.getString("apk_version");

            Log.w(TAG,"activity_version -->"+apk_version);
        }
        //checkPermission();
       // requestPermission();
        isStoragePermissionGranted();

        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri= Uri.parse("http://smart.johnsonliftsltd.com:3000/api/uploads/tab_13_05_2022_1.apk");
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
//                        DownloadManager.Request.NETWORK_MOBILE);
//                request.setTitle("Jlsmart APK");
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"JlsmartDownload");
//                //  request.setMimeType("*/*");
//                downloadManager.enqueue(request);

                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri= Uri.parse(apk_link);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("JlsmartTab APK-" + apk_version);
                request.setDescription("JlsmartTab download using DownloadManager.");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"JlsmartTab-" + apk_version);
              //  request.setMimeType("*/*");
                downloadManager.enqueue(request);


            }
        });
    }

//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//
//        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
//    }
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
//    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}