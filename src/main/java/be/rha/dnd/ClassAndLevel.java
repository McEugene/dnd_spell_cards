package be.rha.dnd;

import java.util.Objects;

public class ClassAndLevel {
    private String className;
    private int lvl;
    private boolean allLvl = false;
    private boolean allClasses = false;

    public static ClassAndLevel allClassesAndLvl() {
        return new ClassAndLevel();
    }

    public ClassAndLevel(String className, int lvl) {
        this.className = className;
        this.lvl = lvl;
    }

    private ClassAndLevel() {
        this.allClasses = true;
        this.allLvl = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassAndLevel that = (ClassAndLevel) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(lvl, that.lvl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, lvl);
    }

    public String getClassName() {
        return className;
    }

    public int getLvl() {
        return lvl;
    }

    public boolean isAllLvl() {
        return allLvl;
    }

    public boolean isAllClasses() {
        return allClasses;
    }
}
