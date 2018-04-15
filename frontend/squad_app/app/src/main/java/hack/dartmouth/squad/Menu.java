package hack.dartmouth.squad;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.text.ParseException;
import java.util.ArrayList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity implements Statistics.OnResponsePass {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<Data> dataList;
    static String fileName;
    Storage storage;
    double totalDist = 0;
    double max_speed = 0;
    static SharedPreferences sp;
    private int index;
    int mSteps;
    BroadcastReceiver receiver;
    public String server_url = "http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/";



    JSONObject response;
    static JSONObject mJsonResponse;
    RequestQueue queue;

    JSONObject api_data;



    public JSONObject JsonResponseLogin(String username, String password) {
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("username", username);
            jsonResponse.put("password", password);
        } catch(JSONException e) {
            Log.d("JSON", e.toString());
        }
        return jsonResponse;
    }

    public void JsonRequestLogin(String username, String password) {
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            server_url + "user/login/",
            JsonResponseLogin(username, password),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("error")) {
                        Log.d("error", response.toString());
                    } else {
                        TextView tv_login = findViewById(R.id.tv_login);
                        tv_login.setText(response.toString());
                        if (response == null) {
                            tv_login.setText("Connection failed");
                        }
                        else
                            tv_login.setText(response.toString());
                            try {
                                api_data.put("login", response);


                            } catch(JSONException e) {
                                Log.d("debug", "could not set api_data to response: " + response.toString());
                            }


                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
        );
        queue.add(request);
    }
    public JSONObject JsonResponseSignup(String name, String username, String password) {
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("name", username);
            jsonResponse.put("username", username);
            jsonResponse.put("password", password);
        } catch(JSONException e) {
            Log.d("JSON", e.toString());
        }
        return jsonResponse;
    }

    public void JsonRequestSignup(String name, String username, String password) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                server_url + "user/create/",
                JsonResponseSignup(name, username, password),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("error")) {
                            Log.d("error", response.toString());
                        } else {
                            TextView tv_login = findViewById(R.id.tv_login);
                            tv_login.setText(response.toString());
                            if (response == null) {
                                tv_login.setText("Connection failed");
                            }
                            else
                                tv_login.setText(response.toString());

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        queue.add(request);
    }

    public void makeRequest() {
        // "http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/trip/"
        String url = "http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/user/login/";
        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject("{ \'username\':\'spongy\', \'password\':\'squarepants\' }");
            Log.d("JSON", "jsonRequest: " + jsonRequest.toString());
        } catch (JSONException e) {
            Log.d("debug", "JSON e: " + e);
            jsonRequest = null;
        }
        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("error")) {
                            Log.d("error", response.toString());

                        } else {
                            mJsonResponse = response;
                            Log.d("JSON", response.toString());
                            TextView tv_login = findViewById(R.id.tv_login);
                            tv_login.setText(response.toString());
                            if (response == null) {
                                tv_login.setText("Connection failed");
                            }
                            else
                                tv_login.setText(response.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", ("Error" + error.toString()));
            }
        });

        // Add the request to the queue
        queue.add(stringRequest);
    }

    @Override
    public void onResponsePass(JSONObject response) {
        Log.d("debug", "JSON response passed: " + response.toString());
        this.response = response;
    }

    //Set a navigation view listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    index = 0;
                    fm.beginTransaction().replace(R.id.frame_content, fragments.get(0)).commit();
                    return true;
                case R.id.navigation_dashboard:
                    index = 1;
                    fm.beginTransaction().replace(R.id.frame_content, fragments.get(1)).commit();
                    return true;
                case R.id.navigation_notifications:
                    index = 2;
                    fm.beginTransaction().replace(R.id.frame_content, fragments.get(2)).commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeTabs();
        index = 0;
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.round_icon);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        storage = new Storage(this);

        updateData(fileName);
        if (fileName != null){
            Log.d("fileName", fileName);
        }
        receiveBroadcast();
        checkPermissions();

        queue = Volley.newRequestQueue(this);

        api_data = initializeApi();
    }

    private JSONObject initializeApi() {
        JSONObject api_data = new JSONObject();
        try {
            api_data.put("login", "null");
//            api_data.put("authToken", "null");
//            api_data.put("tId", "null");
        } catch(JSONException e) {
            Log.d("JSON", e.toString());
        }
        return api_data;

    }

    // when you pause the view, save the filename and the index of the navigation view you are currently on
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fileName", fileName);
        outState.putInt("index", index);
    }

    // restore tab and file
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileName = savedInstanceState.getString("fileName");
        index = savedInstanceState.getInt("index");
        initializeTabs();
//        Log.d("index", String.valueOf(index));
        if (fileName == null || fileName.length() == 0) return;
        updateData(fileName);
    }

    // update the data in accordance to the selected file
    public void updateData(String file){
        dataList = storage.readFile(file);
        Log.d("Data", dataList.toString());
        if (dataList != null){

            for (Data datum : dataList){
                Log.d("user", datum.getUser());
                Log.d("lat", datum.getLat());
                Log.d("lng", datum.getLng());
            }
        }

        else{
            Log.d("Error", "File Not Found");
            Toast.makeText(this, "No file found for the selected date", Toast.LENGTH_SHORT).show();
        }

        initializeTabs();
    }

    // listen to whether a service has been destroyed or not
    // listen to the file update, and update the view accordingly
    protected void receiveBroadcast(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("debug", "broadcast received");
                if (intent.getAction() == "STOP SERVICE")
                    invalidateOptionsMenu();
                else if (fileName != null) {
                    Toast.makeText(context, "Updating", Toast.LENGTH_SHORT).show();
                    updateData(fileName);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("STOP SERVICE");
        intentFilter.addAction("UPDATE");
        registerReceiver(receiver, intentFilter);
    }


    // initialize the tabs in the beginning
    public void initializeTabs(){
        fragments.clear();
        Fragment home = new Home();
        Fragment stats = new Statistics();
        Fragment map = new Map();

        fragments.add(home);
        fragments.add(stats);
        fragments.add(map);

        Log.d("index", String.valueOf(index));

        if(findViewById(R.id.frame_content) != null)
            getFragmentManager().beginTransaction().replace(R.id.frame_content, fragments.get(index)).commit();
    }

//    // search for time stamp
//    public void search(View view){
//        ((Map)fragments.get(2)).showQuery(view);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_start:
//                if (!sp.getBoolean("track", false)) {
//                    startForeground();
//                    sp.edit().putBoolean("track", true).apply();
//                } else {
//                    stopForeground();
//                    sp.edit().putBoolean("track", false).apply();
//                }
//                break;
//            case R.id.action_tutorial:
//                sp.edit().putBoolean("first", true).apply();
//                break;
//            case R.id.action_refresh:
//                if(fileName != null) updateData(fileName);
//                break;
//            case R.id.delete:
//                deleteFile();
//                break;
//            default:
//                setGPS();
//        }
//
//        return true;
//    }


    // delete file for the selected date
    public void deleteFile(){
        if (dataList == null){
            Toast.makeText(this, "There is no file for the selected date", Toast.LENGTH_SHORT).show();
        } else {
            this.deleteFile(fileName);
            dataList = null;
        }
    }

    // set gps frequeny, show the list view dialog
    public void setGPS(){
        FragmentManager fm = getFragmentManager();
        GpsDialog gpsDialog = new GpsDialog();
        gpsDialog.show(fm, "gps");
    }

    private void checkPermissions() {
        if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 0);
        }
    }

    // start the tracking service
    public void startForeground(){
        Intent foreground = new Intent(this, ForegroundService.class);
        foreground.setAction("START");
        startService(foreground);
        Toast.makeText(this, "I am keeping an eye on you", Toast.LENGTH_SHORT).show();
    }

    // stop the tracking service
    public void stopForeground(){
        Intent foreground = new Intent(this, ForegroundService.class);
        stopService(foreground);
        Toast.makeText(this, "Ok, I will stop stalking", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    // GPS frequency selector using a list view displayed via dialog fragment
    public static class GpsDialog extends DialogFragment {
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            //---Inflate the layout for this fragment---
            Log.d("Dialog", "onCreateView");
            View v = inflater.inflate(
                    R.layout.gps_dialog, container, false);
            final ListView freq = v.findViewById(R.id.list);
            final Fragment parent = this;
            freq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            sp.edit().putInt("gps_freq", 30000).apply();
                            break;
                        case 1:
                            sp.edit().putInt("gps_freq", 60000).apply();
                            break;
                        case 2:
                            sp.edit().putInt("gps_freq", 300000).apply();
                            break;
                        case 3:
                            sp.edit().putInt("gps_freq", 180000).apply();
                            break;
                        case 4:
                            sp.edit().putInt("gps_freq", 3600000).apply();
                            break;
                    }
                    getFragmentManager().beginTransaction().remove(parent).commit();
                }
            });
            return v;
        }
    }
}
