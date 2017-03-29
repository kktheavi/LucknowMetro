package com.kktheavi.project.lucknowmetro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kktheavi.project.lucknowmetro.Adapter.RouteListAdapter;
import com.kktheavi.project.lucknowmetro.Helper.DatabaseHandler;
import com.kktheavi.project.lucknowmetro.Helper.StationModel;

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

public class RoutesBetweenStation extends Activity implements View.OnClickListener {

    ListView route_list;
    EditText starting, destination;
    ImageView search;
    RouteListAdapter adapter;
    ImageView setting, back;
    Activity activity;
    String messageValidation;
    GetLTask task;
    ArrayList<String> station;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_routes_between_station);
        activity = this;

        db = new DatabaseHandler(this);
        route_list = (ListView) findViewById(R.id.station_route);
        starting = (EditText) findViewById(R.id.starting_station);
        destination = (EditText) findViewById(R.id.detination_station);
        search = (ImageView) findViewById(R.id.search);
        setting = (ImageView) findViewById(R.id.open_menu);
        back = (ImageView) findViewById(R.id.back);
        setting.setVisibility(View.INVISIBLE);
        station = new ArrayList<>();
        task = new GetLTask();




        Utils.SOP("---------=--" + String.valueOf(Utils.checkNetworkConnection(activity)));
        if (checkDataBase()) {
            GetData();
        } else {

            if (Utils.checkNetworkConnection(activity)) {
                task.execute("http://candywiz.com/lmrc/station_name.json");
            } else {
                Utils.showToastL(activity, "CHECK INTERNET CONNECTION");
            }
        }



        // Adding items to listview
        adapter = new RouteListAdapter(this, station);
        route_list.setAdapter(adapter);



        searchF(starting);
        searchL(destination);
        search.setOnClickListener(this);
        back.setOnClickListener(this);

        if(starting.isFocusable()){
            setData(starting);


        }
        else{
            setData(destination);

            adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
               finish();
                break;
            case R.id.search:

                if (validate(starting, destination)) {
                    Intent search_intent = new Intent(activity, ResultRoutes.class);
                    search_intent.putExtra("Starting Station", starting.getText().toString());
                    search_intent.putExtra("Destination Station", destination.getText().toString());
                    startActivity(search_intent);
                } else {
                    Toast.makeText(activity, messageValidation, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void searchF(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
//                RoutesBetweenStation.this.adapter.getFilter().filter(cs);
//                RoutesBetweenStation.this.adapter.f
//                adapter.g

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void searchL(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
//                RoutesBetweenStation.this.adapter.getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void setData(final EditText editText){
        route_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub

                editText.setText(station.get(arg2));
                Toast.makeText(activity, station.get(arg2), Toast.LENGTH_SHORT).show();
//                Log.d("############","Items " +   );
            }

        });
    }


//check validation.....

    private boolean validate(EditText editText, EditText editText1) {

        if ((editText.getText().toString().trim().isEmpty()) && (editText1.getText().toString().trim().isEmpty())) {
            messageValidation = "Please enter Station Name";
            editText.requestFocus();
            editText1.requestFocus();
            return false;
        }
        return true;
    }


/*
...............Check whether database is alredy exist or not
* */

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {

            File database = this.getDatabasePath("StationDatabase");

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
        adapter = new RouteListAdapter(this, station);
        route_list.setAdapter(adapter);


        for (StationModel cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }


    }



    //Asynctask.....
    class GetLTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        InputStream inputStream = null;
        String result = "";
        JSONArray jsonArray = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(activity);
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
                    adapter = new RouteListAdapter(RoutesBetweenStation.this, station);
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
            route_list.setAdapter(adapter);
            dialog.cancel();

            if (result == false)

                Utils.showToastL(activity, "Unable to fetch data from server");


        }

    }//End Of Asynctask================







}
