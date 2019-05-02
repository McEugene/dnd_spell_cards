package be.rha.dnd.mereinetidor;

import be.rha.dnd.Spell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.rha.dnd.Constants.*;

public class GenerateTex {


    private static final List<String> CLASSES = Arrays.asList("Prê", "Bien", "Terre");
    private static final List<Integer> LVLS = Arrays.asList(0, 1, 2, 3, 4, 5);
    private static final List<String> BOOKS = Arrays.asList(
            "Manuel des Joueurs",
            "Codex profane",
            "Codex aventureux",
            "Codex divin",
            "Codex martial",
            "Compendium arcanique"
    );

    public static void main(String[] args) {
        Path path = Paths.get(TEX_FILE);

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            List<MereinetidorSpell> spells = JSON_HELPER.readSpells(FINAL_JSON_FILE_NAME);
            spells.forEach(MereinetidorSpell::buildClassAndLevels);
            List<MereinetidorSpell> filtered = spells.stream()
                    .filter(spell -> spell.hasClass(CLASSES))
                    .filter(spell -> spell.hasLvl(LVLS))
                    .filter(spell -> spell.isInBook(BOOKS))
                    .collect(Collectors.toList());
            for (Spell spell : filtered) {
                writer.write(spell.toTex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
