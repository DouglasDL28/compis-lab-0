package compis.lab0;

public class YAPLSymbol {
    private String id;
    private YAPLType type;
    private final int width;
    private Integer offset;
    private String scope;

    public YAPLSymbol(String id, YAPLType type, int width, int offset, String scope) {
        this.id = id;
        this.type = type;
        this.width = width;
        this.offset = offset;
        this.scope = scope;
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

    public Integer getOffset() {
        return offset;
    }

    public Integer getWidth() { return this.width; }

    public String getScope() { return this.scope; }
}
