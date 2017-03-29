package com.kktheavi.project.lucknowmetro.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.kktheavi.project.lucknowmetro.Adapter.StationListAdapter;
import com.kktheavi.project.lucknowmetro.Helper.DatabaseHandler;
import com.kktheavi.project.lucknowmetro.Helper.StationModel;
import com.kktheavi.project.lucknowmetro.R;
import com.kktheavi.project.lucknowmetro.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by kktheavi on 10/2/2016.
 */

public class StationListFragment extends android.app.Fragment {


    ListView station_list;
    StationListAdapter adapter;
    Activity mactivity;
    ArrayList<String> station;

    GetTask task;
    DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.station_fragment, container, false);

        db = new DatabaseHandler(getActivity());
        station_list = (ListView) view.findViewById(R.id.station_list);
        station = new ArrayList<>();
        task = new GetTask();
        return view;
    }


/*
...............Check whether database is alredy exist or not
* */

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {

            File database = mactivity.getDatabasePath("StationDatabase");

            if (database.exists()) {

                Log.i("Database", "Found");

                String myPath = database.getAbsolutePath();

                Log.i("Database Path", myPath);

                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            } else {

                // Database does not exist so copy it from assets here
                Log.i("Database", "Not Found");

            }

        } catch (SQLiteException e) {

            Log.i("Database", "Not Found");

        } finally {

            if (checkDB != null) {

                checkDB.close();

            }

        }

        return checkDB != null ? true : false;
    }//End of check database exist method


    public void GetData() {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        ArrayList<StationModel> contacts = db.getAllContacts();
        station.clear();
        Utils.SOP("======" + String.valueOf(contacts.size()));
        for (int i = 0; i < contacts.size(); i++) {
            station.add(i, contacts.get(i).getName().toString());
        }
        adapter = new StationListAdapter(getActivity(), station);
        station_list.setAdapter(adapter);


        for (StationModel cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mactivity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();

        Utils.SOP("---------=--" + String.valueOf(Utils.checkNetworkConnection(mactivity)));
        if (checkDataBase()) {
            GetData();
        } else {

            if (Utils.checkNetworkConnection(mactivity)) {
                task.execute("http://candywiz.com/lmrc/station_name.json");
            } else {
                Utils.showToastL(mactivity, "CHECK INTERNET CONNECTION");
            }
        }
    }

    /*
........GetTask AsyncTask===============
*/

    class GetTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        InputStream inputStream = null;
        String result = "";
        JSONArray jsonArray = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mactivity);
            dialog.setMessage("Loading, please wait");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpPost httpPost = new HttpPost(urls[0]);
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection = 3000;
                /*Set the timeout in milliseconds
                until a connection is established.
                The default value is zero, that means the timeout is not used.*/
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                /* Set the default socket timeout (SO_TIMEOUT)
                in milliseconds which is the timeout for waiting for data.*/
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpResponse httpResponse = httpclient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    result = Utils.convertInputStreamToString(inputStream);
                    Utils.SOP("resuCountry======" + result);
                } else {
                    Utils.SOP("Did not working");
                }


                jsonArray = new JSONArray(result);
                Utils.SOP("=====================jsonArray.length()" + jsonArray.length());
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        station.add(jsonArray.getJSONObject(i).getString("name"));
                    }


                    for (int i = 0; i < station.size(); i++) {
                        db.addContact(new StationModel(i, station.get(i)));       //........Creating Database


                    }
                    adapter = new StationListAdapter(mactivity, station);


                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        protected void onPostExecute(Boolean result) {
            station_list.setAdapter(adapter);
            dialog.cancel();

            if (result == false)

                Utils.showToastL(mactivity, "Unable to fetch data from server");


        }

    }//End Of Asynctask================


}