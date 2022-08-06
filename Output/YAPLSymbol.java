
public class YAPLSymbol {
    private String id;
    private YAPLType type;
    private int scope;
    private int offset;

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

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}