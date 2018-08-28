package algorithm;

import java.util.*;

//二叉堆（最大堆）
//优先队列 PriorityQueue
public class BinaryHeap {
	public List<Integer> heap;

	public BinaryHeap() {
		heap = new ArrayList<Integer>();
	}

	// 最大堆向上调整算法
	// param:index:要向上调整的数据的索引
	public void filterUp(int currentIndex) {
		int value = heap.get(currentIndex);
		int parentIndex = -1;

		while (currentIndex > 0) {
			parentIndex = (currentIndex - 1) / 2;
			if (value < heap.get(parentIndex)) break;
			
			heap.set(currentIndex, heap.get(parentIndex));
			currentIndex = parentIndex;
		}
		heap.set(currentIndex, value);
	}

	// 插入
	public void insert(int data) {
		int size = heap.size();

		heap.add(data); // 先插入数组
		filterUp(size); // 后对数组进行上浮
	}

	// 最大堆向下调整算法
	// param:index:要向下调整的数据的索引
	public void filterDown(int currentIndex) {
		int endIndex = heap.size() - 1;
		int value = heap.get(currentIndex);

		while (currentIndex < endIndex) {
			int childIndex = currentIndex * 2 + 1;

			if (childIndex > endIndex) break;
			if (childIndex < endIndex)
				childIndex += heap.get(childIndex) < heap.get(childIndex + 1) ? 1:0;
			if (value > heap.get(childIndex)) break;
				
			heap.set(currentIndex, heap.get(childIndex));
			currentIndex = childIndex;
		}
		heap.set(currentIndex, value);
	}

	// 弹出最大值
	public Integer pop() throws Exception {
		if (heap.isEmpty())
			throw new Exception("Heap is empty now");

		int maxElem = heap.get(0);

		heap.set(0, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);
		if (!heap.isEmpty())
			filterDown(0);
		return maxElem;
	}
	
	// 删除指定元素
	public void remove(int data) throws Exception{
		if (heap.isEmpty())
			throw new Exception("Heap is empty now");
		
		int elemIndex = heap.indexOf(data);
		
		if(elemIndex == -1)
			throw new Exception("Cannot find this elem");
		
		heap.set(elemIndex, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);
		if (!heap.isEmpty())
			filterDown(elemIndex);
	}

	public static void main(String[] args) throws Exception {
		BinaryHeap heap = new BinaryHeap();

		heap.insert(10);
		heap.insert(20);
		heap.insert(50);
		heap.insert(30);
		heap.insert(70);
		heap.insert(20);
		heap.insert(90);
		heap.insert(10);

		System.out.println(heap.heap);
		System.out.println("++++++++++++++++++++++++++++++++++");
		heap.remove(70);
		for (int i = 0; i < 7; i++) {
			System.out.println(heap.pop());
		}
		System.out.println("++++++++++++++++++++++++++++++++++");
		System.out.println(heap.heap);

	}
}
