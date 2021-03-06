package be.rha.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static be.rha.dnd.Constants.MINIFIER;

public abstract class Spell {
    public static final String LATEX_NEW_LINE = "\\\\\n";
    private static final int DESCRIPTION_MAX_CHAR = 1000;
    private transient List<ClassAndLevel> classAndLevels = new ArrayList<>();
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
    private String effect = "";
    private String description = "";
    private String summary = "";
    private String book = "";
    private String page = "";

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
        result += orElse(MINIFIER.minify(effect), "", "EFFET : ");
        result += LATEX_NEW_LINE;
        result += orElse(summary, description, "");
        if (result.length() > DESCRIPTION_MAX_CHAR) {
            result = result.substring(0, DESCRIPTION_MAX_CHAR) + "...";
        }
        return result;
    }

    private String orElse(String field, String defaultValue, String prefix) {
        if (field.trim().isEmpty()) {
            return defaultValue;
        } else {
            return prefix + field.trim() + LATEX_NEW_LINE;
        }
    }

    public boolean hasClass(List<String> classes) {
        return classAndLevels
                .stream()
                .anyMatch(cal -> cal.isAllClasses() || classes.contains(cal.getClassName()));
    }

    public boolean hasLvl(List<Integer> lvls) {
        return classAndLevels
                .stream()
                .anyMatch(cal -> cal.isAllLvl() || lvls.contains(cal.getLvl()));
    }

    public String getName() {
        return name;
    }

    public List<ClassAndLevel> getClassAndLevels() {
        return classAndLevels;
    }

    public String getType() {
        return type;
    }

    public String getClassAndLevel() {
        return classAndLevel;
    }

    public String getComponents() {
        return components;
    }

    public String getCastTime() {
        return castTime;
    }

    public String getRange() {
        return range;
    }

    public String getTarget() {
        return target;
    }

    public String getDuration() {
        return duration;
    }

    public String getSave() {
        return save;
    }

    public String getMagicResist() {
        return magicResist;
    }

    public String getAreaOfEffect() {
        return areaOfEffect;
    }

    public String getDescription() {
        return description;
    }

    public String appendDescription(String toAppend) {
        description += toAppend;
        return getDescription();
    }

    public String getSummary() {
        return summary;
    }

    public String getBook() {
        return book;
    }

    public String getPage() {
        return page;
    }

    public void setClassAndLevels(List<ClassAndLevel> classAndLevels) {
        this.classAndLevels = classAndLevels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClassAndLevel(String classAndLevel) {
        this.classAndLevel = classAndLevel;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public void setCastTime(String castTime) {
        this.castTime = castTime;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setMagicResist(String magicResist) {
        this.magicResist = magicResist;
    }

    public void setAreaOfEffect(String areaOfEffect) {
        this.areaOfEffect = areaOfEffect;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void addClassAndLevel(ClassAndLevel cal) {
        classAndLevels.add(cal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return Objects.equals(getName(), spell.getName()) &&
                Objects.equals(getType(), spell.getType()) &&
                Objects.equals(getClassAndLevel(), spell.getClassAndLevel()) &&
                Objects.equals(getComponents(), spell.getComponents()) &&
                Objects.equals(getCastTime(), spell.getCastTime()) &&
                Objects.equals(getRange(), spell.getRange()) &&
                Objects.equals(getTarget(), spell.getTarget()) &&
                Objects.equals(getDuration(), spell.getDuration()) &&
                Objects.equals(getSave(), spell.getSave()) &&
                Objects.equals(getMagicResist(), spell.getMagicResist()) &&
                Objects.equals(getAreaOfEffect(), spell.getAreaOfEffect()) &&
                Objects.equals(getEffect(), spell.getEffect()) &&
                Objects.equals(getDescription(), spell.getDescription()) &&
                Objects.equals(getSummary(), spell.getSummary()) &&
                Objects.equals(getBook(), spell.getBook()) &&
                Objects.equals(getPage(), spell.getPage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getClassAndLevel(), getComponents(), getCastTime(), getRange(), getTarget(), getDuration(), getSave(), getMagicResist(), getAreaOfEffect(), getEffect(), getDescription(), getSummary(), getBook(), getPage());
    }

    public abstract void buildClassAndLevels();

    public boolean isInBook(List<String> books) {
        return books.contains(getBook());
    }
}
