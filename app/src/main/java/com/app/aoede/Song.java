package com.app.aoede;

public class Song {
    private String id;
    private String title;
    private String artiste;
    private String fileLink;
    private double songLength;
    private String drawable;

    public Song(String id, String title, String artiste, String fileLink, double songLength, String drawable){
        this.id = id;
        this.title = title;
        this.artiste = artiste;
        this.fileLink = fileLink;
        this.songLength = songLength;
        this.drawable = drawable;
    }

    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getArtiste(){return artiste;}
    public String getFileLink(){return fileLink;}
    public double getSongLength(){return songLength;}
    public String getDrawable(){return drawable;}
}
