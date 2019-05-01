package be.rha.dnd.mereinetidor;

import be.rha.dnd.Spell;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import java.io.IOException;

public class MereinetidorSpell extends Spell {

    public static final String SPELL_URL_XPATH = "//*[@id=\"flexitable\"]/tbody/tr[%i%]/th/a";

    public static final String NAME_XPATH = "//*[@id=\"title\"]/span/strong/div";
    public static final String CLASS_AND_DOMAIN_AND_LEVEL_XPATH = "//*[@id=\"classeniveau_text\"]";
    public static final String SCHOOL_AND_BRANCH_XPATH = "//*[@id=\"ecole_text\"]/span";
    public static final String SUMMARY_XPATH = "//*[@id=\"bulle\"]";

    private transient HtmlPage spellPage;

    public void build(HtmlPage page, int i) throws IOException {
        HtmlAnchor a = (HtmlAnchor) page.getByXPath(SPELL_URL_XPATH.replace("%i%", String.valueOf(i))).get(0);
        String spellUrl = ScraperHelper.SITE_URL + a.getHrefAttribute();
        spellPage = ScraperHelper.CLIENT.getPage(spellUrl);
        extractName();
        extractSummary();
    }

    private void extractName() {
        setName(((HtmlDivision)spellPage.getByXPath(NAME_XPATH).get(0)).getTextContent().trim());
    }

    private void extractSummary() {
        setSummary(((HtmlSpan)spellPage.getByXPath(SUMMARY_XPATH).get(2)).getTextContent().trim());
    }
}
