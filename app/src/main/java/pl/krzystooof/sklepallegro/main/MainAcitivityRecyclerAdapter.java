package pl.krzystooof.sklepallegro.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;

public class MainAcitivityRecyclerAdapter extends RecyclerView.Adapter<MainAcitivityRecyclerAdapter.ViewHolder> {
    private ArrayList<Offer> offers;

    public MainAcitivityRecyclerAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    public TextView titleText;
    public TextView priceText;
    public ImageView imageView;
    public ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            priceText = (TextView) itemView.findViewById(R.id.priceText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public MainAcitivityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.activity_main_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MainAcitivityRecyclerAdapter.ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.titleText.setText(offer.getName());
        holder.priceText.setText(offer.getPrice().getAmount() + " " + offer.getPrice().getCurrency());
        Glide.with(holder.imageView.getContext()).load(offer.getThumbnailUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
}
