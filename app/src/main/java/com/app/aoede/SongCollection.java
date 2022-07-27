package com.app.aoede;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SongCollection {
    static ArrayList<Song> songs = new ArrayList<Song>();

    public SongCollection(){
        Song kutsunohanabi = new Song(
                "s1",
                "靴の花火",
                "Yorushika",
                "https://p.scdn.co/mp3-preview/e75216621cfc30fae44ed19d195b12df2fc5b5bb?cid=2afe87a64b0042dabf51f37318616965",
                5.05,
                "https://i.scdn.co/image/ab67616d0000b27318b1145d9883e5b140151fa9"
        );

        Song yorunikakeru = new Song(
                "s2",
                "夜に駆ける",
                "YOASOBI",
                "https://p.scdn.co/mp3-preview/5ec6d2efbf3a412f1c2a4a8b728bb2ea0653fdb1?cid=2afe87a64b0042dabf51f37318616965",
                4.35,
                "https://i.scdn.co/image/ab67616d0000b273c5716278abba6a103ad13aa7"
        );

        Song turnitoff = new Song(
                "s3",
                "Turn It Off",
                "Paramore",
                "https://p.scdn.co/mp3-preview/3816c0f85e8af91bf6dccb4fb1063f70df8d6ea2?cid=2afe87a64b0042dabf51f37318616965",
                4.33,
                "https://i.scdn.co/image/ab67616d0000b273b9abbedc516dd297039977bd"
        );

        Song disenchanted = new Song(
                "s4",
                "Disenchanted",
                "My Chemical Romance",
                "https://p.scdn.co/mp3-preview/e44c1a29d8857a32bb8667805475dd5386f65693?cid=2afe87a64b0042dabf51f37318616965",
                4.92,
                "https://i.scdn.co/image/ab67616d0000b2739a3e7d0c38c3af225e4695ce"
        );

        Song[] songList = {
                kutsunohanabi,
                yorunikakeru,
                turnitoff,
                disenchanted
        };

        Collections.addAll(songs, songList);

    }

    public Song getCurrentSong(int currentSongIndex){
        return songs.get(currentSongIndex);
    }

    public List<Song> searchSongById(List<Song> songs, String id) {
        return songs.stream()
                .filter(song -> song.getId().equals(id))
                .collect(Collectors.toList());
    }
}



