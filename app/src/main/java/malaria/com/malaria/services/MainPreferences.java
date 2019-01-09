package malaria.com.malaria.services;

import android.content.Context;
import android.content.SharedPreferences;
import malaria.com.malaria.interfaces.IMainPreferences;

public class MainPreferences implements IMainPreferences {
    private static final String NAME = "MainPreferences";

    private SharedPreferences activityPreferences;
    private SharedPreferences.Editor editor;

    public MainPreferences(Context context) {
        activityPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = activityPreferences.edit();
    }

    @Override
    public String getString(String key, String def) {
        return activityPreferences.getString(key, def);
    }

    @Override
    public boolean contains(String key) {
        return activityPreferences.contains(key);
    }

    @Override
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return activityPreferences.getBoolean(key, def);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public int getInt(String key, int def) {
        return activityPreferences.getInt(key, def);
    }

    @Override
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public void removeTag(String key) {
        editor.remove(key);
        editor.commit();
    }

    @Override
    public double getFloat(String key, float def) {
        return activityPreferences.getFloat(key, def);
    }

    @Override
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    @Override
    public double getDouble(String key, double def) {
        return Double.longBitsToDouble(activityPreferences.getLong(key, Double.doubleToLongBits(def)));
    }

    @Override
    public void putDouble(String key, double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }
}
