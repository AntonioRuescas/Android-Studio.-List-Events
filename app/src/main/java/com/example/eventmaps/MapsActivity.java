package com.example.eventmaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton addNewEvent;
    private GoogleMap map;
    Intent intent;
    String event2, site2, time2, date2;
    SharedPreferences sharedPreferences;
    ArrayList<Events> data;
    RecyclerView recyclerEventList;
    RecyclerCustomAdapter adapter;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Obtenemos el fragment del mapa y notificamos cuando está listo para ser usado
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        recyclerEventList = findViewById(R.id.recyclerEventList);

        sharedPreferences = getSharedPreferences("values", MODE_PRIVATE);

        data = new ArrayList<>();
        data = PrefConfig.readListFromPref(this);

        if (data == null) {
            data = new ArrayList<>();
            data.add(new Events("Nombre evento", "Lugar evento", "Fecha evento", "Hora evento"));
        }

        Intent intent2 = getIntent();
        event2 = intent2.getStringExtra("event");
        site2 = intent2.getStringExtra("site");
        date2 = intent2.getStringExtra("date");
        time2 = intent2.getStringExtra("time");


        if (time2 != null) {
            data.add(new Events(event2, site2, date2, time2));

            PrefConfig.writeListInPref(this, data);

            editor = sharedPreferences.edit();
            editor.putString("event", event2);
            editor.putString("site", site2);
            editor.putString("date", date2);
            editor.putString("time", time2);
            editor.apply();
        }
        //Personalizo y atualizo el recycler view mostrando un linear layout para hacer que salga un evento debajo de otro...
        recyclerEventList.setLayoutManager(new LinearLayoutManager(this));
        //le añado el adapter personalizado que he creado (event_recyclerview) y (RecyclerCustomAdapter)
        adapter = new RecyclerCustomAdapter(this, data);
        //Indicamos que el recyclerview use el adapter
        recyclerEventList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //Método onClick para llamar a la búsqueda del sitio al clickar sobre cualquier elemento, devolviendo position, como he definido en el recylercustomadapter
        adapter.setCustomClickListener(new RecyclerCustomAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String location = data.get(position).getSite();
                Toast.makeText(getApplicationContext(), "Mostrando localización del evento: " + position, Toast.LENGTH_SHORT).show();
                geocoder(location);
            }

            @Override
            public void updateRemove() {
                PrefConfig.writeListInPref(getApplicationContext(), data);
            }
        });

        //Encontramos el botón y le añadimos el intent para abrir la siguiente actividad
        addNewEvent = findViewById(R.id.addNewEvent);
        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), DateActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //Localiza dispositivo
        activateLocalization();
    }

    //Función para comprobar permisos de localización
    public void activateLocalization() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 123);
            return;
        }
        map.setMyLocationEnabled(true);
    }

    //Función para permitir que localice nuestro dispositivo
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activateLocalization();
        }
    }

    //Función geocode
    public void geocoder(String place) {
        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocationName(place, 1);

            if (addresses.size() != 0) {
                Address location = addresses.get(0);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.moveCamera(CameraUpdateFactory.zoomTo(10));
            } else {
                Toast.makeText(this, "No se ha encontrado la dirección...", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

