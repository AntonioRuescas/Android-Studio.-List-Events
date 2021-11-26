package com.example.eventmaps;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class PrefConfig {

    private static final String LIST_KEY = "list_key";

    //Metodo para escribir en las preferencias
    public static void writeListInPref(Context context, ArrayList<Events> datas) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(datas);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    //Metodo para leer de las preferencias
    public static ArrayList<Events> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Events>>() {
        }.getType();
        ArrayList<Events> datas = gson.fromJson(jsonString, type);
        return datas;
    }

}