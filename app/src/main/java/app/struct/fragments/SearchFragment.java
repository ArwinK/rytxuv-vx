package app.struct.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import app.struct.R;
import app.struct.animation.ResizeAnimation;
import app.struct.helpers.Callback;
import app.struct.tasks.FilterTask;
import app.struct.tasks.MainTask;
import app.struct.views.PDialog;

/**
 * Created by arwin on 8/29/17.
 */

public class SearchFragment extends Fragment {

    ImageView optionsButton, ic_search;
    int targetHeight = 0;
    int i = 0;

    LinearLayout ln;
    PDialog pDialog;
    private EditText edtKeyword;
    private String keyword;
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_layout, container, false);
        ln = (LinearLayout) view.findViewById(R.id.myButtons);

        pDialog = new PDialog(getActivity());

        optionsButton = (ImageView) view.findViewById(R.id.optionsButton);
        ic_search = (ImageView) view.findViewById(R.id.ic_search);
        edtKeyword = (EditText) view.findViewById(R.id.keyword);

        radioGroup = (RadioGroup) view.findViewById(R.id.days);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButtons();
            }
        });

        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = edtKeyword.getText().toString();
                pDialog.start("Searching ...");
                new FilterTask(getActivity(), new Callback(pDialog), keyword).execute();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                String value = radioButton.getText().toString();

                switch (checkedId){
                    case R.string.san:
                        keyword = "01";
                        break;
                    case R.string.mon:
                        keyword = "02";
                        break;
                    case R.string.tue:
                        keyword = "03";
                        break;
                    case R.string.wed:
                        keyword = "04";
                        break;
                    case R.string.thu:
                        keyword = "05";
                        break;
                    case R.string.fri:
                        keyword = "06";
                        break;
                    case R.string.sat:
                        keyword = "07";
                        break;
                }

                pDialog.start("Searching ...");
                new FilterTask(getActivity(), new Callback(pDialog), keyword).execute();
            }
        });

        return view;
    }

    public void viewButtons() {

        int ANIMATION_SPEED = 200; // Should be defined static and final with class fields

        if (targetHeight == 0) {
            targetHeight = 135;
        } else {
            targetHeight = 0;
        }

        final ResizeAnimation myAnimator = new ResizeAnimation(
                ln,
                targetHeight,
                ResizeAnimation.Type.HEIGHT,
                ANIMATION_SPEED);
        ln.startAnimation(myAnimator);

    }
}
