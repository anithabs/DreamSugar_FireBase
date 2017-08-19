package com.project.uwm.mydiabitiestracker.Objects;

/**
 * Created by RWZ on 7/31/2017.
 */

public class WordObject extends java.util.ArrayList {
    private static int wid;
    private static String word;
    private static String source;

    public WordObject() {
        setWord("-");
        setSource("-");
    }

    public WordObject(int wid, String _word, String _source){
        this.word = _word;
        this.source = _source;
        this.wid = 0;
    }
    // Getters:
    public static String getWord() {
        return word;
    }

    public static String getSource() {
        return source;
    }

    public static int getWid() { return wid; }

    // Setters:
    public static void setWord(String _word) { word  = _word;
    }

    public static void setSource(String _source) { source = _source; }
}


