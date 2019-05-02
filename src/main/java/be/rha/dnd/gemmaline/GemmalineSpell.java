package be.rha.dnd.gemmaline;

import be.rha.dnd.ClassAndLevel;
import be.rha.dnd.Spell;

import java.util.ArrayList;
import java.util.List;

public class GemmalineSpell extends Spell {

    private transient List<String> lines = new ArrayList<>();

    public void addLine(String line) {
        lines.add(line);
    }

    public String popLine() {
        return lines.remove(lines.size() - 1);
    }

    public void build() {
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                setNameAndType(lines.get(i));
            } else if (lines.get(i).startsWith("Niveau :")) {
                setClassAndLevel(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Composantes :")) {
                setComponents(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Temps d'incantation :")) {
                setCastTime(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Portée :")) {
                setRange(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Cible :")) {
                setTarget(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Durée :")) {
                setDuration(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Jet de sauvegarde :")) {
                setSave(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Résistance à la magie :")) {
                setMagicResist(removeLineHeader(i));
            } else if (lines.get(i).startsWith("Zone d'effet :")) {
                setAreaOfEffect(removeLineHeader(i));
            } else {
                if (!getDescription().isEmpty()) {
                    appendDescription("\n");
                }
                appendDescription(lines.get(i));
            }
        }
        buildClassAndLevels();
    }

    @Override
    public void buildClassAndLevels() {
        if (!getClassAndLevel().trim().isEmpty()) {
            String[] splittedByClassAndLevel = getClassAndLevel().split(",");
            for (int i = 0; i < splittedByClassAndLevel.length; i++) {
                String[] splitted = splittedByClassAndLevel[i].trim().split(" ");
                addClassAndLevel(new ClassAndLevel(splitted[0], Integer.valueOf(splitted[1])));
            }
        } else {
            addClassAndLevel(ClassAndLevel.allClassesAndLvl());
        }
    }

    private String removeLineHeader(int i) {
        String[] splitted = lines.get(i).split(":");
        return splitted.length == 1 ? "" : splitted[1].trim();
    }

    private void setNameAndType(String nameAndType) {
        int indexOfBracket = nameAndType.indexOf("[");
        int indexOfParenthesis = nameAndType.indexOf("(");
        int index;
        if (indexOfBracket != -1 && indexOfParenthesis != -1) {
            index = Math.min(indexOfBracket, indexOfParenthesis);
        } else {
            index = Math.max(indexOfBracket, indexOfParenthesis);
        }
        String noBracketOrPar;
        if (index != -1) {
            noBracketOrPar = nameAndType.substring(0, index).trim();
        } else {
            noBracketOrPar = nameAndType;
        }
        int spaceIndex = noBracketOrPar.lastIndexOf(" ");
        setName(noBracketOrPar.substring(0, spaceIndex).trim());
        setType(nameAndType.substring(spaceIndex).trim());
    }

    public void enrich(String summary, String book, String page) {
        setSummary(summary);
        setBook(book);
        setPage(page);
    }

    public void enrich(SpellSummary spellSummary) {
        enrich(spellSummary.getSummary(), spellSummary.getBook(), spellSummary.getPage());
    }

}
