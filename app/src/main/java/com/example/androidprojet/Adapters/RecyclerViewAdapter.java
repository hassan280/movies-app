package com.example.androidprojet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidprojet.R;
import com.example.androidprojet.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<MovieModel> mData ;

    // Allowing the onclick events to the recycler view items
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<MovieModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View viewHolder = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieModel currentItem = mData.get(position);

        String movie_name = currentItem.getTitle();
        String movie_rating = currentItem.getRating();
        String image_url = "https://image.tmdb.org/t/p/w780/" + currentItem.getPoster_path();


        holder.movie_name.setText(movie_name);
        holder.movie_rating.setText(movie_rating);

        Picasso.with(mContext).load(image_url).fit().centerInside().into(holder.movie_image);
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView movie_name ;
        TextView movie_rating ;
        ImageView movie_image;





        public MyViewHolder(View itemView) {
            super(itemView);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_rating = itemView.findViewById(R.id.movie_rating);
            movie_image = itemView.findViewById(R.id.movie_poster);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}