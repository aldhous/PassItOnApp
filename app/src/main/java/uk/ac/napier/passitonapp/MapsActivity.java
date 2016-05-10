/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.napier.passitonapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/** The Google maps screen showing locally available items and the user's location. **/
public class MapsActivity extends AppCompatActivity implements
        OnInfoWindowClickListener,
        OnMapReadyCallback,
        OnInfoWindowLongClickListener,
        OnInfoWindowCloseListener,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /** Set location markers for the map **/
    private static final LatLng ITEM1 = new LatLng(55.9508204, -3.1932204);
    private static final LatLng ITEM2 = new LatLng(55.9484562, -3.2115578);
    private static final LatLng ITEM3 = new LatLng(55.9712258, -3.2415602);
    private static final LatLng ITEM4 = new LatLng(55.9536441, -3.2041015);
    private static final LatLng ITEM5 = new LatLng(55.9408273, -3.2179325);
    private static final LatLng ITEM6 = new LatLng(55.9378034, -3.1808459);
    private static final LatLng ITEM7 = new LatLng(55.9491215, -3.1794442);
    private static final LatLng ITEM8 = new LatLng(55.9557, -3.1545769);

    /** Set the location permission request to on. **/
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /** Set the location permission denied. **/
    private boolean mPermissionDenied = false;

    /** Create a customised info window and contents. **/
    class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        /** Set an image for the customised marker contents. **/
        private void render(Marker marker, View view) {
            int photo;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mItem1)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem2)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem3)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem4)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem5)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem6)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem7)) {
                photo = R.drawable.item1;
            } else if (marker.equals(mItem8)) {
                photo = R.drawable.item1;
            } else {
                /** Passing 0 will clear the image view. **/
                photo = 0;
            }
            ((ImageView) view.findViewById(R.id.photo)).setImageResource(photo);

            /** Set the title for the customised marker contents. **/
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            titleUi.setText(title);

            /** Set the description for the customised marker contents. **/
            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            snippetUi.setText(snippet);
        }
    }

    /** Declare the Google Map. **/
    private GoogleMap mMap;

    /** Declare the Markers. **/
    private Marker mItem1;
    private Marker mItem2;
    private Marker mItem3;
    private Marker mItem4;
    private Marker mItem5;
    private Marker mItem6;
    private Marker mItem7;
    private Marker mItem8;

    /** Keeps track Markers. Useful for refreshing the info window. **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Create the menu for this activity. **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Create the functions for each menu item shown in this activity. **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /** View my account. **/
            case R.id.action_view:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 1);
                Intent activityA = new Intent(getApplicationContext(), SettingsActivity.class);
                activityA.putExtras(dataBundle);
                startActivity(activityA);
                return true;
            /** View items in the list view. **/
            case R.id.action_list:
                Intent activityB = new Intent(getApplicationContext(), ItemsActivity.class);
                startActivity(activityB);
                break;
            /** Log out of the app. **/
            case R.id.action_logout:
                Intent activityC = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityC);
                break;
            /** The activity back button. **/
            case R.id.action_back:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /** Create the Map with UI zoom, markers, custom info windows. **/
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Hide the zoom control.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add markers to the map.
        addMarkersToMap();

        // Set the info window adapter to change the info window and contents
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);

        // Set listeners for My Location events.
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        // Set the content description for accessibility.
        map.setContentDescription("Map with markers.");

        // Add markers to the map and move the camera to see all the markers in the view.
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView != null) {
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation") // We use the new method when supported
                    @SuppressLint("NewApi") // We check which build version we are using.
                    @Override
                    public void onGlobalLayout() {
                        LatLngBounds bounds = new LatLngBounds.Builder()
                                .include(ITEM1)
                                .include(ITEM2)
                                .include(ITEM3)
                                .include(ITEM4)
                                .include(ITEM5)
                                .include(ITEM6)
                                .include(ITEM7)
                                .include(ITEM8)
                                .build();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                    }
                });
            }
        }
    }

    /** Enable My Location if the fine location permission has been granted. **/
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    /** Toast if My Location is clicked by the user. **/
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Your location", Toast.LENGTH_SHORT).show();
        // Return false so the default behavior occurs
        // animate the camera to the user's current position.
        return false;
    }

    /** Request permission from the user to enable My Location using fine location data. **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable My Location if permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragment resumes.
            mPermissionDenied = true;
        }
    }

    /** If permission is denied resume the map fragment with an error message to alert the user. **/
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /** Displays an error message stating that the location permission is missing. **/
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /** Add the information and settings for the custom contents and marker. **/
    private void addMarkersToMap() {
        // Uses a colored icon.
        mItem1 = mMap.addMarker(new MarkerOptions()
                .position(ITEM1)
                .title("Fresh leaks")
                .snippet("2 spare leaks available")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem2 = mMap.addMarker(new MarkerOptions()
                .position(ITEM2)
                .title("Box of onions")
                .snippet("Free box of large onions")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem3 = mMap.addMarker(new MarkerOptions()
                .position(ITEM3)
                .title("Lentils")
                .snippet("2 bags of unopened lentils")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem4 = mMap.addMarker(new MarkerOptions()
                .position(ITEM4)
                .title("Dried seaweed")
                .snippet("Good for iodine")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem5 = mMap.addMarker(new MarkerOptions()
                .position(ITEM5)
                .title("Bag of pasta")
                .snippet("Large bag of Farfalle pasta")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem6 = mMap.addMarker(new MarkerOptions()
                .position(ITEM6)
                .title("Tofu")
                .snippet("Surplus packet of Tofu")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem7 = mMap.addMarker(new MarkerOptions()
                .position(ITEM7)
                .title("Cereal bars")
                .snippet("Pack of 6 special K bars")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mItem8 = mMap.addMarker(new MarkerOptions()
                .position(ITEM8)
                .title("Brown rice")
                .snippet("Large bag of in date rice")
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
    }

    /** Toast for the info window if clicked. **/
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Click map to close", Toast.LENGTH_SHORT).show();
    }

    /** Toast for the info window if closed. **/
    @Override
    public void onInfoWindowClose(Marker marker) {
        Toast.makeText(this, "Info closed", Toast.LENGTH_SHORT).show();
    }

    /** Toast for the info window is clicked for a long time. **/
    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Toast.makeText(this, "No action", Toast.LENGTH_SHORT).show();
    }

}
