import java.util.List;

public class YAPLMethod {
    private String id;
    private YAPLType returnType;

    public String getId() {
        return id;
    }

    public YAPLType getReturnType() {
        return returnType;
    }

    public List<YAPLType> getParams() {
        return params;
    }

    private List<YAPLType> params;
}