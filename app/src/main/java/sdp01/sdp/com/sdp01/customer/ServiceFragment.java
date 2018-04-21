package sdp01.sdp.com.sdp01.customer;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sdp01.sdp.com.sdp01.MyApplication;
import sdp01.sdp.com.sdp01.R;
import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;
import sdp01.sdp.com.sdp01.models.Service;
import sdp01.sdp.com.sdp01.util.Networking;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends Fragment implements OnMapReadyCallback {

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private Marker marker;
    private MarkerOptions markerOptions;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    ScheduledExecutorService scheduler;

    private MapView mapView;
    private GoogleMap googleMap;

    private TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Service");
        }
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        textView = (TextView) view.findViewById(R.id.textView);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        scheduler.shutdown();
    }

    @Override
    public void onStop() {
        super.onStop();
        scheduler.shutdown();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void getService() {
        //ERROR SINCE IT CHECKS user_id
        Networking.getService(new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("GETSERVICE_RESPONSE", response.toString());

                try {

                    String service = response.getString("service");

                    JSONObject location_js = response.getJSONObject("location");
                    Location location = new Location("location");
                    location.setLatitude(Double.parseDouble(location_js.getString("latitude")));
                    location.setLongitude(Double.parseDouble(location_js.getString("longitude")));

                    JSONObject sp_location_js = response.getJSONObject("sp_location");
                    Location sp_location = new Location("sp_location");
                    sp_location.setLatitude(Double.parseDouble(sp_location_js.getString("latitude")));
                    sp_location.setLongitude(Double.parseDouble(sp_location_js.getString("longitude")));

                    JSONObject cu_location_js = response.getJSONObject("cu_location");
                    Location cu_location = new Location("cu_location");
                    cu_location.setLatitude(Double.parseDouble(cu_location_js.getString("latitude")));
                    cu_location.setLongitude(Double.parseDouble(cu_location_js.getString("longitude")));

                    Log.e("GETSERVICE_INSIDE", response.toString());

                    Service service1 = new Service(service, location, sp_location, cu_location);
                    showCar(service1);


                } catch (JSONException e) {
                    // TODO
                    e.printStackTrace();
                    Log.e("GETSERVICE_outSIDE", response.toString());
                }


            }

            @Override
            public void onError(ErrorCode anError) {
                // TODO
                Log.e("GETSERVICE_ERROR", anError.info);
            }
        });
    }


    public void showCar(final Service service) {

        Log.e("RR", "MAPPPASS1111");
        GoogleDirection.withServerKey("AIzaSyAr1tqjaLuZcalZ1BW_ZlitRdjGTNVTy5E")
                .from(new LatLng(service.getCu_location().getLatitude(), service.getCu_location().getLongitude()))
                .to(new LatLng(service.getSp_location().getLatitude(),service.getSp_location().getLongitude()))
                .transportMode(TransportMode.DRIVING)
                .unit(Unit.METRIC)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
//                            List<Step> step = leg.getStepList();
                            ArrayList<LatLng> pointList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(MyApplication.getAppContext(), pointList, 5, Color.RED);
                            googleMap.addPolyline(polylineOptions);

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(service.getLocation().getLatitude(),
                                            service.getLocation().getLongitude()), DEFAULT_ZOOM));
                            markerOptions = new MarkerOptions()
                                    .position(new LatLng(service.getLocation().getLatitude(), service.getLocation().getLongitude()));
                            marker = googleMap.addMarker(markerOptions);


                            Log.e("RR", "MAPPPASS");


                            Info distanceInfo = leg.getDistance();
                            Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            String duration = durationInfo.getText();

                            textView.setText("Duration: " + duration);

                        } else {
                            // Do something
                            Log.e("RR", "MAPPPASS " + rawBody);
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                        Log.e("RR", "MAPPPASS"+ t.getMessage());
                    }
                });

    }


    public void realTimeThread() {
        scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        // call service
                        getRealTimeLocation();
                    }
                }, 0, 5, TimeUnit.SECONDS);
    }
    public void getRealTimeLocation() {

        Networking.getService(new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("GETSERVICE_RESPONSE", response.toString());

                try {

                    if (!response.getString("price").isEmpty()){
                        scheduler.shutdown();
                        Fragment fragment = new PaymentFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.mainFrame, fragment);
                        ft.commit();
                        return;
                    }

                    String service = response.getString("service");

                    JSONObject location_js = response.getJSONObject("location");
                    Location location = new Location("location");
                    location.setLatitude(Double.parseDouble(location_js.getString("latitude")));
                    location.setLongitude(Double.parseDouble(location_js.getString("longitude")));

                    JSONObject sp_location_js = response.getJSONObject("sp_location");
                    Location sp_location = new Location("sp_location");
                    sp_location.setLatitude(Double.parseDouble(sp_location_js.getString("latitude")));
                    sp_location.setLongitude(Double.parseDouble(sp_location_js.getString("longitude")));

                    JSONObject cu_location_js = response.getJSONObject("cu_location");
                    Location cu_location = new Location("cu_location");
                    cu_location.setLatitude(Double.parseDouble(cu_location_js.getString("latitude")));
                    cu_location.setLongitude(Double.parseDouble(cu_location_js.getString("longitude")));

                    Log.e("GETSERVICE_INSIDE", response.toString());

                    Service service1 = new Service(service, location, sp_location, cu_location);

                    if (marker != null) {
                        marker.remove();
                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(service1.getLocation().getLatitude(),
                                    service1.getLocation().getLongitude()), DEFAULT_ZOOM));
                    marker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(service1.getLocation().getLatitude(), service1.getLocation().getLongitude())));


                    Log.e("RR", "MAPPPASS");



                } catch (JSONException e) {
                    // TODO
                    e.printStackTrace();
                    Log.e("GETSERVICE_outSIDE", response.toString());
                }


            }

            @Override
            public void onError(ErrorCode anError) {
                // TODO
                Log.e("GETSERVICE_ERROR", anError.info);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        this.googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
//        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) findViewById(R.id.map), false);
//
//                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
//                title.setText(marker.getTitle());
//
//                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
        getService();
        realTimeThread();

    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this.getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Log.e("MAP", task.getResult().toString());
                            mLastKnownLocation = task.getResult();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d("Maps", "Current location is null. Using defaults.");
                            Log.e("Maps", "Exception: %s", task.getException());
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // Location Permission:
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
