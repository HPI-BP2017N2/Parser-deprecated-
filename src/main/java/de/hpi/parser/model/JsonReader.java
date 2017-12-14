package de.hpi.parser.model;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;

public class JsonReader {

    public static JsonObject readJsonObject(File file) throws FileNotFoundException {
        return readJsonObject(new FileInputStream(file));
    }

    public static JsonObject readJsonObject(InputStream inputStream){
        return readJsonObject(Json.createReader(inputStream));
    }

    public static JsonObject readJsonObject(String ruleAsJson) {
        return readJsonObject(Json.createReader(new StringReader(ruleAsJson)));
    }

    public static JsonObject readJsonObject(javax.json.JsonReader reader){
        JsonObject jsonObject = reader.readObject();
        reader.close();
        return jsonObject;
    }
}
