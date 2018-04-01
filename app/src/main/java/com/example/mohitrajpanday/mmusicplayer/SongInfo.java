package com.example.mohitrajpanday.mmusicplayer;

/**
 * Created by Mohit Raj Panday on 04-Mar-18.
 */

public class SongInfo {
    public  String songName,songArtist,songUrl;

    public SongInfo(){

    }


    public SongInfo(String songName,String songArtist,String songUrl){

    this.songName=songName;
    this.songArtist=songArtist;
    this.songUrl=songUrl;

    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongUrl() {
        return songUrl;
    }
}
