package com.triton.johnsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DownloadapkfileActivity extends AppCompatActivity {

    private String TAG ="DownloadapkfileActivity";
    Button btn_download;

    DownloadManager manager;
    String apk_link,apk_version;
    @Override
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
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                manager = (DownloadManager) getSystemService(DownloadapkfileActivity.this.DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse(apk_link);
//                Log.w(TAG,"APK---"+uri);
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setTitle("Jlsmart APK");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                //request.setDestinationInExternalFilesDir(DownloadapkfileActivity.this,"/JlsmartDownload", String.valueOf(uri)+ ".apk");
//                manager.enqueue(request);
//                //Log.w(TAG,"REQUEST--"+reference);
//                Toast.makeText(DownloadapkfileActivity.this,"Jlsmart download file successfully !!!",Toast.LENGTH_SHORT).show();

                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri= Uri.parse(apk_link);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Jlsmart APK -" + apk_version);
                request.setDescription("Jlsmart download using DownloadManager.");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Jlsmart -"+ apk_version);
                //  request.setMimeType("*/*");
                downloadManager.enqueue(request);

            }
        });
    }
}