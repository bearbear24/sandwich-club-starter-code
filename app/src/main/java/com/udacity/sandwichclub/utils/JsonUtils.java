package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray knownAsJsonArr = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < knownAsJsonArr.length(); i++) {
                alsoKnownAs.add(knownAsJsonArr.getString(i));
            }
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJsonArr = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJsonArr.length(); i++) {
                ingredients.add(ingredientsJsonArr.getString(i));
            }
            sandwich = new Sandwich(
                    mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
