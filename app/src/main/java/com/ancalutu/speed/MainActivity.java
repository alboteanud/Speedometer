package com.ancalutu.speed;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_main, new PlaceholderFragment())
                    .commit();
        }
    }



    public static class PlaceholderFragment extends Fragment implements LocationListener {
        LocationManager mLocationManager;
        TextView txt;
        Animation anim;
        Button button;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }




        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            txt = (TextView) getActivity().findViewById(R.id.textView);

            anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);


            mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            button = (Button) getActivity().findViewById(R.id.buttonEnableGPS);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });





        }

        @Override
        public void onResume() {
            super.onResume();

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
            txt.setTextSize(68);
            txt.setText(getString(R.string.initial_text));
            txt.startAnimation(anim);
            button.setVisibility(View.INVISIBLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }





        @Override
        public void onLocationChanged(Location location) {
            txt.setText (String.valueOf((int) (location.getSpeed() * 3.6)));
            txt.clearAnimation();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

       //     Toast.makeText(getActivity(), "onStatusChanged", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onProviderEnabled(String provider) {
            txt.setTextSize(68);
            txt.setText(". .");
            txt.startAnimation(anim);
            button.setVisibility(View.INVISIBLE);
         //   Toast.makeText(getActivity(), "onProviderEnabled", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            txt.setTextSize(20);
            txt.setText("NO GPS");
            txt.clearAnimation();
            button.setVisibility(View.VISIBLE);
         //   Toast.makeText(getActivity(), "onProviderDisabled", Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onStop() {
            super.onStop();

            mLocationManager.removeUpdates(this);
        }

    }



}





