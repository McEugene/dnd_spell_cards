package be.rha.dnd.gemmaline;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static be.rha.dnd.Constants.GSON;

public class GemmalineJsonHelper {


    public static final String BASE_JSON_FILE_NAME = "target/base.json";

    public List<GemmalineSpell> readGemmalineSpells(String fileName) throws FileNotFoundException {
        Type collectionType = new TypeToken<Collection<GemmalineSpell>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        return GSON.fromJson(reader, collectionType);
    }

    public List<SpellSummary> readSummaries(String fileName) throws FileNotFoundException {
        Type collectionType = new TypeToken<Collection<SpellSummary>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        return GSON.fromJson(reader, collectionType);
    }
}
