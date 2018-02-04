package org.androidluckyguys.architecture.data.ReceipeList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.androidluckyguys.architecture.R;
import org.androidluckyguys.architecture.data.data.Receipe;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ramprasad
 */

public class ReceipesListAdapter extends RecyclerView.Adapter<ReceipesListAdapter.ReceipesListViewHolder>{

    private final Context mContext;
    private final ArrayList<Receipe> mReceipesArrayList;
    private final ReceipesListAdapterListener mReceipesListAdapterListener;

    public ReceipesListAdapter(Context context, ArrayList<Receipe> receipesArrayList, ReceipesListAdapterListener receipesListAdapterListener) {
        this.mContext = context;
        this.mReceipesArrayList = receipesArrayList;
        this.mReceipesListAdapterListener = receipesListAdapterListener;
    }

    @Override
    public ReceipesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipes_list_item, parent, false);
        return new ReceipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceipesListViewHolder holder, int position) {
        Receipe receipe = mReceipesArrayList.get(position);

        String receipeName = receipe.getName();

        holder.receipeNameTextview.setText(receipeName);


        /*String receipeThumbnailURL = receipe.getImage();

        if(receipeThumbnailURL != null && !receipeThumbnailURL.isEmpty()){
            Picasso.with(mContext).load(receipeThumbnailURL)
                    .centerCrop()
                    .resize(holder.receipeThumbnailImageView.getWidth(), holder.receipeThumbnailImageView.getHeight())
                    .into(holder.receipeThumbnailImageView);
        }*/
    }

    @Override
    public int getItemCount() {
        return (mReceipesArrayList != null) ? mReceipesArrayList.size() : 0;
    }

    public class ReceipesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*@BindView(R.id.receipe_thuumbnail_imageview)
        ImageView receipeThumbnailImageView;*/

        @BindView(R.id.receipe_name_textview)
        TextView receipeNameTextview;

        public ReceipesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Receipe receipe = mReceipesArrayList.get(getAdapterPosition());
            mReceipesListAdapterListener.onReceipeClicked(receipe);
        }
    }

    public interface ReceipesListAdapterListener{
        void onReceipeClicked(Receipe receipe);
    }
}
