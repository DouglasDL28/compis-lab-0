package compis.lab0;

import java.util.List;

public class YAPLMethod {
    private final String id;
    private YAPLType returnType;
    private boolean returnsSelfType = false;
    private final List<YAPLType> params;


    public YAPLMethod(String id, YAPLType returnType, List<YAPLType> params, boolean returnsSelfType) {
        this.id = id;
        this.returnType = returnType;
        this.params = params;
        this.returnsSelfType = returnsSelfType;
    }

    public boolean returnsSelfType() {
        return this.returnsSelfType;
    }

    public void setReturnType(YAPLType returnType) {
        this.returnType = returnType;
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

    public void setReturnsSelfType(boolean returnsSelfType) {
        this.returnsSelfType = returnsSelfType;
    }
}