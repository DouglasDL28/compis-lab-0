package compis.lab0;

public class YAPLSymbol {
    private String id;
    private YAPLType type;
    private final int width;
    private int offset;

    public YAPLSymbol(String id, YAPLType type, int width, int offset) {
        this.id = id;
        this.type = type;
        this.width = width;
        this.offset = offset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YAPLType getType() {
        return type;
    }

    public void setType(YAPLType type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public int getWidth() { return this.width; }
}