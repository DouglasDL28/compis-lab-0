import java.util.HashMap;

/**
 * Class that defines the Types Table with a HashMap.
 */
public class YAPLTypesTable {
    private HashMap<String, YAPLType> types;


    /**
     * Constructor with types (Object, Int, String, SELF_TYPE & Bool).
     */
    public YAPLTypesTable() {
        YAPLType objectType = new YAPLType("Object", null);
        this.types = new HashMap<>() {{
            put("Object", objectType);
            put("Int", new YAPLType("Int", objectType));
            put("String", new YAPLType("String", objectType));
            put("Bool", new YAPLType("Bool", objectType));
            put("SemError", new YAPLType("SemError", objectType)); // TODO: chequear parent
            put("SELF_TYPE", new YAPLType("SELF_TYPE", objectType)); // TODO: chequear parent
            put("IO", new YAPLType("IO", objectType));
        }};
    }

    /**
     * Add new type to table.
     * @param newType new type to add.
     * @return if type could be added.
     */
    public boolean addType(YAPLType newType) {
        if (containsType(newType.getId()))
            return false;

        this.types.put(newType.getId(), newType);
        return true;
    }

    /**
     * Checks by id if type exists.
     * @param id
     * @return
     */
    public boolean containsType(String id) {
        return this.types.containsKey(id);
    }

    public YAPLType getType(String id) {
        return this.types.get(id);
    }
}
