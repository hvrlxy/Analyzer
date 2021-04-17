import java.util.HashMap;

/** A ScopedMap is similar to a Map, but with nested scopes. */
public class ScopedMap<K,V> {

    private Integer nestingLevel;
    private Scope currentScope;

    public class Scope{
        private HashMap localMap;
        private Scope parentScope;

        public Scope(){
            this.localMap = new HashMap<K,V>();
            this.parentScope = null;
        }
    }

    /** makes a ScopedMap that maps no keys to values and is set to the global scope (nesting level 0) */
    public ScopedMap(){
    	// TODO: write this
        this.nestingLevel = 0;
        this.currentScope = new Scope();
    }

    /** sets the ScopedMap to a new scope nested inside the previous one;
        the nesting level increases by one */
    public void enterScope(){
        // TODO: write this
        this.nestingLevel ++;
        Scope newScope = new Scope();
        newScope.parentScope = this.currentScope;
        this.currentScope = newScope;
    }

    /** exits from the current scope back to the containing one;
        the nesting level, which must have been positive, decreases by one */
    public void exitScope(){
        // TODO: write this
        this.nestingLevel --;
        Scope parentScope = this.currentScope.parentScope;
        this.currentScope = parentScope;
    }

    /** puts the key/value pair in at the current scope;
        if the key is already in at the current nesting level,
        the new value replaces the old one;
        neither the key nor the value may be null */
    public void put(K key, V value) {
        // TODO: write this
        this.currentScope.localMap.put(key, value);
    }

    /** gets the value corresponding to the key, at the innermost scope for which there is one;
        if there is none, returns null */
    public V get(K key) {
        // TODO: write this
        Scope s = this.currentScope;
        while (s != null){
            V levelkey = (V) s.localMap.get(key);
            if (levelkey != null){
                return levelkey;
            }
            s = s.parentScope;
        }
        return null;
    }
    
    /** returns true if the key has a value at the current scope (ignoring surrounding ones) */
    public boolean isLocal(K key) {
        // TODO: write this
    	return this.currentScope.localMap.containsKey(key);
    }
    
    /** returns the current nesting level */
    public int getNestingLevel() {
        // TODO: write this
    	return this.nestingLevel;
    }
}
