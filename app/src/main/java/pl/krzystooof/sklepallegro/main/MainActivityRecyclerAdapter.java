package pl.krzystooof.sklepallegro.main;

import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;

//TODO layout (text size, colors etc), swipes
public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Offer> offers;
    private String LogTag = "MainActivityRecyclerAdapter";

    public MainActivityRecyclerAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public static class mViewHolder0 extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView priceText;
        public ImageView imageView;
        public ConstraintLayout layout;
        public mViewHolder0(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            priceText = (TextView) itemView.findViewById(R.id.priceText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
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


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //MiddleMessage or EndMessage
        if(viewType == 1 || viewType ==2) {
            View listItem = layoutInflater.inflate(R.layout.activity_main_recycler_item_additional, parent, false);
            return new mViewHolder1(listItem);
        }
        //OfferList
        View listItem = layoutInflater.inflate(R.layout.activity_main_recycler_item, parent, false);
        return new mViewHolder0(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.i(LogTag, "BindViewHolder: position = " + position + ", viewType = " + viewType);
        //MiddleMessage
        if(viewType == 1){
            final mViewHolder1 mHolder = (mViewHolder1) holder;
            mHolder.yesText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mHolder.yesText.getContext(), "To git!",Toast.LENGTH_SHORT).show();
                }
            });
            mHolder.noText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mHolder.noText.getContext(),"Sorki. Poprawię się!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //EndMessage
        else if (viewType == 2){
            mViewHolder1 mHolder = (mViewHolder1) holder;
            mHolder.titleText.setText("Liczba ofert: " + offers.size());
            mHolder.yesText.setVisibility(View.GONE);
            mHolder.noText.setVisibility(View.GONE);
        }
        //OfferList
        else {
            mViewHolder0 mHolder = (mViewHolder0) holder;
            //after showing middle message offer no = position -1
            if(position>getItemCount()/2)position--;
            Offer offer = offers.get(position);
            mHolder.titleText.setText(offer.getName());
            mHolder.priceText.setText(offer.getPrice().getAmount() + " " + offer.getPrice().getCurrency());
            Glide.with(mHolder.imageView.getContext()).load(offer.getThumbnailUrl()).into(mHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        int offersSize = offers.size();
        //no offers
        if(offersSize == 0) return 0;
        //offers + additional (1 MiddleMessage)
        return offersSize + 1 + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        //no offers
        if(itemCount == 0) return 0;
        else {
            if (position == itemCount/2) return 1;
            else if (position == itemCount - 1) return 2;
            return 0;
        }
    }
}
