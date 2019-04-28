package be.rha.dnd;

import java.io.IOException;
import java.util.List;

import static be.rha.dnd.Constants.*;

public class EnrichJson {

    public static void main(String[] args) {
        try {
            List<Spell> spells = JSON_HELPER.readSpells(BASE_JSON_FILE_NAME);
            JSON_HELPER.writeSpells(spells, FINAL_JSON_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
