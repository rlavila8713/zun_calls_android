package com.xkoders.zuncallandroid.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;
import com.xkoders.zuncallandroid.components.ClearableEditText;
import com.xkoders.zuncallandroid.components.TextWatcherAdapterUser;
import com.xkoders.zuncallandroid.constants.SHARED_PREF_IDS;
import com.xkoders.zuncallandroid.database.DBHelper;
import com.xkoders.zuncallandroid.interfaces.OnAsyncTaskFinished;
import com.xkoders.zuncallandroid.interfaces.OnContentChanged;
import com.xkoders.zuncallandroid.models.ZunCallUser;
import com.xkoders.zuncallandroid.utils.LocalPreferences;
import com.xkoders.zuncallandroid.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements OnClickListener, OnAsyncTaskFinished, OnContentChanged {
    private EditText user;
    private ClearableEditText editTextUser;
    private EditText password;
    private CheckBox rememberData;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWidgetsReference();
        boolean checked = LocalPreferences.isDataRemebered(getApplicationContext(), SHARED_PREF_IDS.DATA_REMEMBERED);
        rememberData.setChecked(checked);
        if (verifyDataSetRemembered()) {
            editTextUser.setText(LocalPreferences.getUserNameData(getApplicationContext(), SHARED_PREF_IDS.USER_NAME_DATA));
        }
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean done = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptToLogin();
                    done = true;
                }
                return done;
            }
        });

        rememberData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LocalPreferences.setRememberData(getApplicationContext(), SHARED_PREF_IDS.DATA_REMEMBERED, isChecked, editTextUser.getText().toString());
            }
        });
    }

    private boolean verifyDataSetRemembered() {
        return LocalPreferences.isDataRemebered(getApplicationContext(), SHARED_PREF_IDS.DATA_REMEMBERED);
    }

    public void getWidgetsReference() {
        ImageView closeButton = (ImageView) findViewById(R.id.imageViewClose);
        editTextUser = (ClearableEditText) findViewById(R.id.editTextUser);
        password = (EditText) findViewById(R.id.editTextPassword);
        rememberData = (CheckBox) findViewById(R.id.checkBox1);
        RelativeLayout layoutAccept = (RelativeLayout) findViewById(R.id.layoutAccept);
        RelativeLayout layoutRecoverPassword = (RelativeLayout) findViewById(R.id.layoutRecoverPassword);
        layoutAccept.setOnClickListener(this);
        layoutRecoverPassword.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        editTextUser.addTextChangedListener(new TextWatcherAdapterUser(editTextUser, password, editTextUser));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAccept:
                attemptToLogin();
               /* mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);*/
                break;
            case R.id.layoutRecoverPassword:
                //startActivity(new Intent(this, Recover.class));
                break;
            case R.id.imageViewClose:
                finish();
                break;
        }
    }

    private void attemptToLogin() {

        int id_seco = validateEmailAndPassword();
        HashMap<String, Integer> parametros = new HashMap<>();
        parametros.put("action", 1);
        parametros.put("id_seco", id_seco);

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.login_in_progress));
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            if (Utils.isNetworkAvailable(this)) {
                JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        LocalPreferences.getApiUrl(this, SHARED_PREF_IDS.API_URL2) +
                                LocalPreferences.getLoginUrl(this, SHARED_PREF_IDS.LOGIN2),
                        new JSONObject(parametros),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (parseJsonResult(response)) {
                                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                                        dbHelper.deleteAllUserExceptActual(
                                                ZunCallAndroidApplication.getInstance().getUserLogged()
                                        );
                                        startActivity(mIntent);
                                        pDialog.dismiss();
                                        finish();
                                    } else {
                                        pDialog.dismiss();
                                        showDialog(parseJsonError(response), getResources().getString(R.string.volley_error));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                if (error != null) {
                                    Log.e("LOGIN", error.getMessage());
                                    showDialog(error.getMessage(), getResources().getString(R.string.volley_error));
                                }
                            }
                        });
                mIntent = new Intent(this, MainActivity.class);
                ZunCallAndroidApplication.getInstance().addToRequestQueue(jsArrayRequest);

            } else {
                pDialog.dismiss();
                showDialog(getResources().getString(R.string.no_internet_connection_body),
                        getResources().getString(R.string.connection_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean parseJsonResult(JSONObject response) throws JSONException {
        return Boolean.parseBoolean(response.getString("success"));
    }

    private String parseJsonError(JSONObject response) throws JSONException {

        String error = "";
        switch (response.getString("errors")) {
            case "user_exists":
                error = getResources().getString(R.string.user_exists);
                break;

        }
        return error;
    }

    private int validateEmailAndPassword() {
        boolean userExist = false;
        DBHelper dbHelper = new DBHelper(this);
        int id_seco = -1;

        if (!TextUtils.isEmpty(editTextUser.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
            ArrayList<ZunCallUser> mZunCallUsersList = dbHelper.getAllUsers();
            for (int i = 0; i < mZunCallUsersList.size(); i++) {
                if (mZunCallUsersList.get(i).getEmail().equals(editTextUser.getText().toString())) {
                    userExist = true;
                    if (mZunCallUsersList.get(i).getPassword().equals(password.getText().toString())) {
                        ZunCallAndroidApplication.getInstance().setUserLogged(mZunCallUsersList.get(i));
                        id_seco = mZunCallUsersList.get(i).getId_seco();
                        if (id_seco > 0) {
                            return mZunCallUsersList.get(i).getId_seco();
                        } else {
                            showDialog(getResources().getString(R.string.user_does_not_exist_on_zuncall),
                                    getResources().getString(R.string.error));
                        }
                    } else {
                        showDialog(getResources().getString(R.string.password_incorrect),
                                getResources().getString(R.string.error));
                        break;
                    }
                }
            }
            if (!userExist) {
                showDialog(getResources().getString(R.string.user_does_not_exist),
                        getResources().getString(R.string.error));
            }
        } else {
            showDialog(getResources().getString(R.string.empty_fields),
                    getResources().getString(R.string.information));

        }
        return -1;
    }


    @Override
    public void onAsyncTaskFinished(String response, int id, int from) {

    }

    @Override
    public void onContentChanged(Bundle bundle) {

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


}
