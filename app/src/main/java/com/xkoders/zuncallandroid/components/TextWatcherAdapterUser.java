package com.xkoders.zuncallandroid.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;
import com.xkoders.zuncallandroid.interfaces.TextWatcherListener;


public class TextWatcherAdapterUser implements TextWatcher {

    static String a;

    private final EditText view;
    private final EditText viewNext;
    private final TextWatcherListener listener;

    public TextWatcherAdapterUser(EditText editText, EditText editTextNext, TextWatcherListener listener) {
        this.view = editText;
        this.viewNext = editTextNext;
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        /*if (view.getText().toString().contains("@") && view.getText().toString().length() > 2) {
            if (ZunCallAndroidApplication.keyDel == 0) {
                String user = view.getText().toString();
                String domain = "get.hlg.tur.cu";
                view.setText(user.substring(0, user.length() - 1) + domain);
                view.setSelection(view.getText().length());
            } else {
                ZunCallAndroidApplication.keyDel = 0;
            }
            a = view.getText().toString();
        } else {
            view.setText(a);
        }*/


   /*     if (view.getText().toString().length() <= 14) {
            if (ZunCallAndroidApplication.keyDel == 0) {
                if (view.getText().toString().length() % 4 == 0 && view.getText().toString().length() > 0) {
                    String cpf = view.getText().toString();

                    String d = cpf.length() == 12 ? "-" : ".";
                    view.setText(cpf.substring(0, cpf.length() - 1) + d + cpf.substring(cpf.length() - 1, cpf.length()));

                    view.setSelection(view.getText().length());
                }
            } else {
                ZunCallAndroidApplication.keyDel = 0;
            }
            a = view.getText().toString();
        } else {
            view.setText(a);
        }
        listener.onTextChanged(view, s.toString());*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        a = view.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        /*if (s.toString().length() == 14) {
            if (viewNext != null) {
                viewNext.setFocusableInTouchMode(true);
                viewNext.requestFocus();
            } else {
                view.clearFocus();
            }
        }*/
    }
}