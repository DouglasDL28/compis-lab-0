
public class YAPLSymbol {
    private String id;
    private YAPLType type;
    private int offset;

    public YAPLSymbol(String id, YAPLType type, int offset) {
        this.id = id;
        this.type = type;
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

    public void setOffset(int offset) {
        this.offset = offset;
    }
}