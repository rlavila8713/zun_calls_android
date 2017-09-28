package com.xkoders.zuncallandroid.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.xkoders.zuncallandroid.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FloatingActionButton mFabZunCalls_getCalls;
    private FloatingActionButton mFabZunCalls_getImport;
    private FloatingActionButton mFabZunCalls_getContactsList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private CallsAdapter mCallsAdapter;
    private List<Call> items;
    private int id_seco;
    private HashMap<String, String> parametros;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calls, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidgetsReferences();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items = new ArrayList<>();

        parametros = new HashMap<>();
        setParams();
        getAllCalls(true, parametros);

//        recyclerView.setAdapter(new CallsAdapter(items, getActivity()));

        refreshLayout.setOnRefreshListener(this);
        // Seteamos los colores que se usarán a lo largo de la animación
        refreshLayout.setColorSchemeResources(
                R.color.blue_accent0,
                R.color.blue_accent1,
                R.color.blue_accent2,
                R.color.blue_accent3
        );

        mFabZunCalls_getCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setParams();
                getAllCalls(true, parametros);
            }
        });
        mFabZunCalls_getImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallsImport();
            }
        });
        mFabZunCalls_getContactsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getWidgetsReferences() {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_calls);
        refreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh_calls);
        mFabZunCalls_getCalls = (FloatingActionButton) getView().findViewById(R.id.mFabZunCalls_getCalls);
        mFabZunCalls_getImport = (FloatingActionButton) getView().findViewById(R.id.mFabZunCalls_getImport);
        mFabZunCalls_getContactsList = (FloatingActionButton) getView().findViewById(R.id.mFabZunCalls_getContactsList);
    }

    public void refresh() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          setParams();
                                          getAllCalls(false, parametros);
                                          if (refreshLayout.isRefreshing()) {
                                              refreshLayout.setRefreshing(false);
                                          }
                                      }
                                  }, 2000
        );
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    public void getAllCalls(boolean flag, HashMap<String, String> parametros) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.getting_information));
        pDialog.setCancelable(false);
        if (flag) {
            pDialog.show();
        }
        try {
            if (Utils.isNetworkAvailable(getActivity())) {
                JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        LocalPreferences.getApiUrl(getActivity(), SHARED_PREF_IDS.API_URL2) +
                                LocalPreferences.getAllCallsUrl(getActivity(), SHARED_PREF_IDS.GET_ALL_CALLS2),
                        new JSONObject(parametros),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                items = parseJson(response);
                                if (items.size() > 0) {
                                    mCallsAdapter = new CallsAdapter(items, getActivity());
                                    recyclerView.setAdapter(mCallsAdapter);
                                    mCallsAdapter.notifyDataSetChanged();
                                }
                                pDialog.dismiss();
                                /*if (LocalPreferences.isCachePreferred(getActivity(), SHARED_PREF_IDS.STORE_CALLS)) {
                                    DBHelper dbHelper = new DBHelper(getActivity());
                                    for (int i = 0; i < items.size(); i++) {

                                        // TODO: 25/09/2016 para las pruebas usar uin retorno boolean del metodo insertCall
                                        // TODO: 25/09/2016 ya en produccion cambiarlo
                                        if (!dbHelper.insertCall(items.get(i))) {
                                            showDialog(getResources().getString(R.string.error_writing_in_db), getResources().getString(R.string.sqlite_error));
                                            break;
                                        }
                                    }
                                }*/
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                if(error!=null)
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

    private void showDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
            showDialog(getResources().getString(R.string.no_results_were_founds)+" ("+LocalPreferences.getDateToQuery(getActivity(),SHARED_PREF_IDS.DATE_QUERY)+")", getResources().getString(R.string.information));
        }
        return callList;
    }

    public void getCallsImport() {
        double totalCallsImport = 0.0;
        double totalCallsDuraton = 0.0;
        for (int i = 0; i < items.size(); i++) {
            totalCallsImport += items.get(i).getCost() != null ? Double.parseDouble(items.get(i).getCost()) : 0.0;
            totalCallsDuraton += items.get(i).getCost() != null ? Double.parseDouble(items.get(i).getDuration()) : 0.0;
        }
        String message1 = String.format(getResources().getString(R.string.import_zuncall_message1), totalCallsDuraton);
        String message2 = String.format(getResources().getString(R.string.import_zuncall_message2), totalCallsImport);
        String title = getResources().getString(R.string.import_zuncall_title);
        showDialog(message1 + "  " + message2, title);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void setParams()
    {
        id_seco = ZunCallAndroidApplication.getInstance().getUserLogged().getId_seco();
        parametros.put("id_seco", String.valueOf(id_seco));
        parametros.put("action", "2");
        parametros.put("date", LocalPreferences.getDateToQuery(getActivity(), SHARED_PREF_IDS.DATE_QUERY));
    }
}
