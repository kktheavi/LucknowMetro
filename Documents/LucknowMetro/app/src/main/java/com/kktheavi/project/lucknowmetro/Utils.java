package com.kktheavi.project.lucknowmetro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by kktheavi on 4/4/2016.
 */
public class Utils {

    public static String PREFS_ROOT = "PREFS";


    /**
     * for checking the network and wifi state for internet connectivity.
     */
    public static boolean checkNetworkConnection(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * for printing the value.
     */
    public static void SOP(String message) {
        if (message != null) {
            System.out.println(message);
        }
    }


    /**
     * for hiding keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = activity.getCurrentFocus();
                if (v != null) {
                    IBinder binder = activity.getCurrentFocus()
                            .getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * for Short Toast
     */
    public static void showToastS(Context context, String text) {
        // Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        // t.setGravity(Gravity.CENTER, 0, 0);
        // t.show();
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * for Long Toast
     */
    public static void showToastL(Context context, String text) {
        // Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        // t.setGravity(Gravity.CENTER, 0, 0);
        // t.show();
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /*
     * Finish Activity
     */
    public static void finishactivity(Activity a) {

        try {
            if (a != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) a
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = a.getCurrentFocus();
                if (v != null) {
                    IBinder binder = a.getCurrentFocus().getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        a.finish();
        //a.overridePendingTransition(R.anim.exit_animation_enter_from_right, R.anim.exit_animation_leave_to_right);
    }



    public static String getDeviceId(Context context) {

        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return android_id;
    }

    /**
     * Create static Get Preference method
     */
    public static String getPreferenceValues(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT, context.MODE_PRIVATE);
        return prefs.getString(key, "");

    }

    public static void ClearSharedPrefence(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }

    /**
     * Create static Save Preference method
     */
    public static void savePreferenceValues(Context context, String key, String value) {

        SharedPreferences prefs = context.getSharedPreferences(PREFS_ROOT, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    /*
    Convert Data from web into String===========
    */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }//End of function=========



}
