package be.rha.dnd.mereinetidor;

import be.rha.dnd.Constants;
import be.rha.dnd.Spell;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static be.rha.dnd.mereinetidor.ScraperHelper.*;

public class Scraper {

    public static final int SPELLS_PER_PAGE = 50;


    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.SEVERE);
        System.out.println("Start scraping");
        try {
            String searchUrl = SEARCH_URL + 0;
            HtmlPage page = getPage(searchUrl);
            int i = 1;
            int totalNumber = 0;
            Set<Spell> spells = new HashSet<>();
            while (shouldCreateSpell(page, i) && i <= SPELLS_PER_PAGE) {
                MereinetidorSpell spell = new MereinetidorSpell();
                spell.build(page, i);
                spells.add(spell);
                i++;
                System.out.println(String.format("%s - %s : Processed spell \"%s\"", now(), ++totalNumber, spell.getName()));
            }

            Constants.JSON_HELPER.writeSpells(spells, Constants.FINAL_JSON_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    private static boolean shouldCreateSpell(HtmlPage page, int i) {
        // TODO check if there is a ith spell in the page
        return true;
    }
}
