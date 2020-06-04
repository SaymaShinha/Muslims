package com.mys3soft.muslims.Tools;

import android.util.JsonReader;
import android.util.JsonToken;

import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.Models.World_Cities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonData {
    //Surahs Json Data
    public static List<Surah> readSurahsJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readSurahsArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<Surah> readSurahsArray(JsonReader reader) throws IOException {
        List<Surah> surahs = new ArrayList<Surah>();

        reader.beginArray();
        while (reader.hasNext()) {
            surahs.add(readSurah(reader));
        }
        reader.endArray();
        return surahs;
    }

    public static Surah readSurah(JsonReader reader) throws IOException {
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
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("surah_number")) {
                Surah_Number = reader.nextInt();
            } else if (name.equals("surah_ar_name")) {
                Surah_Ar_Name = reader.nextString();
            } else if (name.equals("surah_en_name")) {
                Surah_En_Name = reader.nextString();
            } else if (name.equals("surah_en_name_translation")) {
                Surah_En_Name_Translation = reader.nextString();
            } else if (name.equals("revelation_type")) {
                Revelation_Type = reader.nextString();
            } else if (name.equals("total_ayah")) {
                Total_Ayah = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Surah(id, Surah_Number, Surah_Ar_Name, Surah_En_Name, Surah_En_Name_Translation, Revelation_Type, Total_Ayah);
    }


    //RevSurahs Json Data
    public static List<RevSurah> readRevSurahsJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readRevSurahsArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<RevSurah> readRevSurahsArray(JsonReader reader) throws IOException {
        List<RevSurah> revSurahs = new ArrayList<RevSurah>();

        reader.beginArray();
        while (reader.hasNext()) {
            revSurahs.add(readRevSurah(reader));
        }
        reader.endArray();
        return revSurahs;
    }

    public static RevSurah readRevSurah(JsonReader reader) throws IOException {
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
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("chronological_order")) {
                Chronological_Order = reader.nextInt();
            }  else if (name.equals("traditional_order")) {
                Traditional_Order = reader.nextInt();
            } else if (name.equals("surah_ar_name")) {
                Surah_Ar_Name = reader.nextString();
            } else if (name.equals("surah_en_name")) {
                Surah_En_Name = reader.nextString();
            } else if (name.equals("surah_en_name_translation")) {
                Surah_En_Name_Translation = reader.nextString();
            } else if (name.equals("location_of_revelation")) {
                Revelation_Type = reader.nextString();
            } else if (name.equals("total_ayah")) {
                Total_Ayah = reader.nextInt();
            }  else if (name.equals("note")) {
                Note = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new RevSurah(id, Chronological_Order, Traditional_Order, Surah_Ar_Name,
                Surah_En_Name, Surah_En_Name_Translation, Revelation_Type, Total_Ayah, Note);
    }


    //Ayahs Json Data
    public static List<Ayah> readAyahsJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readAyahsArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<Ayah> readAyahsArray(JsonReader reader) throws IOException {
        List<Ayah> ayahs = new ArrayList<Ayah>();

        reader.beginArray();
        while (reader.hasNext()) {
            ayahs.add(readAyah(reader));
        }
        reader.endArray();
        return ayahs;
    }

    public static Ayah readAyah(JsonReader reader) throws IOException {
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
            if (reader_name.equals("id")) {
                id = reader.nextInt();
            } else if (reader_name.equals("identifier")) {
                identifier = reader.nextString();
            } else if (reader_name.equals("surah_number")) {
                surah_number = reader.nextInt();
            } else if (reader_name.equals("surah_ar_name")) {
                surah_ar_name = reader.nextString();
            } else if (reader_name.equals("surah_en_name")) {
                surah_en_name = reader.nextString();
            } else if (reader_name.equals("surah_en_name_translation")) {
                surah_en_name_translation = reader.nextString();
            } else if (reader_name.equals("revelation_type")) {
                revelation_type = reader.nextString();
            } else if (reader_name.equals("ayah_number")) {
                ayah_number = reader.nextInt();
            } else if (reader_name.equals("text")) {
                text = reader.nextString();
            } else if (reader_name.equals("juz")) {
                juz = reader.nextInt();
            } else if (reader_name.equals("manzil")) {
                manzil = reader.nextInt();
            } else if (reader_name.equals("page")) {
                page = reader.nextInt();
            } else if (reader_name.equals("ruku")) {
                ruku = reader.nextInt();
            } else if (reader_name.equals("hizb_quarter")) {
                hizb_quarter = reader.nextInt();
            } else if (reader_name.equals("sajda")) {
                sajda = reader.nextBoolean();
            } else if (reader_name.equals("sajda_id")) {
                sajda_id = reader.nextInt();
            } else if (reader_name.equals("sajda_recommended")) {
                sajda_recommended = reader.nextBoolean();
            } else if (reader_name.equals("sajda_obligatory")) {
                sajda_obligatory = reader.nextBoolean();
            } else if (reader_name.equals("language")) {
                language = reader.nextString();
            } else if (reader_name.equals("name")) {
                name = reader.nextString();
            } else if (reader_name.equals("transelator_en_name")) {
                transelator_en_name = reader.nextString();
            } else if (reader_name.equals("format")) {
                format = reader.nextString();
            }  else if (reader_name.equals("type")) {
                type = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Ayah(id, identifier, surah_number, surah_ar_name, surah_en_name, surah_en_name_translation,
                revelation_type, ayah_number, text, juz, manzil, page, ruku, hizb_quarter, sajda, sajda_id,
                sajda_recommended, sajda_obligatory, language, name, transelator_en_name, format, type);
    }


    //World_Cities Json Data
    public static List<World_Cities> readWorld_CitiesJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readWorld_CitiesArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<World_Cities> readWorld_CitiesArray(JsonReader reader) throws IOException {
        List<World_Cities> world_cities = new ArrayList<World_Cities>();

        reader.beginArray();
        while (reader.hasNext()) {
            world_cities.add(readWorld_Cities(reader));
        }
        reader.endArray();
        return world_cities;
    }

    public static World_Cities readWorld_Cities(JsonReader reader) throws IOException {
        int id = -1;
        String city = null;
        String country = null;
        String subcountry = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("city")) {
                city = reader.nextString();
            } else if (name.equals("country")) {
                country = reader.nextString();
            } else if (name.equals("subcountry")) {
                subcountry = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new World_Cities(id, city, country, subcountry);
    }










    public static List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }


    //


}
