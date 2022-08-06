import java.util.HashMap;
import java.util.Objects;

public class YAPLType {
    private String id;
    private HashMap<String, YALPAttribute> attributes;
    private HashMap<String, YAPLMethod> methods;
    private YAPLType parent;

    public YAPLType(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
        this.parent = new YAPLType("Object");
    }

    public YAPLType(String id, YAPLType parent) {
        this.id = id;
        this.parent = parent;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public HashMap<String, YALPAttribute> getAttributes() {
        return attributes;
    }

    public HashMap<String, YAPLMethod> getMethods() {
        return methods;
    }

    public YAPLType getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YAPLType yaplType = (YAPLType) o;
        return id.equals(yaplType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
