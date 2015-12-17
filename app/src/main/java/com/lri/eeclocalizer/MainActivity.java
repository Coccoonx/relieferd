package com.lri.eeclocalizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lri.eeclocalizer.Utils.UIUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildToolBar();

        Button go = (Button) findViewById(R.id.go);
        Button find = (Button) findViewById(R.id.find);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GoActivity.class));
            }
        });

        UIUtils.setFont(UIUtils.Font.LIGHT, go, find);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void buildToolBar() {
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            // Set the toolbar title
            TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
            title.setText(R.string.app_name);
            title.setTextSize(28.0f);

            // Change the toolbar typeface
            UIUtils.setFont(UIUtils.Font.ST_MARIE, toolbar);

            // Set the toolbar navigation icon
//            toolbar.setNavigationIcon(null);

            // Use the toolbar as Actionbar
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
