package com.mys3soft.muslims.Models;

public class Calender {
    private int id;
    private String hijri_date;
    private int hijri_day_number;
    private int hijri_month_number;
    private String hijri_month_en;
    private String hijri_month_ar;
    private int hijri_year_number;
    private String hijri_weakday_en;
    private String hijri_weakday_ar;
    private String hijri_designation_abbreviated;
    private String hijri_designation_expanded;
    private String hijri_holiday;
    private String gregorian_date;
    private int gregorian_day_number;
    private int gregorian_month_number;
    private String gregorian_month_en;
    private int gregorian_year_number;
    private String gregorian_weakday_en;
    private String gregorian_designation_abbreviated;
    private String gregorian_designation_expanded;

    public Calender(int id, String hijri_date, int hijri_day_number, int hijri_month_number,
                    String hijri_month_en, String hijri_month_ar, int hijri_year_number,
                    String hijri_weakday_en, String hijri_weakday_ar,
                    String hijri_designation_abbreviated, String hijri_designation_expanded,
                    String hijri_holiday, String gregorian_date, int gregorian_day_number,
                    int gregorian_month_number, String gregorian_month_en, int gregorian_year_number,
                    String gregorian_weakday_en, String gregorian_designation_abbreviated,
                    String gregorian_designation_expanded) {
        this.id = id;
        this.hijri_date = hijri_date;
        this.hijri_day_number = hijri_day_number;
        this.hijri_month_number = hijri_month_number;
        this.hijri_month_en = hijri_month_en;
        this.hijri_month_ar = hijri_month_ar;
        this.hijri_year_number = hijri_year_number;
        this.hijri_weakday_en = hijri_weakday_en;
        this.hijri_weakday_ar = hijri_weakday_ar;
        this.hijri_designation_abbreviated = hijri_designation_abbreviated;
        this.hijri_designation_expanded = hijri_designation_expanded;
        this.hijri_holiday = hijri_holiday;
        this.gregorian_date = gregorian_date;
        this.gregorian_day_number = gregorian_day_number;
        this.gregorian_month_number = gregorian_month_number;
        this.gregorian_month_en = gregorian_month_en;
        this.gregorian_year_number = gregorian_year_number;
        this.gregorian_weakday_en = gregorian_weakday_en;
        this.gregorian_designation_abbreviated = gregorian_designation_abbreviated;
        this.gregorian_designation_expanded = gregorian_designation_expanded;
    }

    public int getId() {
        return id;
    }

    public String getHijri_date() {
        return hijri_date;
    }

    public int getHijri_day_number() {
        return hijri_day_number;
    }

    public int getHijri_month_number() {
        return hijri_month_number;
    }

    public String getHijri_month_en() {
        return hijri_month_en;
    }

    public String getHijri_month_ar() {
        return hijri_month_ar;
    }

    public int getHijri_year_number() {
        return hijri_year_number;
    }

    public String getHijri_weakday_en() {
        return hijri_weakday_en;
    }

    public String getHijri_weakday_ar() {
        return hijri_weakday_ar;
    }

    public String getHijri_designation_abbreviated() {
        return hijri_designation_abbreviated;
    }

    public String getHijri_designation_expanded() {
        return hijri_designation_expanded;
    }

    public String getHijri_holiday() {
        return hijri_holiday;
    }

    public String getGregorian_date() {
        return gregorian_date;
    }

    public int getGregorian_day_number() {
        return gregorian_day_number;
    }

    public int getGregorian_month_number() {
        return gregorian_month_number;
    }

    public String getGregorian_month_en() {
        return gregorian_month_en;
    }

    public int getGregorian_year_number() {
        return gregorian_year_number;
    }

    public String getGregorian_weakday_en() {
        return gregorian_weakday_en;
    }

    public String getGregorian_designation_abbreviated() {
        return gregorian_designation_abbreviated;
    }

    public String getGregorian_designation_expanded() {
        return gregorian_designation_expanded;
    }
}
