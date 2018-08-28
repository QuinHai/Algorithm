package algorithm;

public class BinaryTree {
	private BinaryTree lc = null; // left child
	private BinaryTree rc = null; // right child
	private Object data = null;
	
	public BinaryTree(Object data) {
		this.lc = null;
		this.rc = null;
		this.data = data;
	}
	
	public static BinaryTree createBinaryTree(Object[] data){
		BinaryTree root = new BinaryTree(data[0]);
		
		for(int i = 1;i < data.length;i ++){
			root.add(new BinaryTree(data[i]));
		}
		
		return root;
		
	}
	
	public void add(BinaryTree node){
		if(lc == null)
			lc = node;
		else if(rc == null)
			rc = node;
		else if(lc != null && (lc.lc == null || lc.rc == null))
			lc.add(node);
		else
			rc.add(node);
	}
	
	//先序遍历
	public void preorder(){
		System.out.println(data);
		if(lc != null)
			lc.preorder();
		if(rc != null)
			rc.preorder();
	}
	
	//中序遍历
	public void inorder(){
		if(lc != null)
			lc.inorder();
		System.out.println(data);
		if(rc != null)
			rc.inorder();
	}
	
	//后续遍历
	public void afterorder(){
		if(lc != null)
			lc.afterorder();
		if(rc != null)
			rc.afterorder();
		System.out.println(data);
	}
	
	public static void main(String[] args) {
		String[] s = new String[5];
		s[0] = "A";
		s[1] = "B";
		s[2] = "C";
		s[3] = "D";
		s[4] = "E";
		
		
		BinaryTree root = BinaryTree.createBinaryTree(s);
		
		root.add(new BinaryTree("F"));
		
		root.preorder();
		System.out.println("+++++++++++++++++++++++++++");
		root.inorder();
		System.out.println("+++++++++++++++++++++++++++");
		root.afterorder();
		System.out.println("+++++++++++++++++++++++++++");
	}

}
