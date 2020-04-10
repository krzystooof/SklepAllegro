package pl.krzystooof.sklepallegro.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.Offers;

public class MainActivity extends AppCompatActivity {

   mRecycler recycler;
   String LogTag = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(LogTag, "onCreate: called");
        setContentView(R.layout.activity_main);
        String jsonUrl = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
        ArrayList<Offer> offers = new ArrayList<>();

        //get Offers object
        new GetData(offers).execute(jsonUrl);

        recycler = new mRecycler(offers);
    }

    class mRecycler{
        private RecyclerView recyclerView;
        private MainAcitivityRecyclerAdapter adapter;
        private LinearLayoutManager linearLayoutManager;
        private DividerItemDecoration itemDecorator;

        mRecycler(ArrayList<Offer> offers){
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_divider));
            recyclerView.addItemDecoration(itemDecorator);
            adapter = new MainAcitivityRecyclerAdapter(offers);
            recyclerView.setAdapter(adapter);
            Log.i(LogTag, "mRecycler: created");
        }

        protected MainAcitivityRecyclerAdapter getAdapter() {
            return adapter;
        }

        protected void showSnackbar(String text, boolean durationLong) {
            if (durationLong) Snackbar.make(recyclerView, text,Snackbar.LENGTH_LONG).show();
            else Snackbar.make(recyclerView, text,Snackbar.LENGTH_SHORT).show();
            Log.i(LogTag, "mRecycler: Snackbar "+ text + " shown");
        }
    }

    class GetData extends AsyncTask<String, String, String> {
        ArrayList<Offer> offers;
        protected GetData(ArrayList<Offer> offers){
            this.offers = offers;
            Log.i(LogTag, "GetData: created");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Offers offersObject = getOffers(strings[0]);
                Log.i(LogTag, "GetData: offers size = "+offersObject.getOffers().size());
                //copy Offers object to recycler's ArrayList of Offer objects
                offers.clear();
                for (Offer offer : offersObject.getOffers()){
                    offers.add(offer);
                }
                Log.i(LogTag, "GetData: copied offers to recycler's array");
            } catch (IOException exception) {
                recycler.showSnackbar("Connection error",true);
                Log.e(LogTag,exception.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recycler.getAdapter().notifyDataSetChanged();
        }

        private Offers getOffers(String url) throws IOException {
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
