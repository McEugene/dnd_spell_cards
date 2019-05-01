package be.rha.dnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonHelper JSON_HELPER = new JsonHelper();
    public static final Minifier MINIFIER = new Minifier();
    public static final String FINAL_JSON_FILE_NAME = "target/final.json";
    public static final String TEX_FILE = "src/main/tex/spells.tex";
}
