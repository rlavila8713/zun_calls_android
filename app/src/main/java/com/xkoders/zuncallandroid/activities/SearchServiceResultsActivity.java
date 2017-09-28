package com.xkoders.zuncallandroid.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.clans.fab.FloatingActionButton;
import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.adapters.CallsAdapter;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;
import com.xkoders.zuncallandroid.constants.SHARED_PREF_IDS;
import com.xkoders.zuncallandroid.models.Call;
import com.xkoders.zuncallandroid.utils.LocalPreferences;
import com.xkoders.zuncallandroid.utils.SearchableProvider;
import com.xkoders.zuncallandroid.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchServiceResultsActivity extends AppCompatActivity {
    private List<Call> items;
    private CallsAdapter mCallsAdapter;
    private RecyclerView recyclerView;
    private HashMap<String, String> parametros;
    private int id_seco;
    private String query;
    private List<Call> items_searched;
    private FloatingActionButton mFabZunCalls_getImport_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service_results);

        getWidgetsReferences();

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_result_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        items = new ArrayList();
        Intent searchIntent = getIntent();

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        parametros = new HashMap<>();
        id_seco = ZunCallAndroidApplication.getInstance().getUserLogged().getId_seco();
        parametros.put("id_seco", String.valueOf(id_seco));
        parametros.put("action", "2");
        parametros.put("date", Utils.getCurrentDate());
        //getAllCalls(true, parametros);

        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            query = searchIntent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            setParams();
            getAllCalls(true, parametros);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY, SearchableProvider.MODES);
            searchRecentSuggestions.saveRecentQuery(query, null);

        }

        mFabZunCalls_getImport_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallsImport();
            }
        });

    }

    public void getWidgetsReferences() {
        recyclerView = (RecyclerView) findViewById(R.id.searh_results_recycler_view_services);
        mFabZunCalls_getImport_search = (FloatingActionButton) findViewById(R.id.mFabZunCalls_getImport_search);

    }

    public List<Call> parseJson(JSONObject jsonObject) {
        List<Call> callList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("calls");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Call post = new Call(
                            objeto.getInt("id_seco"),
                            objeto.getString("digitos"),
                            objeto.getString("descripcion"),
                            objeto.getJSONObject("fecha").getString("date"),
                            objeto.getString("costo"),
                            objeto.getString("duracion"));
                    callList.add(post);

                } catch (JSONException e) {
                    Log.e("CallsAdapter", "Error de parsing: " + e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return callList;
    }

    public void getAllCalls(boolean flag, HashMap<String, String> parametros) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.getting_information));
        pDialog.setCancelable(false);
        if (flag) {
            pDialog.show();
        }
        try {
            if (Utils.isNetworkAvailable(this)) {
                JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        LocalPreferences.getApiUrl(this, SHARED_PREF_IDS.API_URL2) +
                                LocalPreferences.getAllCallsUrl(this, SHARED_PREF_IDS.GET_ALL_CALLS2),
                        new JSONObject(parametros),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                items = parseJson(response);
                                items_searched = new ArrayList<>();
                                for (int i = 0; i < items.size(); i++) {
                                    Call call = items.get(i);
                                    if (call.getName().toLowerCase().contains(query.toLowerCase()) || call.getDigitos().contains(query)) {
                                        items_searched.add(call);
                                    }
                                }

                                if (items_searched.size() > 0) {
                                    mCallsAdapter = new CallsAdapter(items_searched, getApplicationContext());
                                    recyclerView.setAdapter(mCallsAdapter);
                                    mCallsAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(SearchServiceResultsActivity.this, getResources().getString(R.string.no_results_were_founds), Toast.LENGTH_SHORT).show();
                                }
                                pDialog.dismiss();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                showDialog(error.getMessage(), getResources().getString(R.string.volley_error));
                            }
                        }
                );
                ZunCallAndroidApplication.getInstance().addToRequestQueue(jsArrayRequest);
            } else {
                pDialog.dismiss();
                showDialog(getResources().getString(R.string.no_internet_connection_body),
                        getResources().getString(R.string.connection_error));

            }

        } catch (Exception e) {
            pDialog.dismiss();
            e.printStackTrace();
        }
    }

    public void getCallsImport() {

        double totalCallsImport = 0.0;
        double totalCallsDuraton = 0.0;
        for (int i = 0; i < items_searched.size(); i++) {
            totalCallsImport += items_searched.get(i).getCost() != null ? Double.parseDouble(items_searched.get(i).getCost()) : 0.0;
            totalCallsDuraton += items_searched.get(i).getCost() != null ? Double.parseDouble(items_searched.get(i).getDuration()) : 0.0;
        }
        String message1 = String.format(getResources().getString(R.string.import_zuncall_message1), totalCallsDuraton);
        String message2 = String.format(getResources().getString(R.string.import_zuncall_message2), totalCallsImport);
        String title = getResources().getString(R.string.import_zuncall_title);
        showDialog(message1 + "  " + message2, title);
    }

    private void showDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
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

    public void setParams() {
        id_seco = ZunCallAndroidApplication.getInstance().getUserLogged().getId_seco();
        parametros.put("id_seco", String.valueOf(id_seco));
        parametros.put("action", "2");
        parametros.put("date", LocalPreferences.getDateToQuery(getApplicationContext(), SHARED_PREF_IDS.DATE_QUERY));
    }
}
