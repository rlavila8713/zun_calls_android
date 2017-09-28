package com.xkoders.zuncallandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;
import com.xkoders.zuncallandroid.database.DBHelper;
import com.xkoders.zuncallandroid.models.ZunCallUser;

public class ProfileActivity extends AppCompatActivity {
    private static final String EXTRA_USER = "com.xkoders.zucallandroid.profile_user";
    private static final String EXTRA_USER_EMAIL = "com.xkoders.zucallandroid.profile_user_email";
    private static final String EXTRA_USER_NAME = "com.xkoders.zucallandroid.profile_user_name";

    private EditText profile_user_name, profile_user_email, profile_user_phone_number, profile_user, profile_user_password;
    private TextView password_counter;
    private Button update_user_profile;

    public static void createInstance(Activity activity, ZunCallUser mZunCallUser) {
        Intent intent = getLaunchIntent(activity, mZunCallUser);
        activity.startActivity(intent);
    }

    public static Intent getLaunchIntent(Context context, ZunCallUser mZunCallUser) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, mZunCallUser.getName());
        intent.putExtra(EXTRA_USER_EMAIL, mZunCallUser.getEmail());
        intent.putExtra(EXTRA_USER_NAME, mZunCallUser.getUsername());


        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        getWidgetsReference();

        Intent i = getIntent();
        updateWidgetsValues(i);


        update_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = updateProfile();
                Toast.makeText(ProfileActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
            }
        });

        profile_user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tamanoString = String.valueOf(s.length());
                password_counter.setText(tamanoString);
            }
        });

        profile_user_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean done = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    boolean result = updateProfile();
                    done = true;
                    Toast.makeText(ProfileActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                }
                return done;
            }
        });
    }

    private void updateWidgetsValues(Intent i) {
        String name = i.getStringExtra(EXTRA_USER);
        String user_email = i.getStringExtra(EXTRA_USER_EMAIL);
        String username = i.getStringExtra(EXTRA_USER_NAME);

        profile_user.setText(username);
        profile_user_email.setText(user_email);
        profile_user_name.setText(name);
    }

    private boolean updateProfile() {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ZunCallUser mZunCallUser = new ZunCallUser(ZunCallAndroidApplication.getInstance().getUserLogged().getId(),
                profile_user.getText().toString(),
                profile_user_email.getText().toString(),
                profile_user_name.getText().toString(),
                profile_user_password.getText().toString());
        return dbHelper.updateProfile(mZunCallUser);
    }

    public void getWidgetsReference() {
        profile_user_name = (EditText) findViewById(R.id.profile_user_name);
        profile_user_email = (EditText) findViewById(R.id.profile_user_email);
        profile_user = (EditText) findViewById(R.id.profile_user);
        profile_user_password = (EditText) findViewById(R.id.profile_user_password);
        password_counter = (TextView) findViewById(R.id.password_counter);
        update_user_profile = (Button) findViewById(R.id.update_user_profile);
    }
}
