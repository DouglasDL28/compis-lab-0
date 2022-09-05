package compis.lab0;

import java.util.Stack;

public class YAPLScopesStack {
    private Stack<YAPLSymbolTable> scopes;

    public YAPLScopesStack() {
        this.scopes = new Stack<YAPLSymbolTable>();
    }

    public void put(YAPLSymbolTable symTable) {
        this.scopes.push(symTable);
    }

    public YAPLSymbolTable pop() {
        return this.scopes.pop();
    }
}