package compis.lab0;

import java.util.HashMap;

public class YAPLSymbolTable {
    private HashMap<String, YAPLSymbol> symbols;
    private String scope;
    private int offset = 0;

    public YAPLSymbolTable(String scope) {
        this.symbols = new HashMap<String, YAPLSymbol>();
        this.scope = scope;
    }

    public void add(YAPLSymbol symbol) {
        this.symbols.put(symbol.getId(), symbol);
    }

    public String getScope() {
        return scope;
    }

    public YAPLSymbol remove(String id) {
        return this.symbols.remove(id);
    }

    public boolean contains(String id) {
        return this.symbols.containsKey(id);
    }

    public YAPLSymbol get(String id) {
        return symbols.get(id);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}