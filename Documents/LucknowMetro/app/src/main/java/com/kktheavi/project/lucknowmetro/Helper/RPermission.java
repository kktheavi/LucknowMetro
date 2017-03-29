package com.kktheavi.project.lucknowmetro.Helper;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by kktheavi on 1/28/2017.
 */

public class RPermission extends AppCompatActivity  {

    private static final int PERMISSION_REQUEST_CODE = 1;

   /* if (Build.VERSION.SDK_INT >= 23)
    {
        if (checkPermission())
        {
            // Code for above or equal 23 API Oriented Device
            // Your Permission granted already .Do next code
        } else {
            requestPermission(); // Code for permission
        }
    }
    else
    {

        // Code for Below 23 API Oriented Device
        // Do next code
    }*/

    private boolean checkPermission(Context c) {
        int result = ContextCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(Context c) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(RPermission.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(c, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(RPermission.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}