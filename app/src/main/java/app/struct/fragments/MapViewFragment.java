package app.struct.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import app.struct.R;
import app.struct.helpers.Callback;
import app.struct.models.MarkerModel;
import app.struct.network.I_Response;
import app.struct.tasks.MainTask;
import app.struct.views.PDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    List<MarkerModel> companyList;
    PDialog pDialog;
    private GoogleMap mMap;
    private double lng;
    private double lat;

    public MapViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps3, container, false);

        pDialog = new PDialog(getActivity());

        pDialog.start("Loading ...");
        new MainTask(getActivity(), new Callback(pDialog)).execute();

        SharedPreferences resList = getActivity().getSharedPreferences("res_list", Context.MODE_PRIVATE);
        String jArray = resList.getString("resArray", null);

        companyList = new ArrayList<>();

        if (jArray != null) {
            try {

                JSONArray nJArray = new JSONArray(jArray);
                companyList = MarkerModel.makeArrayList(nJArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < companyList.size(); i++) {

            if (companyList.get(i).getLatitude() != null) {
                lat = Double.parseDouble(companyList.get(i).getLatitude());
                Log.d("markerInfo", lat + "");
            }
            if (companyList.get(i).getLongitude() != null) {
                lng = Double.parseDouble(companyList.get(i).getLongitude());
                Log.d("markerInfo", lng + "");
            }

            createMarker(lat, lng,
                    companyList.get(i).getTitle(), companyList.get(i).getSnippet(),
                    companyList.get(i).getIconResID());
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void clear() {
        int size = companyList.size();
        companyList.clear();
    }

    protected void createMarker(double lat, double lon, String title, String snippet, int iconResID) {
        LatLng location = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("kenya")
                .snippet(snippet));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
