package com.example.mohitrajpanday.mmusicplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohit Raj Panday on 04-Mar-18.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
ArrayList<SongInfo> _songs;
Context context;

OnitemClickListner onitemClickListner;


SongAdapter(Context context, ArrayList<SongInfo> _songs){

    this.context=context;
    this._songs=_songs;

}

public interface OnitemClickListner{
    void onItemClick(TextView SongName,View v,SongInfo obj,int position);
    }

    public void setOnitemClickListner(OnitemClickListner onitemClickListner) {
        this.onitemClickListner = onitemClickListner;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View myView= LayoutInflater.from(context).inflate(R.layout.row_song,parent,false);
       return  new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final SongHolder holder, final int position) {

    final SongInfo c=_songs.get(position);
    holder.SongName.setText(c.songName);
    holder.ArtistName.setText(c.songArtist);
    holder.SongName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onitemClickListner!=null){
                    onitemClickListner.onItemClick(holder.SongName,v,c,position);


            }

        }
    });
    }

    @Override
    public int getItemCount() {
        return _songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder{

        TextView SongName,ArtistName;
        Button btnAction;
        public SongHolder(View itemView) {
            super(itemView);

            SongName=(TextView) itemView.findViewById(R.id.SongName);
            ArtistName=(TextView) itemView.findViewById(R.id.ArtistName);

        }
    }
}
