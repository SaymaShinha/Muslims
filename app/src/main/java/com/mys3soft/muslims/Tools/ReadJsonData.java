package com.mys3soft.muslims.Tools;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.MalformedJsonException;

import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.Models.World_Cities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonData {
    //Surahs Json Data
    static List<Surah> readSurahsJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readSurahsArray(reader);
        }
    }

    private static List<Surah> readSurahsArray(JsonReader reader) throws IOException {
        List<Surah> surahs = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            surahs.add(readSurah(reader));
        }
        reader.endArray();
        return surahs;
    }

    private static Surah readSurah(JsonReader reader) throws IOException {
        int id = -1;
        int Surah_Number = -1;
        String Surah_Ar_Name = null;
        String Surah_En_Name = null;
        String Surah_En_Name_Translation = null;
        String Revelation_Type = null;
        int Total_Ayah = -1;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "surah_number":
                    Surah_Number = reader.nextInt();
                    break;
                case "surah_ar_name":
                    Surah_Ar_Name = reader.nextString();
                    break;
                case "surah_en_name":
                    Surah_En_Name = reader.nextString();
                    break;
                case "surah_en_name_translation":
                    Surah_En_Name_Translation = reader.nextString();
                    break;
                case "revelation_type":
                    Revelation_Type = reader.nextString();
                    break;
                case "total_ayah":
                    Total_Ayah = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Surah(id, Surah_Number, Surah_Ar_Name, Surah_En_Name, Surah_En_Name_Translation, Revelation_Type, Total_Ayah);
    }


    //RevSurahs Json Data
    static List<RevSurah> readRevSurahsJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readRevSurahsArray(reader);
        }
    }

    private static List<RevSurah> readRevSurahsArray(JsonReader reader) throws IOException {
        List<RevSurah> revSurahs = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            revSurahs.add(readRevSurah(reader));
        }
        reader.endArray();
        return revSurahs;
    }

    private static RevSurah readRevSurah(JsonReader reader) throws IOException {
        int id = -1;
        int Chronological_Order = -1;
        int Traditional_Order = -1;
        String Surah_Ar_Name = null;
        String Surah_En_Name = null;
        String Surah_En_Name_Translation = null;
        String Revelation_Type = null;
        int Total_Ayah = -1;
        String Note = null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "chronological_order":
                    Chronological_Order = reader.nextInt();
                    break;
                case "traditional_order":
                    Traditional_Order = reader.nextInt();
                    break;
                case "surah_ar_name":
                    Surah_Ar_Name = reader.nextString();
                    break;
                case "surah_en_name":
                    Surah_En_Name = reader.nextString();
                    break;
                case "surah_en_name_translation":
                    Surah_En_Name_Translation = reader.nextString();
                    break;
                case "location_of_revelation":
                    Revelation_Type = reader.nextString();
                    break;
                case "total_ayah":
                    Total_Ayah = reader.nextInt();
                    break;
                case "note":
                    Note = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new RevSurah(id, Chronological_Order, Traditional_Order, Surah_Ar_Name,
                Surah_En_Name, Surah_En_Name_Translation, Revelation_Type, Total_Ayah, Note);
    }


    //Ayahs Json Data
    static List<Ayah> readAyahsJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readAyahsArray(reader);
        }
    }

    private static List<Ayah> readAyahsArray(JsonReader reader) throws IOException {
        List<Ayah> ayahs = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            ayahs.add(readAyah(reader));
        }
        reader.endArray();
        return ayahs;
    }

    private static Ayah readAyah(JsonReader reader) throws IOException {
        int id = -1;
        String identifier = null;
        int surah_number = -1;
        String surah_ar_name = null;
        String surah_en_name = null;
        String surah_en_name_translation = null;
        String revelation_type = null;
        int ayah_number = -1;
        String text = null;
        int juz = -1;
        int manzil = -1;
        int page = -1;
        int ruku = -1;
        int hizb_quarter = -1;
        Boolean sajda = null;
        int sajda_id = -1;
        Boolean sajda_recommended = null;
        Boolean sajda_obligatory = null;
        String language = null;
        String name = null;
        String transelator_en_name = null;
        String format = null;
        String type = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String reader_name = reader.nextName();
            switch (reader_name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "identifier":
                    identifier = reader.nextString();
                    break;
                case "surah_number":
                    surah_number = reader.nextInt();
                    break;
                case "surah_ar_name":
                    surah_ar_name = reader.nextString();
                    break;
                case "surah_en_name":
                    surah_en_name = reader.nextString();
                    break;
                case "surah_en_name_translation":
                    surah_en_name_translation = reader.nextString();
                    break;
                case "revelation_type":
                    revelation_type = reader.nextString();
                    break;
                case "ayah_number":
                    ayah_number = reader.nextInt();
                    break;
                case "text":
                    text = reader.nextString();
                    break;
                case "juz":
                    juz = reader.nextInt();
                    break;
                case "manzil":
                    manzil = reader.nextInt();
                    break;
                case "page":
                    page = reader.nextInt();
                    break;
                case "ruku":
                    ruku = reader.nextInt();
                    break;
                case "hizb_quarter":
                    hizb_quarter = reader.nextInt();
                    break;
                case "sajda":
                    sajda = reader.nextBoolean();
                    break;
                case "sajda_id":
                    sajda_id = reader.nextInt();
                    break;
                case "sajda_recommended":
                    sajda_recommended = reader.nextBoolean();
                    break;
                case "sajda_obligatory":
                    sajda_obligatory = reader.nextBoolean();
                    break;
                case "language":
                    language = reader.nextString();
                    break;
                case "name":
                    name = reader.nextString();
                    break;
                case "transelator_en_name":
                    transelator_en_name = reader.nextString();
                    break;
                case "format":
                    format = reader.nextString();
                    break;
                case "type":
                    type = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Ayah(id, identifier, surah_number, surah_ar_name, surah_en_name, surah_en_name_translation,
                revelation_type, ayah_number, text, juz, manzil, page, ruku, hizb_quarter, sajda, sajda_id,
                sajda_recommended, sajda_obligatory, language, name, transelator_en_name, format, type);
    }


    //World_Cities Json Data
    static List<World_Cities> readWorld_CitiesJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readWorld_CitiesArray(reader);
        }
    }

    private static List<World_Cities> readWorld_CitiesArray(JsonReader reader) throws IOException {
        List<World_Cities> world_cities = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            world_cities.add(readWorld_Cities(reader));
        }
        reader.endArray();
        return world_cities;
    }

    private static World_Cities readWorld_Cities(JsonReader reader) throws IOException {
        int id = -1;
        String city = null;
        String country = null;
        String subcountry = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "city":
                    city = reader.nextString();
                    break;
                case "country":
                    country = reader.nextString();
                    break;
                case "subcountry":
                    subcountry = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new World_Cities(id, city, country, subcountry);
    }




    public static List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }


    //


}
