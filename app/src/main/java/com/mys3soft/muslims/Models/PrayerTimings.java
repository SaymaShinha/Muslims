package com.mys3soft.muslims.Models;

import java.util.Date;

public class PrayerTimings {
    private int id;
    private String Imsak;
    private String Fajr;
    private String Sunrise;
    private String Dhuhr;
    private String Asr;
    private String Sunset;
    private String Maghrib;
    private String Isha;
    private String Midnight;
    private String Location;

    public PrayerTimings(int id, String imsak, String fajr, String sunrise, String dhuhr, String asr,
                         String sunset, String maghrib, String isha, String midnight,
                         String location){
        this.id = id;
        this.Imsak = imsak;
        this.Fajr = fajr;
        this.Sunrise = sunrise;
        this.Dhuhr = dhuhr;
        this.Asr = asr;
        this.Sunset = sunset;
        this.Maghrib = maghrib;
        this.Isha = isha;
        this.Midnight = midnight;
        this.Location = location;
    }

    public int getId() {
        return id;
    }

    public String getImsak() {
        return Imsak;
    }

    public String getFajr() {
        return Fajr;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public String getDhuhr() {
        return Dhuhr;
    }

    public String getAsr() {
        return Asr;
    }

    public String getSunset() {
        return Sunset;
    }

    public String getMaghrib() {
        return Maghrib;
    }

    public String getIsha() {
        return Isha;
    }

    public String getMidnight() {
        return Midnight;
    }

    public String getLocation() {
        return Location;
    }
}
