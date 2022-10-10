package compis.lab0;

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
        YAPLType intType = new YAPLType("Int", objectType, 1, 4, "0");
        YAPLType stringType = new YAPLType("String", objectType, 1, 256, "\"\"");
        YAPLType boolType = new YAPLType("Bool", objectType, 1, 1, "TRUE");
        YAPLType ioType = new YAPLType("IO", objectType, 1);
        this.types = new HashMap<>() {{
            put("Object", objectType);
            put("Int", intType);
            put("String", stringType);
            put("Bool", boolType);
//            put("SemError", semErrorType);
            put("IO", ioType);
        }};

        // Object Methods

        // abort(): Object
        objectType.getMethods().put("abort()", new YAPLMethod("abort", objectType, new ArrayList<>(), false));
        // type_name(): String
        objectType.getMethods().put("type_name()", new YAPLMethod("type_name", stringType, new ArrayList<>(), false));
        // copy(): SELF_TYPE
        objectType.getMethods().put("copy()", new YAPLMethod("copy", objectType, new ArrayList<>(), true));

        // IO Methods

        // abort(): Object
        ioType.getMethods().put("abort()", new YAPLMethod("abort", ioType, new ArrayList<>(), false));
        // type_name(): String
        ioType.getMethods().put("type_name()", new YAPLMethod("type_name", stringType, new ArrayList<>(), false));
        // copy(): SELF_TYPE
        ioType.getMethods().put("copy()", new YAPLMethod("copy", ioType, new ArrayList<>(), true));

        // out_string(x: String) : SELF_TYPE
        ioType.getMethods().put("out_string(String)", new YAPLMethod("out_string", ioType, List.of(stringType), true));
        // out_int(x: Int):SELF_TYPE
        ioType.getMethods().put("out_int(Int)", new YAPLMethod("out_int", ioType, List.of(intType), true));
        // in_string() : String
        ioType.getMethods().put("in_string()", new YAPLMethod("in_string", stringType, new ArrayList<>(), false));
        // in_int(): Int
        ioType.getMethods().put("in_int()", new YAPLMethod("in_int", intType, new ArrayList<>(), false));


        // String methods

        // abort(): Object
        stringType.getMethods().put("abort()", new YAPLMethod("abort", stringType, new ArrayList<>(), false));
        // type_name(): String
        stringType.getMethods().put("type_name()", new YAPLMethod("type_name", stringType, new ArrayList<>(), false));
        // copy(): SELF_TYPE
        stringType.getMethods().put("copy()", new YAPLMethod("copy", stringType, new ArrayList<>(), true));

        // length() : Int
        stringType.getMethods().put("length()", new YAPLMethod("length", intType, new ArrayList<>(), false));
        // concat(s: String) : String
        stringType.getMethods().put("concat(String)", new YAPLMethod("concat", stringType, new ArrayList<>(), false));
        // substr(i: Int, l: Int) : String
        stringType.getMethods().put("substr(Int, Int)", new YAPLMethod("substr", stringType, Arrays.asList(intType, intType), false));

        // Int methods (only Object methods)

        // abort(): Object
        intType.getMethods().put("abort()", new YAPLMethod("abort", intType, new ArrayList<>(), false));
        // type_name(): String
        intType.getMethods().put("type_name()", new YAPLMethod("type_name", stringType, new ArrayList<>(), false));
        // copy(): SELF_TYPE
        intType.getMethods().put("copy()", new YAPLMethod("copy", intType, new ArrayList<>(), true));


        // Bool methods (only methods inherited from Object)

        // abort(): Object
        boolType.getMethods().put("abort()", new YAPLMethod("abort", boolType, new ArrayList<>(), false));
        // type_name(): String
        boolType.getMethods().put("type_name()", new YAPLMethod("type_name", stringType, new ArrayList<>(), false));
        // copy(): SELF_TYPE
        boolType.getMethods().put("copy()", new YAPLMethod("copy", boolType, new ArrayList<>(), true));
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

    public List<YAPLType> getAllTypes() {
        return new ArrayList<YAPLType>(this.types.values());
    }
}
