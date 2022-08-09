import java.util.HashMap;
import java.util.Objects;

public class YAPLType {
    private final String id;
    private final HashMap<String, YALPAttribute> attributes;
    private final HashMap<String, YAPLMethod> methods;
    private final YAPLType parent;

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

    /**
     * Validates if parent is an ancestor by name. Works recursively.
     * @param parent YAPLType.
     * @return boolean
     */
    public boolean isAncestor(YAPLType parent) {
        if (this.parent.equals(parent)) {
            return true;
        }
        // Check up until Object in types tree
        if (this.parent.getId().equals("Object") && !parent.getId().equals("Object")) {
            return false;
        }

        return parent.isAncestor(parent);
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