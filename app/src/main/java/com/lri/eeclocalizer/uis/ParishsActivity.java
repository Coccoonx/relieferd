package com.lri.eeclocalizer.uis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.lri.eeclocalizer.R;
import com.lri.eeclocalizer.uis.recipient.adapters.ParishAdapter;
import com.lri.eeclocalizer.utils.UIUtils;


public class ParishsActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ParishAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parishs_list);

        buildToolBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.settingsRecyclerView);

        // Set an adapter to this recycler view
        mAdapter = new ParishAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // Set the behaviour of this recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);

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
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

            // Use the toolbar as Actionbar
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }
}
