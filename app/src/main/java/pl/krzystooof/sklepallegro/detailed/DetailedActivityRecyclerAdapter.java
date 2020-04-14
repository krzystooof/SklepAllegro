package pl.krzystooof.sklepallegro.detailed;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import pl.krzystooof.sklepallegro.R;
import pl.krzystooof.sklepallegro.data.Offer;

class DetailedActivityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Offer offer;
    private String LogTag = "DetailedActivityRecyclerAdapter";

    public DetailedActivityRecyclerAdapter(Offer offer) {
        this.offer = offer;
    }

    //2 types of views implemented: Image, Description

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //Description
        if (viewType == 1) {
            View listItem = layoutInflater.inflate(R.layout.recycler_item_description, parent, false);
            return new DetailedActivityRecyclerAdapter.mViewHolder1(listItem);
        }
        //Image
        View listItem = layoutInflater.inflate(R.layout.recycler_item_image, parent, false);
        return new DetailedActivityRecyclerAdapter.mViewHolder0(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //Log.i(LogTag, "BindViewHolder: position = " + position + ", viewType = " + viewType);
        //Description
        if (viewType == 1) {
            final DetailedActivityRecyclerAdapter.mViewHolder1 mHolder = (DetailedActivityRecyclerAdapter.mViewHolder1) holder;
            String text = "cena: <b>" + String.format ("%.2f", offer.getPrice().getAmount()) + "<b> " + offer.getPrice().getCurrency();
            mHolder.priceText.setText(Html.fromHtml(text));
            mHolder.descText.setText(Html.fromHtml(offer.getDescription​()));
        }
        //Image
        else {
            DetailedActivityRecyclerAdapter.mViewHolder0 mHolder = (DetailedActivityRecyclerAdapter.mViewHolder0) holder;
            Glide.with(mHolder.imageView.getContext()).load(offer.getThumbnailUrl()).into(mHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        //image + description
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class mViewHolder1 extends RecyclerView.ViewHolder {
        //Image
        public TextView priceText;
        public TextView descText;
        public ConstraintLayout layout;

        public mViewHolder1(@NonNull View itemView) {
            super(itemView);
            priceText = itemView.findViewById(R.id.priceText);
            descText = itemView.findViewById(R.id.descText);
            layout = itemView.findViewById(R.id.additionalLayout);
        }
    }

    public static class mViewHolder0 extends RecyclerView.ViewHolder {
        //Description
        public ImageView imageView;
        public ConstraintLayout layout;

        public mViewHolder0(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.imageLayout);
        }
    }
}
