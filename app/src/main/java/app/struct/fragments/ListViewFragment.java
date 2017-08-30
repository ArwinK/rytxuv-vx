package app.struct.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import app.struct.R;
import app.struct.adapters.RestaurantAdapter;
import app.struct.helpers.Callback;
import app.struct.models.MarkerModel;
import app.struct.network.I_Response;
import app.struct.tasks.MainTask;
import app.struct.views.PDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    private static final String TAG = "MainActivity";
    List<MarkerModel> orderList;
    private RestaurantAdapter adapter;
    private RecyclerView recyclerView;
    private int targetHeight = 0;
    private int i = 0;

    PDialog pDialog;

    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        pDialog = new PDialog(getActivity());

        pDialog.start("Loading ...");
        new MainTask(getActivity(), new Callback(pDialog)).execute();

        SharedPreferences resList = getActivity().getSharedPreferences("res_list", Context.MODE_PRIVATE);
        String jArray = resList.getString("resArray", null);

        orderList = new ArrayList<>();

        try {
            if (jArray != null) {
                JSONArray nJArray = new JSONArray(jArray);
                orderList = MarkerModel.makeArrayList(nJArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int ANIMATION_SPEED = 250; // Should be defined static and final with class fields
//
//                //Construct an instance of MyAnimator - This one will be used for width animation
//                if(i == 0){
//                    targetHeight = 80;
//                    i++;
//                } else {
//                    targetHeight = 0;
//                    i--;
//                }
//
//                final ResizeAnimation myAnimator = new ResizeAnimation(
//                        ln,
//                        targetHeight,
//                        ResizeAnimation.Type.HEIGHT,
//                        ANIMATION_SPEED);
//                ln.startAnimation(myAnimator);
//            }
//        });

        adapter = new RestaurantAdapter(getActivity(), orderList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void clear() {
        int size = orderList.size();
        orderList.clear();
    }
}
