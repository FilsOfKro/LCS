package fr.univ_brest.lcs.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;

import fr.univ_brest.lcs.R;

import android.util.Log;

/**
 * MapsActivity displays a map with user's position and the nearby cameras.
 *
 * We first execute, we get
 * onCreate,    we get a GoogleApiClient
 * onMapReady,  initialize vars, connect to API, launches onConnected
 * onConnected, get location
 */
public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getName(); //For logging purposes
    private GoogleMap myMap;
    private GoogleApiClient myGoogleApiClient;
    private Double myLongitude;
    private Double myLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (myGoogleApiClient == null) {
            myGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.e(TAG, "Connection failed");
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myGoogleApiClient.connect();

        // Add a marker in Sydney and UBO
        LatLng sydney = new LatLng(-34, 151);
        LatLng ubo = new LatLng(48.4, -4.45);

        myMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        myMap.addMarker(new MarkerOptions().position(ubo).title("Marker in UBO"));

        //Find user's location
        Marker myCurrLocation;
        LatLng me = new LatLng(myLatitude, myLongitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(me);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        myCurrLocation = myMap.addMarker(markerOptions);

        //and move the camera
        myMap.moveCamera(CameraUpdateFactory.newLatLng(me));
    }

    //Requested by ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Permission check required bymyLastLocation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLastLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleApiClient);
        if(myLastLocation != null){
            myLatitude = myLastLocation.getLatitude();
            myLongitude = myLastLocation.getLongitude();
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        myGoogleApiClient.connect();
    }
}
