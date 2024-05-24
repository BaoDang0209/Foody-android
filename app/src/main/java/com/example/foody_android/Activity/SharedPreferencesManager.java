package com.example.foody_android.Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";

    private static SharedPreferencesManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AUTH_TOKEN, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void clearAuthToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_AUTH_TOKEN);
        editor.apply();
    }

    public void saveUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // -1 if not found
    }

    public void clearUserId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}
