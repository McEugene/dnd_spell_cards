package be.rha.dnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static be.rha.dnd.Constants.*;

public class GenerateBaseJson {

    private static final List<String> CLASSES = Collections.singletonList("Prêtre");
    private static final List<Integer> LVLS = Arrays.asList(0, 1, 2, 3, 4);

    public static void main(String[] args) {
        try {
            List<Spell> spells = getSpells();
            spells.forEach(Spell::build);
            List<Spell> filteredSpells = spells.stream()
                    .filter(spell -> spell.hasClass(CLASSES))
                    .filter(spell -> spell.hasLvl(LVLS))
                    .collect(Collectors.toList());
            JSON_HELPER.writeSpells(filteredSpells, BASE_JSON_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Spell> getSpells() throws IOException {
        File f = new File(INPUT_FILE_NAME);

        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine;
        List<Spell> spells = new ArrayList<>();
        Spell spell = new Spell();
        spells.add(spell);
        boolean first = true;
        while ((readLine = b.readLine()) != null) {
            if (readLine.startsWith("Niveau")) {
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
        return spells;
    }
}
