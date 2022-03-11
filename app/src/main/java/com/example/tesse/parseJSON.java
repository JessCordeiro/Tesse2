package com.example.tesse;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class parseJSON {

    private static final String TAG = parseJSON.class.getSimpleName();
    public static String currentJsonStr;

    public static HashMap<String, Card> codeToCard;
    public static Card currentCard;

    public static HashMap<String, ItiCreditCard> codeToItiCard;
    public static ItiCreditCard currentStudentMetroCard;
    public static boolean itiCard = false;


    public static void fetchStudentInfoFromCode(String cardCode, Context context) {

        if (codeToCard == null) //first time only, supposing we're dealing with one JSON file only
            loadJSONIntoMap(context);

        currentCard = codeToCard.get(cardCode);

    }

    public static void fetchStudentInfoFromCodeMetro(String itiCardCode, Context context) {

        if (codeToItiCard == null) //first time only, supposing we're dealing with one JSON file only
            loadJSONIntoMapIti(context);

        currentStudentMetroCard = codeToItiCard.get(itiCardCode);

    }

    private static void loadJSONIntoMap(Context context) {
        //load the json string from the json file of names list
        currentJsonStr = loadJSONFromAssets(context);

        try {
            //parse the json string
            JSONArray jsonArray = new JSONArray(currentJsonStr);
            codeToCard = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentCard = (JSONObject) jsonArray.get(i);

                int Number;
                String Name;


                Name = currentCard.getString("Name");
                Number = currentCard.getInt("Number");


                //add the current student to the map
                Card card = new Card( Number, Name);
                codeToCard.put( Name, card);

            }


        } catch (JSONException e) {
            Log.e(TAG, "Something wrong with parsing the JSON");
            e.printStackTrace();
        }

    }

    private static void loadJSONIntoMapIti(Context context) {
        //load the json string from the json file of names list
        currentJsonStr = loadJSONFromAssetsIti(context);

        try {
            //parse the json string
            JSONArray jsonArray = new JSONArray(currentJsonStr);
            codeToItiCard = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentCard = (JSONObject) jsonArray.get(i);

                int number;
                String name;

                name = currentCard.getString("Name");
                number = currentCard.getInt("Number");


                //add the current student to the map
                ItiCreditCard itiCreditCard = new ItiCreditCard(number, name);
                codeToItiCard.put(name, itiCreditCard);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Something wrong with parsing the JSON");
            e.printStackTrace();
        }

    }

    private static String loadJSONFromAssets(Context context) {

        String jsonStr = null;
        try {
            InputStream is = context.getAssets().open("JSON/CSE19_NamesList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonStr = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            Log.e(TAG, "couldn't load the json string");
            ex.printStackTrace();
            return null;
        }

        Log.i(TAG, "parsed the JSON successfully!");
        return jsonStr;

    }


    private static String loadJSONFromAssetsIti(Context context) {

        String jsonStr = null;
        try {
            InputStream is = context.getAssets().open("JSON/Iti_NamesList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonStr = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            Log.e(TAG, "couldn't load the json string");
            ex.printStackTrace();
            return null;
        }

        Log.i(TAG, "parsed the JSON successfully!");
        return jsonStr;

    }

}
