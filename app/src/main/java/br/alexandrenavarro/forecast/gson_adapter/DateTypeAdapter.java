package br.alexandrenavarro.forecast.gson_adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class DateTypeAdapter extends TypeAdapter<Date> {
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "date": {
                    Date date = null;
                    try {
                        date = dateFormat.parse(in.nextString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }
        }
        in.endObject();

        return null;
    }
}
