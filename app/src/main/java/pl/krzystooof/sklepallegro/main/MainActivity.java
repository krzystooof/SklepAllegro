package pl.krzystooof.sklepallegro.main;

import android.os.AsyncTask;
import android.os.Bundle;

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
import java.util.List;
import java.util.Scanner;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.Offers;

public class MainActivity extends AppCompatActivity {

   mRecycler recycler;
   ArrayList<Offer> offers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jsonUrl = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
        offers = new ArrayList<>();

        //get Offers object
        new GetData().execute(jsonUrl);

        recycler = new mRecycler();
    }

    class mRecycler{
        private RecyclerView recyclerView;
        private MainAcitivityRecyclerAdapter adapter;
        private LinearLayoutManager linearLayoutManager;
        private DividerItemDecoration itemDecorator;
        private Snackbar snackbar;
        private String snackbarText;

        mRecycler(){
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_divider));
            recyclerView.addItemDecoration(itemDecorator);
            snackbar = Snackbar.make(recyclerView, "",Snackbar.LENGTH_LONG);
            adapter = new MainAcitivityRecyclerAdapter(offers);
            recyclerView.setAdapter(adapter);
        }

        public MainAcitivityRecyclerAdapter getAdapter() {
            return adapter;
        }

        public Snackbar getSnackbar() {
            return snackbar;
        }

        public String getSnackbarText() {
            return snackbarText;
        }
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recycler.getAdapter().notifyDataSetChanged();
        }

        public void getOffers(String url) throws IOException {
            //connect
            URL dataUrl = new URL(url);
            InputStream inputStream = dataUrl.openStream();

            //cast data from connection to String
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String jsonString = s.hasNext() ? s.next() : "";

            //cast string to objects
            Gson gson = new Gson();
            Offers offersObject = gson.fromJson(jsonString, Offers.class);
            //cast Offers object to ArrayList of Offer objects
            for (Offer offer : offersObject.getOffers()){
                offers.add(offer);
            }
        }
    }
}
