package be.rha.dnd;

public class Minifier {

    public String minify(String toMinify) {
        return toMinify
                .replace("minutes", "min")
                .replace("minute", "min")
                .replace("niveaux", "lvl")
                .replace("niveau", "lvl")
                .replace("action simple", "AS")
                .replace("moyenne", "moy")
                .replace("le jeteur de sorts", "perso")
                .trim();
    }
}
