package com.lri.eeclocalizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lri.eeclocalizer.Utils.CoreUtils;
import com.lri.eeclocalizer.Utils.DirectionsJSONParser;
import com.lri.eeclocalizer.Utils.UIUtils;
import com.lri.eeclocalizer.core.model.Parish;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GoActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.CancelableCallback {

    GoogleMap mGoogleMap;
    ArrayList<LatLng> markerPoints;
    private MapFragment mMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLatLn;
    private int topPadding;
    private ProgressDialog progressDialog;
    Button btnDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        buildGoogleApiClient();
        buildToolBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Searching ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        int x = UIUtils.getScreenWidth(this) - (int) UIUtils.convertDpToPixel(40, this);
        int y = (int) UIUtils.convertDpToPixel(70, this);
        topPadding = Math.min(x, y);

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        // Getting reference to SupportMapFragment of the activity_main
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        mMapFragment.getMapAsync(this);


        // Getting reference to Button
        btnDraw = (Button) findViewById(R.id.btn_draw);
        btnDraw.setVisibility(View.INVISIBLE);


    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service

        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("lri", e.toString());
        } finally {
            try {
                iStream.close();
                urlConnection.disconnect();
            } catch (RuntimeException e) {
                Log.d("lri", "Exception " + Log.getStackTraceString(e));
            }

        }
        return data;
    }

    private void showGroup(LatLng... listTmpMarker) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Zoom the camera to see all the marquers
        for (LatLng position : listTmpMarker) {
            builder.include(position);
        }

        LatLngBounds bounds = builder.build();
        int padding = 200; // offset from edges of the map in pixels

        // Animate camera
        mGoogleMap.setPadding(0, 0, 0, 0);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mGoogleMap.animateCamera(cu);
        mGoogleMap.setPadding(0, topPadding, 0, 0);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Enable MyLocation Button in the Map
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);

        mGoogleMap.setPadding(0, topPadding, 0, UIUtils.getActionBarSize(this));
//        mGoogleMap.setOnMarkerClickListener(this);
//        mGoogleMap.setOnMapClickListener(this);


        UiSettings uiSettings = mGoogleMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setZoomControlsEnabled(true);

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
            mCurrentLatLn = new LatLng(l.getLatitude(), l.getLongitude());
            UIUtils.moveCameraTo(mGoogleMap, mCurrentLatLn, this);
        }

        if (mCurrentLatLn != null) {
            final Parish mostClosed = CoreUtils.getMinLatLng(mCurrentLatLn);

            if (mostClosed != null) {
                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(mostClosed.getLatLng());
                options.snippet(mostClosed.getDisplayName());
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mGoogleMap.addMarker(options);

                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(mCurrentLatLn, mostClosed.getLatLng());

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
            progressDialog.dismiss();
            showGroup(mCurrentLatLn, mostClosed.getLatLng());


            btnDraw.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String uri = String
                            .format(Locale.ENGLISH,
                                    "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                                    mCurrentLatLn.latitude,
                                    mCurrentLatLn.longitude,
                                    "My position",
                                    mostClosed.getLatLng().latitude,
                                    mostClosed.getLatLng().longitude,
                                    "" + mostClosed.getDisplayName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);


                }
            });
//            btnDraw.setVisibility(View.VISIBLE);

        } else {
            progressDialog.dismiss();
            Toast.makeText(GoActivity.this, "Please enable your location service.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void buildToolBar() {
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            // Set the toolbar title
            TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
            title.setText(R.string.app_name);
            title.setTextSize(28.0f);

            // Change the toolbar typeface
            UIUtils.setFont(UIUtils.Font.ST_MARIE, toolbar);

            // Set the toolbar navigation icon
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

            // Use the toolbar as Actionbar
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();
        switch (itemID) {
            case android.R.id.home:
//                intent = null;
                super.onBackPressed();
                finish();
                break;

            default:
                throw new IllegalArgumentException("This menu item id is not valid : " + itemID);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);
            }

            // Drawing polyline in the Google Map for the i-th route
            try {
                mGoogleMap.addPolyline(lineOptions);
            } catch (RuntimeException e) {
                Toast.makeText(GoActivity.this, "Please select valid places", Toast.LENGTH_SHORT).show();
                Log.d("lri", "onPostExecute " + Log.getStackTraceString(e));
            }
        }
    }
}
