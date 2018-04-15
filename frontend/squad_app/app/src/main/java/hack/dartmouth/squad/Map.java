package hack.dartmouth.squad;

import android.Manifest;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Map extends Fragment{
    MapView mView;
    SeekBar timeView;
    private GoogleMap gMap;
    private ArrayList<LatLng> locations = new ArrayList<>();
    private Marker curMarker = null;
    private Marker curDestination = null;
    static Menu curActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_machine, container, false);
        curActivity = (Menu) getActivity();
        if(curActivity.dataList != null) {
            Log.d("debug", curActivity.dataList.toString());
//            drawMarkers(curActivity.dataList);
        }
        mView = (MapView) view.findViewById(R.id.mapView);
        mView.onCreate(savedInstanceState);
        mView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e){
            Log.d("Error", e.toString());
        }

        mView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                drawMarkers(curActivity.dataList);
                if (ContextCompat.checkSelfPermission(curActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                }
                gMap.setMyLocationEnabled(true);
//                if (curActivity.dataList != null) drawPath();
            }
        });

        curActivity.updateData("file");
        return view;
    }

    // Draw path between two locations in correspondence to the time line
//    public void drawPath(){
//        PolylineOptions line = new PolylineOptions();
//        line.color(Color.RED);
//        int len = curActivity.dataList.size();
//        for (int i = 1; i < len; i++) {
//            line.add(getLatLng(i - 1), getLatLng(i));
//        }
//        gMap.addPolyline(line);
//        drawMarker(0);
//        gMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(0)));
//        gMap.moveCamera(CameraUpdateFactory.zoomTo(15.5f));
//    }

    // call drawMarker() on every Data in dataList
    public void drawMarkers(ArrayList<Data> dataList) {
        for (Data datum: dataList) {
            drawMarker(datum);
        }
    }

    // Draw a Marker on the map given the corresponding Data
    public void drawMarker(Data datum){
        LatLng pos = getLatLng(datum);
        Log.d("d", "position is: " + pos.toString());
        curMarker = gMap.addMarker(new MarkerOptions().position(pos));
        curMarker.setTitle(datum.getUser());
//        curMarker.setSnippet(getTime(datum));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curMarker.getPosition(), 14));
    }

    // Construct a time string for the marker snippet
//    public String getTime(Data datum){
//        String timestamp = "at ";
//        String before = curActivity.dataList.get(datum).getTime();
//        timestamp += before.substring(0, 2) + " : ";
//        timestamp += before.substring(2, 4) + " : ";
//        timestamp += before.substring(4, 6);
//
//        return timestamp;
//    }

    // Turn stored string data into Latlng format
    public LatLng getLatLng(Data datum){
        Log.d("debug", "trying to get lat, lng: " + datum.getLat() + ", " + datum.getLng());
        return new LatLng(Float.valueOf(datum.getLat()), Float.valueOf(datum.getLng()));

    }

    @Override
    public void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mView.onLowMemory();
    }
}
