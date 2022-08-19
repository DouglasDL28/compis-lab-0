import java.util.List;

public class YAPLMethod {
    private final String id;
    private final YAPLType returnType;

    private List<YAPLType> params;

    public YAPLMethod(String id, YAPLType returnType, List<YAPLType> params) {
        this.id = id;
        this.returnType = returnType;
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public YAPLType getReturnType() {
        return returnType;
    }

    public List<YAPLType> getParams() {
        return params;
    }


    @Override
    public String toString() {
        return "YAPLMethod{" +
                "id='" + id + '\'' +
                ", returnType=" + returnType.getId() +
                ", params=" + params.toString() +
                '}';
    }
}