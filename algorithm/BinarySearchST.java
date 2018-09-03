package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//有序符号表 二分查找实现
public class BinarySearchST<Key extends Comparable<Key>, Value> implements
		SymbolTable<Key, Value> {

	private List<Key> keys;
	private List<Value> vals;
	private int N; // 符号表大小

	public BinarySearchST(int size) {
		this.keys = new ArrayList<>(size);
		this.vals = new ArrayList<>(size);
		this.N = 0;
	}

	public BinarySearchST() {
		this.keys = new ArrayList<>();
		this.vals = new ArrayList<>();
		this.N = 0;
	}

	@Override
	public void put(Key key, Value value) {
		int keyIndex = rank(key);
		// 键值相同则覆盖
		if (keyIndex < N && keys.get(keyIndex).compareTo(key) == 0) {
			vals.set(keyIndex, value);
			return;
		}
		// 挪动后续元素并添加新元素
		keys.add(keyIndex, key);
		vals.add(keyIndex, value);
		// 更新符号表大小
		N++;
	}

	@Override
	public Value get(Key key) {
		if (isEmpty())
			return null;

		int keyIndex = rank(key);
		// 键值相同则覆盖
		if (keyIndex < N && keys.get(keyIndex).compareTo(key) == 0) {
			return vals.get(keyIndex);
		} else
			return null;
	}

	@Override
	public Value delete(Key key) {
		Value val = get(key);
		if (null == val)
			return null;

		for (int i = rank(key); i < N - 1; i++) {
			keys.set(i, keys.get(i + 1));
			vals.set(i, vals.get(i + 1));
		}
		keys.set(N - 1, null);
		vals.set(N - 1, null);
		N--;
		return val;
	}

	@Override
	public boolean containsKey(Key key) {
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return N == 0;
	}

	@Override
	public int size() {
		return N;
	}

	public Key min() {
		return keys.get(0);
	}

	public Key max() {
		return keys.get(N - 1);
	}

	// 返回小于给定键值的键的数量(若存在这个键，即为给定键值的索引)
	public int rank(Key key) {
		return rank(key, 0, N - 1);
	}

	// rank方法的递归实现 (二分查找)
	public int rank(Key key, int lo, int hi) {
		if (hi < lo)
			return lo; // 不存在这个键，也应该返回小于这个键的键的数量

		int mid = (hi + lo) / 2;
		int cmp = key.compareTo(keys.get(mid));
		if (cmp < 0)
			return rank(key, lo, mid - 1);
		else if (cmp > 0)
			return rank(key, mid + 1, hi);
		else
			return mid;
	}

	/*
	 * //迭代实现 
	 * public int rank(Key key) { int lo = 0, hi = N-1; while(lo <= hi){
	 * int mid = (hi + lo) / 2; int cmp = key.compareTo(keys.get(mid)); if(cmp <
	 * 0) hi = mid-1; else if(cmp > 0) lo = mid+1; else return mid; } return lo;
	 * }
	 */

	//小于等于key的最大键
	public Key floor(Key key) {
		int keyIndex = rank(key);
		for(int i = keyIndex; i >= 0; i--){
			if(key.compareTo(keys.get(i)) <= 0){
				return keys.get(i);
			}
		}
		return null;
	}
	
	//大于等于key的最小键
	public Key ceiling(Key key){
		int keyIndex = rank(key);
		return keys.get(keyIndex);
	}

	@Override
	public Iterator<Key> keys() {
		return keys.iterator();
	}

	public static void main(String[] args) {
		BinarySearchST<Integer, String> bsst = new BinarySearchST<>();

		bsst.put(1, "Hello");
		bsst.put(2, "World");
		bsst.put(3, "My");
		bsst.put(4, "Lady");

		System.out.println(bsst.get(2));
		System.out.println(bsst.size());
		System.out.println(bsst.delete(1));
		System.out.println(bsst.size());
		System.out.println(bsst.get(4));

	}
}
