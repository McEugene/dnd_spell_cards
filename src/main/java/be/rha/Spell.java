package be.rha;

import java.util.ArrayList;
import java.util.List;

public class Spell {
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
        return String.format("\\begin{spell}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}\n\n%s\n\n\\end{spell}\n",
                name, type, classAndLevel, classAndLevel, components, castTime, range, target, duration, save, magicResist, areaOfEffect, description);
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
