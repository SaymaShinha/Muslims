package com.mys3soft.muslims.Models;

public class Surah {
    private int id;
    private int Surah_Number;
    private String Surah_Ar_Name;
    private String Surah_En_Name;
    private String Surah_En_Name_Translation;
    private String Revelation_Type;
    private int Total_Ayah;


    public Surah(int id, int Surah_Number, String Surah_Ar_Name, String Surah_En_Name, String Surah_En_Name_Translation, String Revelation_Type, int Total_Ayah) {
        this.id = id;
        this.Surah_Number = Surah_Number;
        this.Surah_Ar_Name = Surah_Ar_Name;
        this.Surah_En_Name = Surah_En_Name;
        this.Surah_En_Name_Translation = Surah_En_Name_Translation;
        this.Revelation_Type = Revelation_Type;
        this.Total_Ayah = Total_Ayah;
    }

    public int getId() {
        return id;
    }

    public int getSurah_Number() {
        return Surah_Number;
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

    public void setId(int id) {
        this.id = id;
    }
}
