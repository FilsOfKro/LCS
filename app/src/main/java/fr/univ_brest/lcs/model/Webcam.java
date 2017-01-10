package fr.univ_brest.lcs.model;

import com.google.android.gms.maps.model.LatLng;

import java.net.InetAddress;

/**
 * Created by owenn on 20/12/2016.
 */

public class Webcam {
    private String hostname;
    private LatLng coordinates;
    private String name;

    //Constructor with a hostname
    public Webcam(String hostname, LatLng coordinates, String name) {
        this.hostname = hostname;
        this.coordinates = coordinates;
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
