package be.rha.dnd;

import be.rha.dnd.gemmaline.GemmalineSpell;
import be.rha.dnd.gemmaline.SpellSummary;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static be.rha.dnd.Constants.GSON;

public class JsonHelper {

    public void writeSpells(List<? extends Spell> spells, String fileName) throws IOException {
        String json = GSON.toJson(spells);

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            writer.write(json);
        }
    }

    public List<Spell> readSpells(String fileName) throws FileNotFoundException {
        Type collectionType = new TypeToken<Collection<Spell>>() {}.getType();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        return GSON.fromJson(reader, collectionType);
    }
}
