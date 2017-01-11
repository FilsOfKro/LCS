/*
 * Copyright (c) 2017.
 *
 * Owenn Pantry / Lucas Roulin
 *
 * owenn.pantry@gmail.com
 *
 * Ce logiciel est un programme informatique développé comme un projet d'étude.
 *
 *  Ce logiciel est régi par la licence CeCILL soumise au droit français et
 *  respectant les principes de diffusion des logiciels libres. Vous pouvez
 *  utiliser, modifier et/ou redistribuer ce programme sous les conditions
 *  de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 *  sur le site "http://www.cecill.info".
 *
 *  En contrepartie de l'accessibilité au code source et des droits de copie,
 *  de modification et de redistribution accordés par cette licence, il n'est
 *  offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 *  seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 *  titulaire des droits patrimoniaux et les concédants successifs.
 *
 *  A cet égard  l'attention de l'utilisateur est attirée sur les risques
 *  associés au chargement,  à l'utilisation,  à la modification et/ou au
 *  développement et à la reproduction du logiciel par l'utilisateur étant
 *  donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 *  manipuler et qui le réserve donc à des développeurs et des professionnels
 *  avertis possédant  des  connaissances  informatiques approfondies.  Les
 *  utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 *  logiciel à leurs besoins dans des conditions permettant d'assurer la
 *  sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 *  à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 *  Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 *  pris connaissance de la licence CeCILL, et que vous en avez accepté les
 *  termes.
 */

package fr.univ_brest.lcs.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import fr.univ_brest.lcs.model.Webcam;
import fr.univ_brest.lcs.R;

import android.util.Log;
import android.widget.Toast;

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
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.d("", marker.getTitle());
            }
        });

        myGoogleApiClient.connect();
    }

    //Requested by ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Permission check required by myLastLocation

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    0); //Does not catch
            return;
        }

        Location myLastLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleApiClient);
        if(myLastLocation != null){
            myLatitude = myLastLocation.getLatitude();
            myLongitude = myLastLocation.getLongitude();
        }

        // Add a marker in SUMPPS and UBO
        Webcam ubo = new Webcam("iotubo.univ-brest.fr", new LatLng(48.400695, -4.501135), "UBO");
        Webcam sumpps = new Webcam("une.adresse.fr" , new LatLng(48.399519, -4.495070), "SUMPPS");

        //Les marqueurs sont pour l'instant écrits en dur dans l'application, dans une version future
        // il faudra constituer une liste et la parcourir a l'aide d'un foreach par exemple et
        // appliquer le traitement suivant

        myMap.addMarker(new MarkerOptions().position(ubo.getCoordinates()).title("Ouvrir le flux pour " + ubo.getName()));
        myMap.addMarker(new MarkerOptions().position(sumpps.getCoordinates()).title("Ouvrir le flux pour " + sumpps.getName()));

        //Find user's location
        LatLng me = new LatLng(myLatitude, myLongitude);

        //and move the camera
        myMap.setMyLocationEnabled(true);
        myMap.moveCamera(CameraUpdateFactory.newLatLng(me));
        myMap.moveCamera(CameraUpdateFactory.zoomTo(14f));

        GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
                = new GoogleMap.OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MapsActivity.this,
                        "onInfoWindowClick():\n" +
                                marker.getPosition().latitude + "\n" +
                                marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MapsActivity.this, WebViewActivity.class);
                        startActivity(intent);
            }
        };

        myMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);
    }

    //Never called by ActivityCompat.requestPermissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("TAG", "dialog onRequestPermissionsResult");
        switch (requestCode) {
            case 0:
                // Check Permissions Granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(myMap);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Read contact permission is denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
// --Commented out by Inspection START (21/12/2016 15:28):
//    public void onConnectionFailed(ConnectionResult result) {
//        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
// --Commented out by Inspection STOP (21/12/2016 15:28)

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        myGoogleApiClient.connect();
    }

}
