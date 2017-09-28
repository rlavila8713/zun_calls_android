package com.xkoders.zuncallandroid.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.adapters.ViewPagerAdapter;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;
import com.xkoders.zuncallandroid.components.SlidingTabLayout;
import com.xkoders.zuncallandroid.constants.FRAGMENTS_ACTIONS;
import com.xkoders.zuncallandroid.constants.SHARED_PREF_IDS;
import com.xkoders.zuncallandroid.fragments.CallsFragment;
import com.xkoders.zuncallandroid.interfaces.OnActionPerformed;
import com.xkoders.zuncallandroid.models.ZunCallUser;
import com.xkoders.zuncallandroid.utils.LocalPreferences;
import com.xkoders.zuncallandroid.utils.SearchableProvider;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnActionPerformed, DatePickerDialog.OnDateSetListener {

    //posteriormente este dato saldra de una db
    CharSequence Titles[] = {"Llamadas"};
    int Numboftabs = Titles.length;
    NavigationView navigationView1;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TextView nav_header_user_email;
    private View nav_header_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWidgetsReference();

        setSupportActionBar(toolbar);


        CallsFragment mCallsFragment = new CallsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mCallsFragment).commit();

        /*adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.s4);
            }
        });
        tabs.setViewPager(pager);*/

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView1 != null) {
            navigationView1.setNavigationItemSelectedListener(this);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (drawer != null) {
            setUserData();
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        setUserData();

    }

    @Override
    public void onBackPressed() {
        if (drawer != null)
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Servicios/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
//                showLocationDialog();
                Calendar now = Calendar.getInstance();
                String[] date = LocalPreferences.getDateToQuery(getApplicationContext(), SHARED_PREF_IDS.DATE_QUERY).split("-");

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        (DatePickerDialog.OnDateSetListener) MainActivity.this,
                        Integer.parseInt(date[2]),//year
                        Integer.parseInt(date[0]) - 1,//month
                        Integer.parseInt(date[1])//day
                );
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                dpd.show(getFragmentManager(), "Datepickerdialog");

                break;
            case R.id.action_clear_history:
                SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, SearchableProvider.AUTHORITY,
                        SearchableProvider.MODES);
                searchRecentSuggestions.clearHistory();
                break;
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUserData() {
        nav_header_user_email.setText(ZunCallAndroidApplication.getInstance().getUserLogged().getEmail());
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.action_about));
        builder.setMessage("Message");
        builder.setCancelable(false);
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String text = "";
        if (id == R.id.nav_money) {
            text = "money";
        } else if (id == R.id.nav_calls) {

        } else if (id == R.id.nav_contacts) {
            text = "contacts";
        } else if (id == R.id.nav_account) {
            ProfileActivity.createInstance(
                    (Activity) this, ZunCallAndroidApplication.getInstance().getUserLogged());
        }

        // Toast.makeText(this, "You have chosen " + text, Toast.LENGTH_LONG).show();

        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActionPerformed(int ACTION, Bundle bundle) {
        switch (ACTION) {
            case FRAGMENTS_ACTIONS.SHOW_REP_CELULARES:

                break;
            case FRAGMENTS_ACTIONS.SHOW_REP_ORDENADORES:
                break;
        }
    }

    public void myStartActivity(final Intent t) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(t);
            }
        }, 250);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void getWidgetsReference() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        //pager = (ViewPager) findViewById(R.id.pager);
        //tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        nav_header_user_email = (TextView) navigationView1.getHeaderView(0).findViewById(R.id.nav_header_user_email);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = getResources().getString(R.string.date_toquery_selected) + " (" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + ")";
        String dateToQuery = (monthOfYear + 1) + "-" + dayOfMonth + "-" + year;
        Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
        LocalPreferences.setDateToQuery(getApplicationContext(), SHARED_PREF_IDS.DATE_QUERY, dateToQuery);
    }
}
