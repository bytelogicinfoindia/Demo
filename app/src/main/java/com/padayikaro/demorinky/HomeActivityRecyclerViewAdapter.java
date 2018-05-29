package com.padayikaro.demorinky;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



public class HomeActivityRecyclerViewAdapter extends RecyclerView.Adapter<HomeActivityRecyclerViewAdapter.ViewHolder> {


    private List<ResultModel> data;
    Context mContext;

    LayoutInflater layoutInflater;

    public HomeActivityRecyclerViewAdapter(Context mContext,List<ResultModel> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mName.setText("Title :"+data.get(position).getTitle().toString());
        holder.relise_date.setText("Release date:"+data.get(position).getReleaseDate().toString());
        holder.overview.setText("OverView:"+data.get(position).getOverview().toString());
        holder.popularity.setText("Popularity:"+data.get(position).getPopularity().toString());

      ///  imageurlimage="http://twp1touch.com/assets/rfq/" + data.get(position).getPopularity().toString();

       /* // Glide image path
          Glide.with(mContext).load(""+data.get(position).getPopularity().toString())
                                                        .placeholder(R.drawable.dummy)
                                                        .error(R.drawable.dummy).into(single_listview);
                                                System.out.println("Imagerfq_image_Size" + imageurl.size());
*/


    }
    public void filterList(ArrayList<ResultModel> filterdNames) {
        this.data = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName, relise_date, overview, popularity;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            relise_date = (TextView) itemView.findViewById(R.id.relise_date);
            overview = (TextView) itemView.findViewById(R.id.overview);
            popularity = (TextView) itemView.findViewById(R.id.popularity);

        }
    }


}

