package be.rha.dnd;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static be.rha.dnd.Constants.*;

public class EnrichJson {

    public static void main(String[] args) {
        try {
            List<Spell> spells = JSON_HELPER.readSpells(BASE_JSON_FILE_NAME);
            List<SpellSummary> summaries = JSON_HELPER.readSummaries(SUMMARY_FILE_NAME);
            Map<String, SpellSummary> summariesByName = summaries.stream()
                    .collect(Collectors.toMap(SpellSummary::getName, Function.identity()));
            spells.forEach(spell -> {
                if (summariesByName.containsKey(spell.getName())) {
                    SpellSummary spellSummary = summariesByName.get(spell.getName());
                    spell.enrich(spellSummary);
                }
            });
            JSON_HELPER.writeSpells(spells, FINAL_JSON_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
