package pl.krzystooof.sklepallegro.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;

public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<MainActivityRecyclerAdapter.ViewHolder> {
    private ArrayList<Offer> offers;

    public MainActivityRecyclerAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    //default - offer list
    public static class ViewHolderOfferList extends ViewHolder{
        public TextView titleText;
        public TextView priceText;
        public ImageView imageView;
        public ConstraintLayout layout;
        public ViewHolderOfferList(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            priceText = (TextView) itemView.findViewById(R.id.priceText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    public static class ViewHolder2 extends ViewHolder {
        public TextView titleText;
        public TextView priceText;
        public ImageView imageView;
        public ConstraintLayout layout;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            priceText = (TextView) itemView.findViewById(R.id.priceText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public MainActivityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.activity_main_recycler_item, parent, false);
        if (viewType == 1) return new ViewHolderOfferList(listItem);
        else  return new ViewHolder2(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityRecyclerAdapter.ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        switch (getItemViewType(position)){
            case 1:
                ViewHolderOfferList viewHolderOfferList = (ViewHolderOfferList) holder;
                viewHolderOfferList.titleText.setText(offer.getName());
                viewHolderOfferList.priceText.setText(offer.getPrice().getAmount() + " " + offer.getPrice().getCurrency());
                Glide.with(viewHolderOfferList.imageView.getContext()).load(offer.getThumbnailUrl()).into(viewHolderOfferList.imageView);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    @Override
    public int getItemViewType(int position) {
        //if(position == getItemCount()/2) return 2;
        return 1;
    }
}
