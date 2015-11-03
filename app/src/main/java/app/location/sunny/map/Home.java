package app.location.sunny.map;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import LocationService.GPSTracker;

public class Home extends FragmentActivity implements OnMapReadyCallback {
Button pic,esign;
    private GoogleMap mMap;
    GPSTracker gps;
    public static final int SIGNATURE_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        pic=(Button)findViewById(R.id.photo);
        esign=(Button)findViewById(R.id.esignature);

        esign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, CaptureSignature.class);
                startActivityForResult(intent,SIGNATURE_ACTIVITY);


            }
        });


        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Images.class);
                startActivity(intent);
            }});

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String status  = bundle.getString("status");
                    if(status.equalsIgnoreCase("done")){
                        Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 105, 50);
                        toast.show();
                    }
                }
                break;
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
        mMap = googleMap;
        gps = new GPSTracker(Home.this);
        double latitude = 0;
        double longitude = 0;
        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
