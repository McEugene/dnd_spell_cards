package be.rha.dnd;

import java.util.Objects;

public class ClassAndLevel {
    private String className;
    private int lvl;

    public ClassAndLevel(String className, int lvl) {
        this.className = className;
        this.lvl = lvl;
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
}
