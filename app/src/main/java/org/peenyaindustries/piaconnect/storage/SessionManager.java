package org.peenyaindustries.piaconnect.storage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.peenyaindustries.piaconnect.activities.RegisterActivity;

import java.util.HashMap;

public class SessionManager {

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_URL = "url";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_EXCERPT = "excerpt";
    public static final String KEY_DATE = "date";
    public static final String KEY_COMMENT_COUNT = "commentCount";
    public static final String KEY_THUMBNAIL_URL = "thumbnail";
    public static final String KEY_IMAGE_URL = "image";
    //Shared Preference File Name
    private static final String PREF_NAME = "PIA_CONNECT";
    //Directory Name
    private static final String KEY_CATEGORIES_NAME = "categories";
    //
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_ROLE = "role";
    //Shared Preference Mode
    int PRIVATE_MODE = 0;
    //Shared Preference
    private SharedPreferences pref;
    //Editor for Shared Preferences
    private SharedPreferences.Editor editor;
    private Context _context;

    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create Login Session
    public void createLoginSession(String name, String email, String mobile, String role) {

        //Storing login value as true
        editor.putBoolean(IS_LOGIN, true);

        //Storing user credentials in shared preferences
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_ROLE, role);

        //Commit Changes
        editor.commit();
    }

    //Get Stored Session data
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
        return user;
    }

    //Check User Login Status
    public void checkLogin() {
        if (!isLoggedIn()) {
            Intent i = new Intent(_context, RegisterActivity.class);
            //Closing all activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Add new flag to start new activity
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //starting LoginActivity
            _context.startActivity(i);
        }
    }

    //Logout User - Clearing all the session data
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, RegisterActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    //Quick Check for Login status
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    //Store Current Post Details
    public void storeData(String id, String type, String url, String status, String title, String content, String excerpt, String date, String comment, String category, String thumbnail, String image) {

        editor.putString(KEY_ID, id);
        editor.putString(KEY_TYPE, type);
        editor.putString(KEY_URL, url);
        editor.putString(KEY_STATUS, status);
        editor.putString(KEY_TITLE, title);
        editor.putString(KEY_CONTENT, content);
        editor.putString(KEY_EXCERPT, excerpt);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_COMMENT_COUNT, comment);
        editor.putString(KEY_CATEGORY, category);
        editor.putString(KEY_THUMBNAIL_URL, thumbnail);
        editor.putString(KEY_IMAGE_URL, image);

        //Commit Changes
        editor.commit();
    }

    //Get Stored Post data
    public HashMap<String, String> getPostDetails() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(KEY_ID, pref.getString(KEY_ID, null));
        data.put(KEY_TYPE, pref.getString(KEY_TYPE, null));
        data.put(KEY_URL, pref.getString(KEY_URL, null));
        data.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
        data.put(KEY_TITLE, pref.getString(KEY_TITLE, null));
        data.put(KEY_CONTENT, pref.getString(KEY_CONTENT, null));
        data.put(KEY_EXCERPT, pref.getString(KEY_EXCERPT, null));
        data.put(KEY_DATE, pref.getString(KEY_DATE, null));
        data.put(KEY_COMMENT_COUNT, pref.getString(KEY_COMMENT_COUNT, null));
        data.put(KEY_CATEGORY, pref.getString(KEY_CATEGORY, null));
        data.put(KEY_THUMBNAIL_URL, pref.getString(KEY_THUMBNAIL_URL, null));
        data.put(KEY_IMAGE_URL, pref.getString(KEY_IMAGE_URL, null));

        return data;
    }
}
