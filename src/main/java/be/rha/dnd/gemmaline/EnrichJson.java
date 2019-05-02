package be.rha.dnd.gemmaline;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static be.rha.dnd.Constants.FINAL_JSON_FILE_NAME;
import static be.rha.dnd.Constants.JSON_HELPER;
import static be.rha.dnd.gemmaline.GemmalineJsonHelper.BASE_JSON_FILE_NAME;

public class EnrichJson {

    private static final GemmalineJsonHelper GEMMALINE_JSON_HELPER = new GemmalineJsonHelper();
    private static final String SUMMARY_FILE_NAME = "src/main/resources/summary.json";

    public static void main(String[] args) {
        try {
            List<GemmalineSpell> spells = GEMMALINE_JSON_HELPER.readGemmalineSpells(BASE_JSON_FILE_NAME);
            List<SpellSummary> summaries = GEMMALINE_JSON_HELPER.readSummaries(SUMMARY_FILE_NAME);
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
