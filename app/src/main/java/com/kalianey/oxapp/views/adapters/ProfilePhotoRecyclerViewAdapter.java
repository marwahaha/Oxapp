package com.kalianey.oxapp.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kalianey.oxapp.R;
import com.kalianey.oxapp.models.ModelAttachment;
import com.kalianey.oxapp.utils.AppController;
import com.kalianey.oxapp.utils.UICircularImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalianey on 18/08/2015.
 */
public class ProfilePhotoRecyclerViewAdapter extends  RecyclerView.Adapter<ProfilePhotoRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ModelAttachment> photos; //data
    private Activity listContext;
    //private Context listContext;
    private int listRowLayoutId;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public ProfilePhotoRecyclerViewAdapter(Activity context) {
        photos = new ArrayList<>();
        listContext = context;
        //this.notifyDataSetChanged();
    }

    public void setPhotos(List< ModelAttachment> photoList) {
        photos.clear();
        photos.addAll(photoList);
        this.notifyItemRangeInserted(0, photos.size() - 1);
    }

    @Override
    public ProfilePhotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(listContext).inflate(R.layout.profile_photogrid_item, parent, false);

        return new ProfilePhotoRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfilePhotoRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        ModelAttachment photo = photos.get(i);
        viewHolder.imageView.setImageUrl(photo.getUrl(), imageLoader);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ModelAttachment photo;
        public NetworkImageView imageView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (NetworkImageView) itemView.findViewById(R.id.imageView);
        }
    }


}