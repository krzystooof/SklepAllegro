package pl.krzystooof.sklepallegro.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class mSharedPref {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Set<String> offersSet;
    Gson gson;
    String setName;
    String LogTag;

    public mSharedPref(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
        editor = sharedPref.edit();
        offersSet = new HashSet<>();
        gson = new Gson();
        setName = "offersSet";
        LogTag = "mSharedPref";
    }

    public mSharedPref() {
        //only for offerToJson and offerFromJson
        gson = new Gson();
        LogTag = "mSharedPref";
    }

    public String offerToJson(Offer offer){
        Log.i(LogTag, "offer to json called");
        return gson.toJson(offer);
    }

    public void save(ArrayList<Offer> offers) {
        sharedPref.edit().putBoolean("paused", true).commit();
        for (Offer offer : offers) {
            offersSet.add(offerToJson(offer));
        }
        editor.putStringSet(setName, offersSet).apply();
        Log.i(LogTag, "offers saved to SharedPref");
    }

    public Offer offerFromJson(String jsonString){
        Log.i(LogTag, "offer from json called");
        return gson.fromJson(jsonString, Offer.class);
    }

    public ArrayList<Offer> read() {
        ArrayList<Offer> offers = new ArrayList<>();

        //if paused - something is saved, so read
        if (sharedPref.getBoolean("paused", false)) {
            Set<String> emptySet = new HashSet<>();
            offersSet = sharedPref.getStringSet(setName, emptySet);
            for (String jsonString : offersSet) {
                offers.add(offerFromJson(jsonString));
            }
        }
        Log.i(LogTag, "offers readed from SharedPref");
        return offers;
    }
}
