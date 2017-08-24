package app.struct;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import app.struct.models.LoginModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arwin on 7/3/17.
 */

public class LoginActivity extends ActionBarActivity {
    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("Class Started");
        loginModel = new LoginModel(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginModel.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
