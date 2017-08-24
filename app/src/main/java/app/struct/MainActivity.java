package app.struct;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

import app.struct.animation.ResizeAnimation;
import app.struct.auth.UserDetails;
import app.struct.fragments.ListViewFragment;
import app.struct.fragments.MapViewFragment;

public class MainActivity extends AppCompatActivity {

   FragmentManager fm;
    Button listView, mapView;
    private boolean menuState = false;
    int targetHeight = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        fm = getFragmentManager();

        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_view, new MapViewFragment())
                    .commit();
        }

        listView = (Button) findViewById(R.id.listView);

//        mapView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fm.beginTransaction()
//                        .replace(R.id.fragment_view, new MapViewFragment())
//                        .commit();
//            }
//        });
//
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fm.beginTransaction()
//                        .replace(R.id.fragment_view, new ListViewFragment())
//                        .commit();
//            }
//        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logOut() {
        UserDetails userDetails = new UserDetails();
        userDetails.logout(getApplicationContext(), true);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void mapView(View view) {
        fm.beginTransaction()
                        .replace(R.id.fragment_view, new MapViewFragment())
                        .commit();
    }

    public void listView(View view) {
        fm.beginTransaction()
                        .replace(R.id.fragment_view, new ListViewFragment())
                        .commit();
    }

    public void viewButtons(final View view) {

        final LinearLayout ln = (LinearLayout) findViewById(R.id.myButtons);
        int ANIMATION_SPEED = 250; // Should be defined static and final with class fields

        //Construct an instance of MyAnimator - This one will be used for width animation
        if(i == 0){
            targetHeight = 80;
            i++;
        } else {
            targetHeight = 0;
            i--;
        }

        final ResizeAnimation myAnimator = new ResizeAnimation(
                ln,
                targetHeight,
                ResizeAnimation.Type.HEIGHT,
                ANIMATION_SPEED);
        ln.startAnimation(myAnimator);

    }
}

