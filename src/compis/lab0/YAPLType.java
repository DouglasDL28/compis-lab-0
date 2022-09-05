package compis.lab0;

import java.util.HashMap;
import java.util.Objects;

public class YAPLType {
    private final String id;
    private final HashMap<String, YALPAttribute> attributes;
    private final HashMap<String, YAPLMethod> methods;
    private YAPLType parent;
    private int depth;


    public YAPLType(String id, YAPLType parent, int depth) {
        this.id = id;
        this.parent = parent;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
        this.depth = depth;
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

    public Boolean putMethod() {
       return true;
    }
    public YAPLType getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setParent(YAPLType parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Validates if parent is an ancestor by name. Works recursively.
     * @param parent YAPLType.
     * @return boolean
     */
    public boolean isDescendantOf(YAPLType typeToCheck) {
        // Check up until Object in types tree


        // Object is not descendant of anyone
        if (this.getId().equals("Object")) {
            return false;
        }

        // found typeToCheck in type's ancestors
        if (this.parent.equals(typeToCheck)) {
            return true;
        }

        return this.parent.isDescendantOf(typeToCheck);
    }

    /**
     * TODO: tomar en cuenta depth.
     * Find common ancestor between two types
     * @param type
     * @return common ancestor
     */
    public YAPLType commonAncestorWith(YAPLType type) {
        System.out.println("Finding common ancestor of " + this.id + " and " + type.getId());

        if (this.equals(type)) {
            System.out.println("Common ancestor: " + this.getId());
            return this;
        }

        if (this.getDepth() > type.getDepth()) {
            return this.parent.commonAncestorWith(type);
        }

        if (this.getDepth() < type.getDepth()) {
            return this.commonAncestorWith(type.parent);
        }

        return this.parent.commonAncestorWith(type.parent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YAPLType yaplType = (YAPLType) o;
        return id.equals(yaplType.id);
    }

    @Override
    public String toString() {
        return "YAPLType{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                ", methods=" + methods +
                ", parent=" + parent +
                ", depth=" + depth +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
