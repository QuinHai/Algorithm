package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListedST<Key, Value> implements SymbolTable<Key, Value> {
	private int size;
	private Node head;

	private class Node {
		private Key key;
		private Value value;
		private Node next;

		public Node(Key key, Value value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	@Override
	public void put(Key key, Value value) {
		try {
			if (value == null || key == null)
				throw new Exception("Key or Value can not be null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 判断有无重复键值
		for (Node node = head; node != null; node = node.next) {
			if (key.equals(node.key)) {
				node.value = value;
				return;
			}
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

		head = new Node(key, value, head);
		size++;
	}

	@Override
	public Value get(Key key) {
		for (Node node = head; node != null; node = node.next) {
			if (key.equals(node.key)) {
				return node.value;
			}
		}
		return null;
	}

	@Override
	public Value delete(Key key) {
		Node lastNode = head;
		for (Node node = head; node != null; node = node.next) {
			if (key.equals(node.key)) {
				if (node == head)
					head = node.next;
				else
					lastNode.next = node.next;
				size--;
				return node.value;
			}
			lastNode = node;
		}
		return null;
	}

	@Override
	public boolean containsKey(Key key) {
		for (Node node = head; node != null; node = node.next) {
			if (key.equals(node.key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Key> keys() {
		List<Key> list = new ArrayList<>(size);
		for (Node node = head; node != null; node = node.next) {
			list.add(node.key);
		}
		return list.iterator();
	}

	public static void main(String[] args) {
		LinkedListedST<Integer, String> llst = new LinkedListedST<>();
		llst.put(1, "Hello");
		llst.put(2, "World");
		llst.put(3, "My");
		llst.put(4, "Lady");

		System.out.println(llst.size());
		System.out.println(llst.delete(2));
		System.out.println(llst.size());
		System.out.println(llst.get(4));
		System.out.println(llst.containsKey(3));
		System.out.println(llst.isEmpty());

		for (Iterator<Integer> it = llst.keys(); it.hasNext();) {
			System.out.println(llst.get(it.next()));
		}
	}
}
