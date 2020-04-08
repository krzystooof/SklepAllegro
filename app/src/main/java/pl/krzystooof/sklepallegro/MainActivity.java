package pl.krzystooof.sklepallegro;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import pl.krzystooof.sklepallegro.data.Offers;

public class MainActivity extends AppCompatActivity {

    String jsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonUrl = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
        new GetData().execute(jsonUrl);

    }

    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                getOffers(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public Offers getOffers(String url) throws IOException {
            URL dataUrl = new URL(url);
            InputStream inputStream = dataUrl.openStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String jsonString = s.hasNext() ? s.next() : "";

            Gson gson = new Gson();
            return gson.fromJson(jsonString, Offers.class);
        }
    }
}
