package com.padayikaro.demorinky;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPsTracker extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;
    Button buttonok;
    Geocoder geocoder;
    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracker);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission, Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_PERMISSION);
            }
            buttonok=(Button)findViewById(R.id.buttonok);
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gps = new GPSTracker(GPsTracker.this);

                    // check if GPS enabled
                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        geocoder = new Geocoder(GPsTracker.this, Locale.getDefault());

                        try {


                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                           // String postalCode = addresses.get(0).getPostalCode();
                            //String knownName = addresses.get(0).getFeatureName();

                            System.out.println("addresslocal"+country+state+city);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    }else{
                        gps.showSettingsAlert();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //onLocationChanged();
                } else {
                    System.out.println("permission denied!");
                }
                break;
        }
    }



}
