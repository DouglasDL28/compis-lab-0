package compis.lab0;

/**
 * YAPL semantic error class.
 */
public class YAPLSemError {
    private final int line;
    private final int pos;
    private final String description;

    public YAPLSemError(int line, int pos, String description) {
        this.line = line;
        this.pos = pos;
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "\u001B[31m" + "SemError! " + "(line " + line + " at position " + pos + "). " + description + "\033[0m";
    }
}
