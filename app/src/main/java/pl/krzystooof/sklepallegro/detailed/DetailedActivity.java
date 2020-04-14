package pl.krzystooof.sklepallegro.detailed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.mSharedPref;
import pl.krzystooof.sklepallegro.main.MainActivity;
import pl.krzystooof.sklepallegro.main.MainActivityRecyclerAdapter;

public class DetailedActivity extends AppCompatActivity {
    String LogTag = "DetailedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //get passed offer
        Intent intent = getIntent();
        Offer offer = new mSharedPref().offerFromJson(intent.getStringExtra(String.valueOf(R.string.intent_offer)));

        setTitle(offer.getName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class DetailedActivityRecycler {
        private RecyclerView recyclerView;
        private MainActivityRecyclerAdapter adapter;
        private LinearLayoutManager linearLayoutManager;
        private DividerItemDecoration itemDecorator;

        DetailedActivityRecycler(Offer offer) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_divider));
            recyclerView.addItemDecoration(itemDecorator);
            adapter = new DetailedActivityRecyclerAdapter(offer);
            recyclerView.setAdapter(adapter);
            Log.i(LogTag, "recycler: created, items = " + adapter.getItemCount());
        }

        protected MainActivityRecyclerAdapter getAdapter() {
            return adapter;
        }
    }
}
