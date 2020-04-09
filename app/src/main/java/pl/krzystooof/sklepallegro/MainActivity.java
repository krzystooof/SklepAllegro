package pl.krzystooof.sklepallegro;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.Offers;

public class MainActivity extends AppCompatActivity {

    ArrayList<Offer> offers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jsonUrl = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
        offers = new ArrayList<>();

        //get Offers object
        new GetData().execute(jsonUrl);


    }

    class GetData extends AsyncTask<String, String, String> {
        Offers offersList;
        @Override
        protected String doInBackground(String... strings) {
            try {
                offersList = getOffers(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //cast Offers object to ArrayList of Offer objects and clear first to save memory
            offers = new ArrayList<>(offersList.getOffers());
            offers.clear();
        }

        public Offers getOffers(String url) throws IOException {
            //connect
            URL dataUrl = new URL(url);
            InputStream inputStream = dataUrl.openStream();

            //cast data from connection to String
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String jsonString = s.hasNext() ? s.next() : "";

            //cast string to objects
            Gson gson = new Gson();
            return gson.fromJson(jsonString, Offers.class);
        }
    }
}
