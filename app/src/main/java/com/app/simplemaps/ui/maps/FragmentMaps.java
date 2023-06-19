package com.app.simplemaps.ui.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.app.simplemaps.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class FragmentMaps extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setupMap();
    }

    private void setupMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        googleMap.setMyLocationEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                // Pin additional locations
                pinLocations();
            }
        });
    }

    private void pinLocations() {
        List<PinData> pinDataList = new ArrayList<>();
        pinDataList.add(new PinData(new LatLng(-6.941749, 107.628353), "Pujasera Kliningan (Rame mulu)"));
        pinDataList.add(new PinData(new LatLng(-6.8855696, 107.5966687), "Wizzmie Sukajadi (Murah cuy)"));
        pinDataList.add(new PinData(new LatLng(-6.9558739, 107.662286), "KulaKuli Ramen (Enak + murah)"));
        pinDataList.add(new PinData(new LatLng(-6.8977707, 107.612679), "Gacoan Dipatiukur (Sama aja rame mulu)"));
        pinDataList.add(new PinData(new LatLng(-6.8946934,107.6416496), "Ayam Geprek Janda - JawaranaLada (Ril Wareg)"));

        for (PinData pinData : pinDataList) {
            googleMap.addMarker(new MarkerOptions().position(pinData.getLatLng()).title(pinData.getTitle()));
        }
    }

    private static class PinData {
        private LatLng latLng;
        private String title;

        public PinData(LatLng latLng, String title) {
            this.latLng = latLng;
            this.title = title;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public String getTitle() {
            return title;
        }
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4