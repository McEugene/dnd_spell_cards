package be.rha.dnd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static be.rha.dnd.Constants.*;

public class GenerateTex {

    public static void main(String[] args) {
        Path path = Paths.get(TEX_FILE);

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            List<Spell> spells = JSON_HELPER.readSpells(FINAL_JSON_FILE_NAME);
            for (Spell spell : spells) {
                writer.write(spell.toTex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
