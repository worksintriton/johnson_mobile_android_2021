
package com.triton.johnsonapp.interfaces;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.gcacace.signaturepad.views.SignaturePad;

public interface GetDigitalSignUploadAddListener {
    void getDigitalSignUploadAddListener(LinearLayout llheaderdigitalsignature, ImageView ivdigitalsignature, SignaturePad mSignaturePad, Button mSaveButton, Button mClearButton, int position, String field_length);
}
