package com.example.mohitrajpanday.mmusicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity  {

private  ArrayList<SongInfo> _songs= new ArrayList<SongInfo>();
RecyclerView recyclerView;
SeekBar seekBar;
SongAdapter songAdapter;
Button play,next,previous,backward,forward;
MediaPlayer mediaPlayer;
String last;
    private Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

       play=(Button) findViewById(R.id.play);
       next=(Button) findViewById(R.id.next);
       previous=(Button) findViewById(R.id.previous);
       forward=(Button) findViewById(R.id.forward);
       backward=(Button) findViewById(R.id.backward);
        seekBar=(SeekBar) findViewById(R.id.seekBar2);

        songAdapter=new SongAdapter(this,_songs);
        recyclerView.setAdapter(songAdapter);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play.getText().toString().equals("P")){
                    mediaPlayer.start();
                    play.setText("||");
                    play.setBackground(getDrawable(R.drawable.pause_normal));
                }
                else {

                    mediaPlayer.pause();
                    play.setText("P");
                    play.setBackground(getDrawable(R.drawable.play2));
                }
            }
        });


        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
            }
        });



        songAdapter.setOnitemClickListner(new SongAdapter.OnitemClickListner() {
            @Override
            public void onItemClick(TextView SongName, View v, SongInfo obj, int position) {

                try {
                    if(play.getText().toString().equals("||")){
                        stopPlaying();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(obj.getSongUrl());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();


                            }
                        });
                        play.setBackground(getDrawable(R.drawable.pause_normal));
                        play.setText("||");


                    }

                    else {

                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(obj.getSongUrl());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();


                            }
                        });
                        play.setBackground(getDrawable(R.drawable.pause_normal));
                        play.setText("||");
                    }
                }catch(IOException e){

                }

            }
        });

            CheckPermission();
    }
    private void CheckPermission(){
        if(Build.VERSION.SDK_INT>23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                return;
            }

        }
        else {
            loadSongs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case  123:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                    CheckPermission();
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }
    public  void stopPlaying(){
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
       }
    private void  loadSongs(){

        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);

        if(cursor!=null){
            if(cursor.moveToFirst()){
                    do{
                        String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)).replace(".MP3"
                                ,"").replace(".aac","").replace(
                                        ".wav","").replace(".mp3","");

                        String artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        String url=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                         SongInfo s=new SongInfo(name,artist,url);
                         _songs.add(s);
                    }while (cursor.moveToNext());


            }
            cursor.close();
            songAdapter=new SongAdapter(this,_songs);

        }






    }


   }
