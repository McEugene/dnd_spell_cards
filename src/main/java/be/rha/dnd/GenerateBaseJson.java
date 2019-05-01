package be.rha.dnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static be.rha.dnd.Constants.*;

public class GenerateBaseJson {

    private static final List<String> CLASSES = Collections.singletonList("Prêtre");
    private static final List<Integer> LVLS = Arrays.asList(0, 1, 2, 3, 4, 5);

    public static void main(String[] args) {
        try {
            List<Spell> spells = getSpells();
            spells.forEach(Spell::build);
            List<Spell> filteredSpells = spells.stream()
                    .distinct()
                    .filter(spell -> spell.hasClass(CLASSES))
                    .filter(spell -> spell.hasLvl(LVLS))
                    .collect(Collectors.toList());
            JSON_HELPER.writeSpells(filteredSpells, BASE_JSON_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Spell> getSpells() throws IOException {
        List<Path> paths = Files.list(Paths.get("src/main/resources/books"))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        List<Spell> allSpells = new ArrayList<>();
        for (Path path : paths) {
            allSpells.addAll(getSpells(path));
        }
        return allSpells;
    }

    private static List<Spell> getSpells(Path filePath) throws IOException {
        List<Spell> spells;
        try (BufferedReader b = Files.newBufferedReader(filePath, Charset.forName("UTF-8"))) {
            String readLine;
            spells = new ArrayList<>();
            Spell spell = new Spell();
            spells.add(spell);
            boolean first = true;
            while ((readLine = b.readLine()) != null) {
                if (readLine.startsWith("Niveau :")) {
                    if (!first) {
                        Spell newSpell = new Spell();
                        newSpell.addLine(spell.popLine());
                        spell = newSpell;
                        spells.add(spell);
                    }
                    first = false;
                }
                spell.addLine(readLine);
            }
        }
        return spells;
    }
}
