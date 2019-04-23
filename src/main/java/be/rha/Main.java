package be.rha;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            List<Spell> spells = getSpells();
            spells.forEach(Spell::build);

            /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(spells);
            System.out.println(json);*/

            Path path = Paths.get("target/spells.tex");

            try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
                for (Spell spell : spells) {
                    writer.write(spell.toTex());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Spell> getSpells() throws IOException {
        File f = new File("src/main/resources/example.txt");

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
