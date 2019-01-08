package malaria.com.malaria.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MainPreferences {
    private static final String NAME = "MainPreferences";
    public static final String FIRST_TIME_APP = "firsttime";


    private SharedPreferences activityPreferences;
    private SharedPreferences.Editor editor;

    public MainPreferences(Context context) {
        activityPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = activityPreferences.edit();
    }

    public String getString(String key, String def) {
        return activityPreferences.getString(key, def);
    }

    public boolean contains(String key) { return activityPreferences.contains(key); }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean def) {
        return activityPreferences.getBoolean(key, def);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int getInt(String key, int def) {
        return activityPreferences.getInt(key, def);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void removeTag(String key) {
        editor.remove(key);
        editor.commit();
    }
}
