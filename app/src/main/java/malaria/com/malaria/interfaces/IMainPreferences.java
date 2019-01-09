package malaria.com.malaria.interfaces;

public interface IMainPreferences {
    String FIRST_TIME_APP = "FIRST_TIME_APP";
    String THRESHOLD = "THRESHOLD";

    String getString(String key, String def);

    boolean contains(String key);

    void putString(String key, String value);

    boolean getBoolean(String key, boolean def);

    void putBoolean(String key, boolean value);

    int getInt(String key, int def);

    void putInt(String key, int value);

    void removeTag(String key);

    double getFloat(String key, float def);

    void putFloat(String key, float value);

    double getDouble(String key, double def);

    void putDouble(String key, double value);
}
