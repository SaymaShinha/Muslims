package com.mys3soft.muslims.Models;

public class RevSurah {
    private int id;
    private int Chronological_Order;
    private int Traditional_Order;
    private String Surah_Ar_Name;
    private String Surah_En_Name;
    private String Surah_En_Name_Translation;
    private String Revelation_Type;
    private int Total_Ayah;
    private String Note;

    public RevSurah(int id, int Chronological_Order, int Traditional_Order, String Surah_Ar_Name,
                    String Surah_En_Name, String Surah_En_Name_Translation, String Revelation_Type,
                    int Total_Ayah, String Note) {
        this.id = id;
        this.Chronological_Order = Chronological_Order;
        this.Traditional_Order = Traditional_Order;
        this.Surah_Ar_Name = Surah_Ar_Name;
        this.Surah_En_Name = Surah_En_Name;
        this.Surah_En_Name_Translation = Surah_En_Name_Translation;
        this.Revelation_Type = Revelation_Type;
        this.Total_Ayah = Total_Ayah;
        this.Note = Note;
    }

    public int getId() {
        return id;
    }

    public int getChronological_Order() {
        return Chronological_Order;
    }

    public int getTraditional_Order() {
        return Traditional_Order;
    }

    public String getSurah_Ar_Name() {
        return Surah_Ar_Name;
    }

    public String getSurah_En_Name() {
        return Surah_En_Name;
    }

    public String getSurah_En_Name_Translation() {
        return Surah_En_Name_Translation;
    }

    public String getRevelation_Type() {
        return Revelation_Type;
    }

    public int getTotal_Ayah() {
        return Total_Ayah;
    }

    public String getNote() {
        return Note;
    }
}

