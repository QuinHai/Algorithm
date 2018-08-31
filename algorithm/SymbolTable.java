package algorithm;

import java.util.Iterator;

public interface SymbolTable<Key, Value> {
	public void put(Key key, Value value);
	public Value get(Key key);
	public Value delete(Key key);
	public boolean containsKey(Key key);
	public boolean isEmpty();
	public int size();
	Iterator<Key> keys();
}
