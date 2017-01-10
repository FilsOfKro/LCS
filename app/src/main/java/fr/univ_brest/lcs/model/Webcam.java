package fr.univ_brest.lcs.model;

import com.google.android.gms.maps.model.LatLng;

import java.net.InetAddress;

/**
 * Created by owenn on 20/12/2016.
 */

public class Webcam {
    private InetAddress ip;
    private LatLng coordinates;

    public Webcam(InetAddress ip, LatLng coordinates) {
        this.ip = ip;
        this.coordinates = coordinates;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
