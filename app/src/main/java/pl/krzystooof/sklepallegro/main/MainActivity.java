package pl.krzystooof.sklepallegro.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.Offers;

//TODO shared preferences, second screen
public class MainActivity extends AppCompatActivity {

    MainActivityRecycler recycler;
    String LogTag = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(LogTag, "onCreate: called");
        setContentView(R.layout.activity_main);
        ArrayList<Offer> offers = new ArrayList<>();

        //get Offers object
        new GetData(offers).execute();

        recycler = new MainActivityRecycler(offers);
    }

    class MainActivityRecycler {
        private String LogTag = "MainActivityRecycler";
        private RecyclerView recyclerView;
        private MainActivityRecyclerAdapter adapter;
        private LinearLayoutManager linearLayoutManager;
        private DividerItemDecoration itemDecorator;
        private ItemTouchHelper itemTouchHelper;

        MainActivityRecycler(ArrayList<Offer> offers) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_divider));
            recyclerView.addItemDecoration(itemDecorator);
            adapter = new MainActivityRecyclerAdapter(offers);
            recyclerView.setAdapter(adapter);
            itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);
            Log.i(LogTag, "created, items = " + adapter.getItemCount());
        }

        protected MainActivityRecyclerAdapter getAdapter() {
            return adapter;
        }

        protected void showSnackbar(String text, boolean durationLong) {
            Snackbar snackbar;
            if (durationLong) snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG);
            else snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT);
            snackbar.show();
            Log.i(LogTag, "Snackbar " + text + " shown");
        }

        class TouchHelper extends ItemTouchHelper.SimpleCallback {
            ColorDrawable backgroundColorLeft;
            ColorDrawable backgroundColorRight;
            Drawable icon;
            private MainActivityRecyclerAdapter adapter;

            public TouchHelper(MainActivityRecyclerAdapter adapter) {
                super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
                this.adapter = adapter;
                backgroundColorLeft = new ColorDrawable(Color.WHITE);
                backgroundColorRight = new ColorDrawable(Color.RED);
                icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_done_black_18dp);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if(viewHolder.getItemViewType()==0) {
                    //set element back to its original position
                    adapter.notifyItemChanged(position);
                    //left
                    if (direction == 4) {
                        showSnackbar("To może być akcja", false);
                        Log.i(LogTag, "mRecyclerTouchHelper: item no " + position + " swiped left");
                    }
                    //right
                    else if (direction == 8) {
                        showSnackbar("To może być inna akcja", false);
                        Log.i(LogTag, "mRecyclerTouchHelper: item no " + position + " swiped right");
                    }
                }
                else showSnackbar("Ten element się nie przesuwa", false);
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder.getItemViewType() == 0) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX,
                            dY, actionState, isCurrentlyActive);
                    View itemView = viewHolder.itemView;

                    int backgroundCornerOffset = 20;
                    int maxOffset = 250;

                    int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconBottom = iconTop + icon.getIntrinsicHeight();

                    if (dX > 0) { // Swiping to the right
//                    int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//                    int iconRight = itemView.getLeft() + iconMargin;
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                        backgroundColorRight.setBounds(itemView.getLeft(), itemView.getTop(),
                                itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                                itemView.getBottom());
                        backgroundColorRight.draw(c);
                        //set max swipe position
                        if (dX > maxOffset) {
                            onChildDraw(c, recyclerView, viewHolder, maxOffset,
                                    dY, actionState, false);
                        }

                    } else if (dX < 0) { // Swiping to the left
                        int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                        int iconRight = itemView.getRight() - iconMargin;
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                        backgroundColorLeft.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                                itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        backgroundColorLeft.draw(c);
                    }
                    icon.draw(c);
                }
            }
        }
    }


    class GetData extends AsyncTask<String, String, String> {
        ArrayList<Offer> offers;
        String jsonUrl;

        protected GetData(ArrayList<Offer> offers) {
            this.offers = offers;
            jsonUrl = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
            Log.i(LogTag, "GetData: created");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Offers offersObject = getOffers(jsonUrl);
                Log.i(LogTag, "GetData: offers downloaded, size = " + offersObject.getOffers().size());
                offersObject = filter(offersObject,50,1000,true);
                Log.i(LogTag, "GetData: offers filtered, size = " + offersObject.getOffers().size());
                //copy Offers object to recycler's ArrayList of Offer objects
                offers.clear();
                for (Offer offer : offersObject.getOffers()) {
                    offers.add(offer);
                }
                Log.i(LogTag, "GetData: copied offers to recycler's array");
            } catch (IOException exception) {
                recycler.showSnackbar("Brak połączenia", true);
                Log.e(LogTag, exception.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recycler.getAdapter().notifyDataSetChanged();
            Log.i(LogTag, "GetData: notified about data change, recycler items = " + recycler.getAdapter().getItemCount());
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
        private Offers filter(Offers offersObject, int minPrice, final int maxPrice, boolean sort){
            offersObject.getOffers().removeIf(p -> (p.getPrice().getAmount() < minPrice)|| p.getPrice().getAmount()>maxPrice);
            if(sort) offersObject.getOffers().sort((o1, o2) -> {
                double o1Price = o1.getPrice().getAmount();
                double o2Price = o2.getPrice().getAmount();
                return o1Price<o2Price ? -1 : o1Price == o2Price ? 0 : 1;
            });
            return offersObject;
        }
    }
}
