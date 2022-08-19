import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class that defines the Types Table with a HashMap.
 */
public class YAPLTypesTable {
    private HashMap<String, YAPLType> types;


    /**
     * Constructor with types (Object, Int, String, SELF_TYPE & Bool).
     */
    public YAPLTypesTable() {
        YAPLType objectType = new YAPLType("Object", null, 0);
        YAPLType intType = new YAPLType("Int", objectType, 1);
        YAPLType stringType = new YAPLType("String", objectType, 1);
        YAPLType boolType = new YAPLType("Bool", objectType, 1);
        YAPLType semErrorType = new YAPLType("SemError", objectType, 1); // TODO: chequear parent
        YAPLType ioType = new YAPLType("IO", objectType, 1);
        this.types = new HashMap<>() {{
            put("Object", objectType);
            put("Int", intType);
            put("String", stringType);
            put("Bool", boolType);
            put("SemError", semErrorType);
            put("IO", ioType);
        }};

        // Object Methods
        // abort(): Object
        objectType.getMethods().put("abort", new YAPLMethod("abort", objectType, new ArrayList<>()));
        // type_name(): String
        objectType.getMethods().put("type_name", new YAPLMethod("type_name", stringType, new ArrayList<>()));
        // copy(): SELF_TYPE
        objectType.getMethods().put("copy", new YAPLMethod("copy", objectType, new ArrayList<>()));

        // IO Methods
        // out_string(x: String) : SELF_TYPE
        ioType.getMethods().put("out_string", new YAPLMethod("out_string", ioType, List.of(stringType)));
        // out_int(x: Int):SELF_TYPE
        ioType.getMethods().put("out_int", new YAPLMethod("out_int", ioType, List.of(intType)));
        // in_string() : String
        ioType.getMethods().put("in_string", new YAPLMethod("in_string", stringType, new ArrayList<>()));
        // in_int(): Int
        ioType.getMethods().put("in_int", new YAPLMethod("in_int", intType, new ArrayList<>()));

        // String methods
        // length() : Int
        stringType.getMethods().put("length", new YAPLMethod("length", intType, new ArrayList<>()));
        // concat(s: String) : String
        stringType.getMethods().put("concat", new YAPLMethod("concat", stringType, new ArrayList<>()));
        // substr(i: Int, l: Int) : String
        stringType.getMethods().put("substr", new YAPLMethod("substr", stringType, Arrays.asList(intType, intType)));

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

    public int size() {
        return this.types.size();
    }
}
