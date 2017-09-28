package com.xkoders.zuncallandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xkoders.zuncallandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelp extends Fragment {
    public static final String ARG_PAGE = "page";
    private TextView help_text_title;
    private int mPageNumber;

    public static FragmentHelp newInstance(int position) {
        FragmentHelp fragmentHelp = new FragmentHelp();
        Bundle localBundle = new Bundle();
        localBundle.putInt("page", position);
        fragmentHelp.setArguments(localBundle);
        return fragmentHelp;
    }

    public int getPageNumber() {
        return this.mPageNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageNumber = getArguments().getInt("page");
        if ((savedInstanceState != null) && savedInstanceState.containsKey(ARG_PAGE)) {
            this.mPageNumber = savedInstanceState.getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_help_slide_screen_page, container, false);
        ImageView help_image = (ImageView) rootView.findViewById(R.id.help_image);
        TextView help_text_title = (TextView) rootView.findViewById(R.id.help_text_title);

        switch (mPageNumber) {
            case 0:
                help_image.setImageResource(R.drawable.help1);
                help_text_title.setText(getString(R.string.help_login));
                break;
            case 1:
                help_image.setImageResource(R.drawable.help2);
                help_text_title.setText(getString(R.string.help_get_calls));
                break;
            case 2:
                help_image.setImageResource(R.drawable.help3);
                help_text_title.setText(getString(R.string.help_get_calls_import));
                break;
            case 3:
                help_image.setImageResource(R.drawable.help4);
                help_text_title.setText(getString(R.string.help_add_to_contact));
                break;
            case 4:
                help_image.setImageResource(R.drawable.help5);
                help_text_title.setText(getString(R.string.help_add_set_query_date));
                break;
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_PAGE, mPageNumber);
    }

}
