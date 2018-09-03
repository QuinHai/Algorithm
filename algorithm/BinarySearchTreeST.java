package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

//有序符号表 二叉查找树实现
public class BinarySearchTreeST<Key extends Comparable<Key>, Value> implements
		SymbolTable<Key, Value> {
	private Node root;

	private class Node {
		private Key key;
		private Value value;
		private Node left, right;
		private int size; // 节点计数器 以该节点为根的子树中节点总数（包含这个节点）

		public Node(Key key, Value value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}
	}

	@Override
	public void put(Key key, Value value) {
		if (null == value) {
			delete(key);
			return;
		}
		root = put(root, key, value);
	}

	private Node put(Node root, Key key, Value value) {
		if (null == root)
			return new Node(key, value, 1);

		int cmp = key.compareTo(root.key);
		if (cmp > 0)
			root.right = put(root.right, key, value); // 大值放右儿子节点
		else if (cmp < 0)
			root.left = put(root.left, key, value); // 小值放左儿字节点
		else
			root.value = value; // key值相等则更新value
		root.size = size(root.left) + size(root.right) + 1; // 更新root的size
		return root;
	}

	@Override
	public Value get(Key key) {
		return get(root, key);
	}

	private Value get(Node root, Key key) {
		if (root == null) return null; // 未查找到

		int cmp = key.compareTo(root.key);
		if (cmp > 0) return get(root.right, key); // 大于则查找右儿子节点
		else if (cmp < 0) return get(root.left, key); // 小于则查找左儿子节点
		else return root.value;
	}

	//删除最小的键
	public void deleteMin(){
		root = deleteMin(root);
	}
	private Node deleteMin(Node node){
		//若左儿子节点为空，则当前节点位最小节点，删除此节点并将当前节点的右儿子节点链接到当前节点父节点的左儿子节点
		if(null == node.left) return node.right;
		//若左儿子节点不为空，则当前节点不是最小，继续搜索
		node.left = deleteMin(node.left);
		//更新节点size
		node.size = size(node.left) + size(node.right) + 1;
		
		return node;
	}
	
	@Override
	public Value delete(Key key) {
		Value val = get(key);
		root = delete(root, key);
		return val;
	}
	private Node delete(Node node, Key key){
		if(null == node) return null;
		
		int cmp = key.compareTo(node.key);
		if(cmp > 0) node.right = delete(node.right, key);
		else if(cmp < 0) node.left = delete(node.left, key);
		else{
			if(node.left == null) return node.right;
			if(node.right == null) return node.left; //左右孩子不全，则直接补上
			Node mid = node;
			node = min(node.right);
			node.right = deleteMin(node.right);
			node.left = node.left;
		}
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	@Override
	public boolean containsKey(Key key) {
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (null == node)
			return 0;
		return node.size;
	}

	// 小于等于key的最大键
	public Key floor(Key key) {
		Node node = floor(root, key);
		if(node == null) return null;
		return node.key;
	}
	private Node floor(Node node, Key key) {
		if (node == null) return null;

		int cmp = key.compareTo(node.key);

		if (cmp == 0) return node; // 找到节点
		else if (cmp < 0) return floor(node.left, key); // 比key值大,则向左儿子节点搜索

		Node t = floor(node.right, key);
		if (t != null) return t;
		else return node;
	}

	// 大于等于key的最小键
	public Key ceiling(Key key) {
		Node node = ceiling(root, key);
		if(node == null) return null;
		return node.key;
	}
	private Node ceiling(Node node, Key key){
		if (node == null) return null;

		int cmp = key.compareTo(node.key);

		if (cmp == 0) return node; // 找到节点
		else if (cmp > 0) return ceiling(node.right, key); // 比key值小,则向右儿子节点搜索

		Node t = ceiling(node.left, key);
		if (t != null) return t;
		else return node;
	}
	
	//返回排位位k的节点
	public Key select(int k){
		return select(root, k).key;
	}
	private Node select(Node node, int k){
		if(node == null) return null;
		
		int size = size(node.left);
		if(size < k)
			return select(node.right, k);
		else if(size > k)
			return select(node.left, k);
		else
			return node;
	}
	
	public int rank(Key key){
		return rank(root, key);
	}
	private int rank(Node node, Key key){
		if(node == null) return 0;
		
		int cmp = key.compareTo(node.key);
		//若节点比key小，则其左儿子节点均比key小
		if(cmp > 0) return rank(node.right, key) + size(node.left) + 1; 
		//若节点比key大，则继续查左儿子节点
		else if(cmp < 0) return rank(node.left, key);
		else return size(node.left);
	}
	
	public Key min(){
		return min(root).key;
	}
	private Node min(Node node){
		if(node.left == null) return node;
		return min(node.left);
	}
	
	public Key max(){
		return max(root).key;
	}
	private Node max(Node node){
		if(node.right == null) return node;
		return max(node.right);
	}

	@Override
	public Iterator<Key> keys() {
		return keys(min(), max());
	}
	//获取特定范围key
	public Iterator<Key> keys(Key lo, Key hi){
		List<Key> list = new ArrayList<Key>();
		keys(root, list, lo, hi);
		return list.iterator();
	}
	
	private void keys(Node node, List<Key> list, Key lo, Key hi){
		if(node == null) return;
		
		int cmplo = lo.compareTo(node.key);
		int cmphi = hi.compareTo(node.key);
		if(cmplo < 0) keys(node.left, list, lo, hi);
		if(cmplo <= 0 && cmphi >= 0) list.add(node.key);
		if(cmphi > 0) keys(node.right, list, lo, hi);
	}
	
	public static void main(String[] args) {
		BinarySearchTreeST<Integer, String> bstst = new BinarySearchTreeST<>();
		
		bstst.put(20, "Hello");
		bstst.put(10, "World");
		bstst.put(30, "!");
		bstst.put(15, "My");
		bstst.put(25, "Lady");
		
		System.out.println(bstst.get(15));
		System.out.println(bstst.size());
		System.out.println(bstst.delete(30));
		System.out.println(bstst.get(25));
		System.out.println(bstst.size());
		
		for(Iterator<Integer> i = bstst.keys();i.hasNext();){
			System.out.println(i.next());
		}
	}

}
