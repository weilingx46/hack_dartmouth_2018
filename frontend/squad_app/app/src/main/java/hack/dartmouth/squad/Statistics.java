package hack.dartmouth.squad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Statistics extends Fragment {

    Menu curActivity;
    RequestQueue queue;
    private Activity context;
    JSONObject jsonResponse;
    OnResponsePass responsePasser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        curActivity = (Menu) getActivity();


        // get Menu's response variable and display it
        TextView testArea = view.findViewById(R.id.tvResponse);
        if (((Menu) getActivity()).response != null) {
            testArea.setText(((Menu) getActivity()).response.toString());
        }

        Button testButton = view.findViewById(R.id.button_test);
        testButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // "http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/trip/"
                String url = "http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/trip/";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    jsonResponse = new JSONObject(response);
                                    if (jsonResponse.has("error")) {
                                        showResOnUI(jsonResponse.get("error").toString());
                                    } else {
                                        showResOnUI(jsonResponse.toString());
                                        responsePasser.onResponsePass(jsonResponse);

                                    }
                                } catch (JSONException e) {
                                    Log.d("JSON", e.toString());
                                    showResOnUI(e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showResOnUI("Error" + error.toString());
                    }
                });

                // Add the request to the queue
                queue.add(stringRequest);
            }
        });
        // https://stackoverflow.com/questions/10984729/dynamically-adding-textview-to-scrollview-on-android
        try {
            LinearLayout linearlayout = view.findViewById(R.id.scrollview_linearlayout);

            TextView[] tx = new TextView[4];
            if (curActivity.api_data.get("login") != null) {
                for (int i = 0; i < 4; i++) {
                    tx[i] = new TextView(curActivity);
                    tx[i].setText("Member " + Integer.toString(i));
                    linearlayout.addView(tx[i]);
                }
            }
        } catch(JSONException e) {
            Log.d("JSON", "e " + e.toString());
        }
//        setContentView(R.layout.main);

        return view;
    }

    // source: https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity?noredirect=1&lq=1
    public interface OnResponsePass {
        void onResponsePass(JSONObject response);
    }

    public void showResOnUI(final String res) {
        TextView resTV = context.findViewById(R.id.tvResponse);
        if (res == null)
            resTV.setText("Connection failed");
        else
            resTV.setText(res);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        responsePasser = (OnResponsePass) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    // Source: https://rosettacode.org/wiki/Haversine_formula#Java
    public static class Haversine {
        public static final double R = 6372.8; // In kilometers
        public static double haversine(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        }
        public static void main(String[] args) {
            System.out.println(haversine(36.12, -86.67, 33.94, -118.40));
        }
    }
}
