package be.rha.dnd;

import java.util.ArrayList;
import java.util.List;

import static be.rha.dnd.Constants.MINIFIER;

public class Spell {
    private static final int DESCRIPTION_MAX_CHAR = 300;

    private transient List<String> lines = new ArrayList<>();
    private String name = "";
    private String type = "";
    private String classAndLevel = "";
    private String components = "";
    private String castTime = "";
    private String range = "";
    private String target = "";
    private String duration = "";
    private String save = "";
    private String magicResist = "";
    private String areaOfEffect = "";
    private String description = "";
    private String summary = "";
    private String book = "";
    private String page = "";

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
                classAndLevel = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Composantes :")) {
                components = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Temps d'incantation :")) {
                castTime = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Portée :")) {
                range = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Cible :")) {
                target = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Durée :")) {
                duration = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Jet de sauvegarde :")) {
                save = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Résistance à la magie :")) {
                magicResist = removeLineHeader(i);
            } else if (lines.get(i).startsWith("Zone d'effet :")) {
                areaOfEffect = removeLineHeader(i);
            } else {
                if (!description.isEmpty()) {
                    description += "\n";
                }
                description += lines.get(i);
            }
        }
    }

    private String removeLineHeader(int i) {
        String[] splitted = lines.get(i).split(":");
        return splitted.length == 1 ? splitted[0].trim() : splitted[1].trim();
    }

    public String toTex() {
        return String.format("\\begin{spell}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}\n\n%s\n\n\\end{spell}\n",
                name, type, classAndLevel, source(), components, MINIFIER.minify(castTime), MINIFIER.minify(range), MINIFIER.minify(duration), enrichedDescription());
    }

    private String source() {
        return book + " " + page;
    }

    private String enrichedDescription() {
        String result = orElse(MINIFIER.minify(target), "", "CIBLE : ");
        result += orElse(MINIFIER.minify(save), "", "JDS : ");
        result += orElse(MINIFIER.minify(magicResist), "", "RM : ");
        result += orElse(MINIFIER.minify(areaOfEffect), "", "AOE : ");

        result += orElse(summary, description, "");
        return result.substring(0, DESCRIPTION_MAX_CHAR) + "...";
    }

    private String orElse(String field, String defaultValue, String prefix) {
        if (field.trim().isEmpty()) {
            return defaultValue;
        } else {
            return prefix + field.trim() + "\\\\\n";
        }
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
        name = noBracketOrPar.substring(0, spaceIndex).trim();
        type = nameAndType.substring(spaceIndex).trim();
    }

}
