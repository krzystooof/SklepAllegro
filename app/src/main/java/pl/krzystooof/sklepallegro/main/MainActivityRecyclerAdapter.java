package pl.krzystooof.sklepallegro.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;
import pl.krzystooof.sklepallegro.data.mSharedPref;
import pl.krzystooof.sklepallegro.detailed.DetailedActivity;


public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Offer> offers;
    private String LogTag = "MainActivityRecyclerAdapter";

    public MainActivityRecyclerAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    //3 types of views implemented: OfferList (default), MiddleMessage and EndMessage

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //MiddleMessage or EndMessage
        if (viewType == 1 || viewType == 2) {
            View listItem = layoutInflater.inflate(R.layout.recycler_item_additional, parent, false);
            return new mViewHolder1(listItem);
        }
        //OfferList
        View listItem = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new mViewHolder0(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //Log.i(LogTag, "BindViewHolder: position = " + position + ", viewType = " + viewType);
        //MiddleMessage
        if (viewType == 1) {
            final mViewHolder1 mHolder = (mViewHolder1) holder;
            mHolder.yesText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mHolder.yesText.getContext(), "I jak się podoba?", Toast.LENGTH_SHORT).show();
                }
            });
            mHolder.noText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mHolder.noText.getContext(), "Warto spróbować!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //EndMessage
        else if (viewType == 2) {
            mViewHolder1 mHolder = (mViewHolder1) holder;
            mHolder.titleText.setTextSize(14);
            mHolder.titleText.setText("Liczba ofert: " + offers.size());
            mHolder.yesText.setVisibility(View.GONE);
            mHolder.noText.setVisibility(View.GONE);
        }
        //OfferList
        else {
            mViewHolder0 mHolder = (mViewHolder0) holder;
            //after showing middle message offer no = position -1
            if (position > getItemCount() / 2) position--;
            Offer offer = offers.get(position);

            //prevent part of title showing off screen
            mHolder.titleText.setText(offer.getName());

            mHolder.priceText.setText(String.format ("%.2f", offer.getPrice().getAmount()) + " " + offer.getPrice().getCurrency());

            Glide.with(mHolder.imageView.getContext()).load(offer.getThumbnailUrl()).into(mHolder.imageView);

            mHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra(String.valueOf(R.string.intent_offer), new mSharedPref().offerToJson(offer));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int offersSize = offers.size();

        //no offers - no views
        if (offersSize == 0) return 0;

        //offers + additional (1 MiddleMessage, 1 EndMessage)
        return offersSize + 1 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();

        //no offers
        if (itemCount == 0) return 0;

        else {
            if (position == itemCount / 2) return 1;
            else if (position == itemCount - 1) return 2;
            return 0;
        }
    }

    public static class mViewHolder0 extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView priceText;
        public ImageView imageView;
        public ConstraintLayout layout;

        public mViewHolder0(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            priceText = itemView.findViewById(R.id.priceText);
            imageView = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public static class mViewHolder1 extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView yesText;
        public TextView noText;
        public ConstraintLayout layout;

        public mViewHolder1(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.mainText);
            yesText = itemView.findViewById(R.id.yesText);
            noText = itemView.findViewById(R.id.noText);
            layout = itemView.findViewById(R.id.additionalLayout);
        }
    }
}
