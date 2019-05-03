package be.rha.dnd.mereinetidor;

import be.rha.dnd.ClassAndLevel;
import be.rha.dnd.Spell;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import java.io.IOException;

public class MereinetidorSpell extends Spell {

    public static final String SPELL_URL_XPATH = "//*[@id=\"flexitable\"]/tbody/tr[%i%]/th/a";

    public static final String NAME_XPATH = "//*[@id=\"title\"]/span/strong/div";
    public static final String CLASS_AND_DOMAIN_AND_LEVEL_XPATH = "//*[@id=\"classeniveau_text\"]/a[1]";
    public static final String SCHOOL_AND_BRANCH_XPATH = "//*[@id=\"ecole_text\"]/a";
    public static final String SUMMARY_XPATH = "//*[@id=\"bulle\"]";
    public static final String BOOK_XPATH = "//*[@id=\"sources_text\"]/a";
    public static final String PAGE_XPATH = "//*[@id=\"page_dd3\"]";
    public static final String RANGE_XPATH = "//*[@id=\"portee\"]/text()";
    public static final String DURATION_XPATH = "//*[@id=\"duree\"]/text()";
    public static final String AOE_XPATH = "//*[@id=\"zoneeffet\"]/text()";
    public static final String EFFECT_XPATH = "//*[@id=\"effet\"]/text()";
    public static final String TARGET_XPATH = "//*[@id=\"cible\"]/text()";
    public static final String CAST_TIME_XPATH = "//*[@id=\"incantation\"]/text()";
    public static final String SAVE_XPATH = "//*[@id=\"jds\"]/text()";
    public static final String MAGIC_RESIST_XPATH = "//*[@id=\"rm\"]/text()";
    public static final String COMPONENTS_XPATH = "//*[@id=\"composantes\"]/text()";

    private transient HtmlPage spellPage;

    public void build(HtmlPage page, int i) throws IOException {
        try {
            HtmlAnchor a = (HtmlAnchor) page.getByXPath(SPELL_URL_XPATH.replace("%i%", String.valueOf(i))).get(0);
            String spellUrl = ScraperHelper.SITE_URL + a.getHrefAttribute();
            spellPage = ScraperHelper.CLIENT.getPage(spellUrl);
            extractName();
            extractSummary();
            extractBook();
            extractPage();
            extractSchool();
            extractClassAndLevels();
            extractRange();
            extractDuration();
            extractAOE();
            extractEffect();
            extractTarget();
            extractCastTime();
            extractSave();
            extractMagicResist();
            extractComponents();

            buildClassAndLevels();
        } catch (Exception e) {
            System.out.println("Exception while handling spell with name " + getName());
            throw e;
        }
    }

    @Override
    public void buildClassAndLevels() {
        if (!getClassAndLevel().trim().isEmpty()) {
            String[] splittedByComma = getClassAndLevel().split(",");
            for (int i = 0; i < splittedByComma.length; i++) {
                String rawCal = splittedByComma[i].trim();
                // sometimes there is just a blank space between 2 commas
                if (!rawCal.isEmpty()) {
                    // handle levels with another feat like (v) or (Metal)
                    if (rawCal.contains("(")) {
                        rawCal = rawCal.substring(0, rawCal.indexOf("(")).trim();
                    }
                    addClassAndLevel(new ClassAndLevel(rawCal.substring(0, rawCal.length() - 1).trim(), Integer.valueOf(rawCal.substring(rawCal.length() - 1))));
                }
            }
        } else {
            addClassAndLevel(ClassAndLevel.allClassesAndLvl());
        }
    }

    private void extractComponents() {
        setComponents(simpleText(COMPONENTS_XPATH));
    }

    private void extractMagicResist() {
        setMagicResist(simpleText(MAGIC_RESIST_XPATH));
    }

    private void extractSave() {
        setSave(simpleText(SAVE_XPATH));
    }

    private void extractCastTime() {
        setCastTime(simpleText(CAST_TIME_XPATH));
    }

    private String simpleText(String castTimeXpath) {
        return clean(asText((DomNode) spellPage.getByXPath(castTimeXpath).get(0)));
    }

    private void extractTarget() {
        setTarget(simpleText(TARGET_XPATH));
    }

    private void extractEffect() {
        setEffect(simpleText(EFFECT_XPATH));
    }

    private void extractAOE() {
        setAreaOfEffect(simpleText(AOE_XPATH));
    }

    private void extractDuration() {
        setDuration(simpleText(DURATION_XPATH));
    }


    private void extractRange() {
        setRange(simpleText(RANGE_XPATH));
    }

    private void extractClassAndLevels() {
        Node node = (Node) spellPage.getByXPath(CLASS_AND_DOMAIN_AND_LEVEL_XPATH).get(0);
        String rawCal = textContent(node);
        while (node.getNextSibling() != null) {
            node = node.getNextSibling();
            String content = textContent(node);
            if (StringUtils.isNumeric(content.substring(0, 1))) {
                rawCal += " ";
            }
            rawCal += content;
        }
        setClassAndLevel(rawCal.replace(", ", ",").replace(",", ", "));
    }

    private String textContent(Node node) {
        return node.getTextContent().trim();
    }

    private String asText(DomNode node) {
        return node.asText().trim();
    }

    private String clean(String toClean) {
        // remove leading ':'
        return toClean.substring(1).trim();
    }

    private void extractSchool() {
        HtmlAnchor schoolAnchor = (HtmlAnchor) spellPage.getByXPath(SCHOOL_AND_BRANCH_XPATH).get(0);
        String tmpType = textContent(schoolAnchor);
        if (schoolAnchor.getNextSibling() != null) {
            tmpType = tmpType + " " + asText(schoolAnchor.getNextSibling());
        }
        setType(tmpType);
    }

    private void extractPage() {
        setPage(textContent((HtmlSpan) spellPage.getByXPath(PAGE_XPATH).get(0)));
    }

    private void extractBook() {
        setBook(textContent((HtmlAnchor) spellPage.getByXPath(BOOK_XPATH).get(0)));
    }

    private void extractName() {
        setName(textContent((HtmlDivision) spellPage.getByXPath(NAME_XPATH).get(0)));
    }

    private void extractSummary() {
        setSummary(clean(textContent((HtmlSpan) spellPage.getByXPath(SUMMARY_XPATH).get(2))));
    }
}
