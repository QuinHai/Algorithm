package algorithm;


class Sort {
	public static void Log(Object a) {
		System.out.println(a);
	}

	public static void Log(int... a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
		System.out.println();
	}

	public static void Exch(int[] arr, int a, int b) {
		int mid = arr[a];
		arr[a] = arr[b];
		arr[b] = mid;
	}

	// 选择排序
	public static void sort1(int[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = a[i];
			int change = i;
			for (int j = i + 1; j < N; j++) {
				if (min > a[j]) {
					min = a[j];
					change = j;
				}
			}
			int mid = a[i];
			a[i] = min;
			a[change] = mid;
		}
	}

	public static void sort2(int[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				if (a[i] > a[j]) {
					Exch(a, i, j);
				}
	}

	// 插入排序
	public static void sort3(int[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0; j--) {
				if (a[j] < a[j - 1])
					Exch(a, j - 1, j);
				else
					break;// 插入排序保证前列有序，所以一旦不符合a[j] < a[j-1]则前面所有元素均不符合
			}
		}
	}

	// 希尔排序
	public static void sort4(int[] a) {
		int N = a.length;
		int gap = N / 2; // 间隔步长
		while (gap > 0) {
			for (int i = N - 1; i >= N - gap; i--)
				for (int j = i; j >= gap; j -= gap)// 遍历等步长元素
					for (int k = j; k >= gap; k -= gap)// 等步长元素插入排序
						if (a[k] < a[k - gap])
							Exch(a, k, k - gap);
						else
							break;
			gap /= 2;
		}
	}

	// 希尔排序优化
	public static void sort5(int[] a) {
		int N = a.length;
		int gap = N;
		while (gap > 1) {
			gap = gap / 3 + 1;// 间隔步长(n/3+1数学上最优)
			for (int i = gap; i < N; i++)// 从低位开始遍历，相当于插入排序第一层循环
				for (int j = i; j >= gap; j -= gap)// 遍历等步长元素
					if (a[j] < a[j - gap])
						Exch(a, j, j - gap);
					else
						break;
		}
	}

	// 归并排序 MergeSorts
	// 自顶向下/*先拆分后合并*/
	// 辅助数组
	static int[] acp;

	public static void merge(int[] a, int l, int m, int h) {
		// c1,c2表示归并的左右起点
		// 之后表示当前归并点
		int c1 = l, c2 = m + 1;
		// 复制
		for (int i = l; i <= h; i++) {
			acp[i] = a[i];
		}
		// 将acp前半段和后半段中的数据归并到a中
		for (int i = l; i <= h; i++) {
			if (c1 > m)
				a[i] = acp[c2++];
			else if (c2 > h)
				a[i] = acp[c1++];
			else if (acp[c2] < acp[c1])
				a[i] = acp[c2++];
			else
				a[i] = acp[c1++];
		}
	}

	// 不断递归将数组二分到最小，然后依次归并
	private static void sort6(int[] a, int l, int h) {
		if (h <= l)
			return;
		// 计算中间索引
		int mid = l + (h - l) / 2;
		// 归并排序左半
		sort6(a, l, mid);
		// 归并排序右半
		sort6(a, mid + 1, h);
		// 原地归并
		merge(a, l, mid, h);
	}

	public static void sort6A(int[] a) {
		acp = new int[a.length];
		sort6(a, 0, a.length - 1);
	}

	// 自底向上/*直接一个一个合并*/
	public static void sort6B(int[] a) {
		int N = a.length;
		acp = new int[N];
		// as ArraySize 归并子数组大小
		// index 子数组在主数组中索引索引
		for (int as = 1; as < N; as *= 2) {// 一长度数组-》二长度数组-》四-》八......
			for (int index = 0; index < N - as; index += 2 * as) {// 每次归并左起相邻两个数组
				merge(a, index, index + as - 1, Math.min(index + 2 * as - 1, N - 1));
			}
		}
	}

	// 快速排序
	public static void sort7(int[] a, int l, int h) {
		if (l >= h)
			return;
		//将片段的开始(a[l])作为基准数
		int i = l;// i低位
		int j = h+1; // j高位
		while (true) {
			while(a[++i] < a[l]) if(i == h) break;//低位遍历 直到元素大于等于基准数
			while(a[--j] > a[l]) if(j == l) break;//高位遍历 直到元素小于等于基准数
			if(i >= j) break;	//不合要求就退出
			Exch(a, i, j);
		}
		Exch(a, j, l);
		sort7(a, l, j - 1);
		sort7(a, j + 1, h);
	}

	public static void sort7(int[] a) {
		sort7(a, 0, a.length - 1);
	}
	
	//堆排序
	public static void sort8(int[] a){
		int endIndex = a.length -1 ;
		
		for(int i = (endIndex-1)/ 2;i >= 0;i --){
			Sink(a, i, endIndex);
		}
		while(endIndex > 0){
			Exch(a, 0, endIndex--); 
			Sink(a, 0, endIndex);
		}
	}
	//下沉
	public static void Sink(int[] a, int currentIndex, int endIndex){
		int value = a[currentIndex];
		
		while(currentIndex < endIndex){
			int childIndex = currentIndex*2 +1;
			
			if(childIndex > endIndex) break;
			if(childIndex < endIndex)
				childIndex += a[childIndex] < a[childIndex+1]?1:0;
			if (value > a[childIndex]) break;
			
			a[currentIndex] = a[childIndex];
			currentIndex = childIndex;
		}
		a[currentIndex] = value; 
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		int[] a = { 8, 1,9, 1, 7, 2, 2, 5, 4, 6,11, 32,1,0,243}; // 测试数据
		Sort.sort8(a);
		Sort.Log(a);

		// 程序运行时间
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间:" + (endTime - startTime) + "ms");
	}
}
