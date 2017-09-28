package com.xkoders.zuncallandroid.utils;

import android.content.SearchRecentSuggestionsProvider;

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.xkoders.zuncallandroid.utils.SearchableProvider";
    public static final int MODES = DATABASE_MODE_QUERIES;

    public SearchableProvider() {
        setupSuggestions(AUTHORITY,MODES);
    }
}
