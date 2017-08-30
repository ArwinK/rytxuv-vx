package app.struct;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.struct.auth.UserDetails;
import app.struct.fragments.ListViewFragment;
import app.struct.fragments.MapViewFragment;
import app.struct.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Log.d("rom2", date);

        fm = getFragmentManager();

        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_view, new MapViewFragment())
                    .commit();
            fm.beginTransaction()
                    .add(R.id.fragment_view2, new SearchFragment())
                    .commit();
        }

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


}

