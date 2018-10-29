package com.amostra.net.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.amostra.net.api.API;
import com.amostra.net.data.model.product.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserPrefManager {

    private SharedPreferences sharedPreferences;
    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "pref_user";

    public static final String USER_KEY = "user_app";
    public static final String TOKEN_KEY = "key_app";
    public static final String LOGIN_STATUS = "login_status";
    public static final String ORDER = "order";
    public static final String PRODUCTS = "products";

    public UserPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void saveObject(String serializedObjectKey, Object object) {

        String serializedObject = API.toJson(object);
        sharedPreferences.edit().putString(serializedObjectKey, serializedObject).apply();
    }

    public <GenericClass> GenericClass loadObject(String key, Class<GenericClass> classType) {
        if (sharedPreferences.contains(key)) {
            return API.fromJson(sharedPreferences.getString(key, ""), classType);
        }
        return null;
    }

    public <GenericClass> List<GenericClass> loadListObject(String key, Type classType) {
        if (sharedPreferences.contains(key)) {
            return  new Gson().fromJson(sharedPreferences.getString(key, ""), classType);
        }
        return null;
    }

    public List<Product> loadListProducts(String key) {
        if (sharedPreferences.contains(key)) {
            Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
            return  new Gson().fromJson(sharedPreferences.getString(key, ""), listType);
        }
        return null;
    }

    public boolean getValueBoolean(String key) {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getBoolean(key, false);
        }else return false;
    }

    public void setValueBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
}
