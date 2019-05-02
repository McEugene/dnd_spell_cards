package be.rha.dnd.mereinetidor;

import be.rha.dnd.Constants;
import be.rha.dnd.Spell;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.HashSet;
import java.util.Set;

import static be.rha.dnd.mereinetidor.ScraperHelper.CLIENT;
import static be.rha.dnd.mereinetidor.ScraperHelper.SEARCH_URL;

public class Scraper {

    public static final int SPELLS_PER_PAGE = 50;

    public static void main(String[] args) {

        try {
            String searchUrl = SEARCH_URL + 0;
            HtmlPage page = CLIENT.getPage(searchUrl);
            int i = 1;
            Set<Spell> spells = new HashSet<>();
            while (shouldCreateSpell(page, i) && i <= SPELLS_PER_PAGE) {
                MereinetidorSpell spell = new MereinetidorSpell();
                spell.build(page, i);
                spells.add(spell);
                i++;
            }

            Constants.JSON_HELPER.writeSpells(spells, Constants.FINAL_JSON_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean shouldCreateSpell(HtmlPage page, int i) {
        // TODO check if there is a ith spell in the page
        return true;
    }
}
