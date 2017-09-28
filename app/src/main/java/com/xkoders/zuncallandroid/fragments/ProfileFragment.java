package com.xkoders.zuncallandroid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xkoders.zuncallandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private EditText profile_user_name, profile_user_email, profile_user_phone_number, profile_user, profile_user_password;
    private TextView password_counter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getWidgetsReference();
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
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    //call update function
                    done = true;
                }
                return done;
            }
        });
    }

    public void getWidgetsReference() {
        profile_user_name = (EditText) getView().findViewById(R.id.profile_user_name);
        profile_user_email = (EditText) getView().findViewById(R.id.profile_user_email);
        profile_user = (EditText) getView().findViewById(R.id.profile_user);
        profile_user_password = (EditText) getView().findViewById(R.id.profile_user_password);
        password_counter = (TextView) getView().findViewById(R.id.password_counter);
    }
}
