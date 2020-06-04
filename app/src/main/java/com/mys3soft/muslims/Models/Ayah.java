package com.mys3soft.muslims.Models;

public class Ayah {
    private int id;
    private String identifier;
    private int surah_number;
    private String surah_ar_name;
    private String surah_en_name;
    private String surah_en_name_translation;
    private String revelation_type;
    private int ayah_number;
    private String text;
    private int juz;
    private int manzil;
    private int page;
    private int ruku;
    private int hizb_quarter;
    private Boolean sajda;
    private int sajda_id;
    private Boolean sajda_recommended;
    private Boolean sajda_obligatory;
    private String language;
    private String name;
    private String transelator_en_name;
    private String format;
    private String type;


    public Ayah(int id, String identifier, int surah_number, String surah_ar_name,
                String surah_en_name, String surah_en_name_translation, String revelation_type,
                int ayah_number, String text, int juz, int manzil, int page, int ruku,
                int hizb_quarter, Boolean sajda, int sajda_id, Boolean sajda_recommended,
                Boolean sajda_obligatory, String language, String name, String transelator_en_name,
                String format, String type){

        this.id = id;
        this.identifier = identifier;
        this.surah_number = surah_number;
        this.surah_ar_name = surah_ar_name;
        this.surah_en_name = surah_en_name;
        this.surah_en_name_translation = surah_en_name_translation;
        this.revelation_type = revelation_type;
        this.ayah_number = ayah_number;
        this.text = text;
        this.juz = juz;
        this.manzil = manzil;
        this.page = page;
        this.ruku = ruku;
        this.hizb_quarter = hizb_quarter;
        this.sajda = sajda;
        this.sajda_id = sajda_id;
        this.sajda_recommended = sajda_recommended;
        this.sajda_obligatory = sajda_obligatory;
        this.language = language;
        this.name = name;
        this.transelator_en_name = transelator_en_name;
        this.format = format;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getSurah_number() {
        return surah_number;
    }

    public String getSurah_ar_name() {
        return surah_ar_name;
    }

    public String getSurah_en_name() {
        return surah_en_name;
    }

    public String getSurah_en_name_translation() {
        return surah_en_name_translation;
    }

    public String getRevelation_type() {
        return revelation_type;
    }

    public int getAyah_number() {
        return ayah_number;
    }

    public String getText() {
        return text;
    }

    public int getJuz() {
        return juz;
    }

    public int getManzil() {
        return manzil;
    }

    public int getPage() {
        return page;
    }

    public int getHizb_quarter() {
        return hizb_quarter;
    }

    public Boolean getSajda() {
        return sajda;
    }

    public int getSajda_id() {
        return sajda_id;
    }

    public Boolean getSajda_recommended() {
        return sajda_recommended;
    }

    public Boolean getSajda_obligatory() {
        return sajda_obligatory;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getTranselator_en_name() {
        return transelator_en_name;
    }

    public String getFormat() {
        return format;
    }

    public String getType() {
        return type;
    }

    public int getRuku() {
        return ruku;
    }
}
