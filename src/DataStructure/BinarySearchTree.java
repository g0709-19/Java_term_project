package DataStructure;

// ���� ������ �Է¸� �����Ƿ� ���� �������� ����
public class BinarySearchTree <T extends Comparable<T>> {

	private TreeNode<T> root;
	private int size;
	
	public BinarySearchTree() {
		root = null;
		size = 0;
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}
	
	public T find(T key) {
		TreeNode<T> current = root;
		
		while (current != null) {
			// ���� ��� == ã�� ��
			if (current.getData().compareTo(key) == 0)
				return current.getData();
			// ���� ��� > ã�� ��
			else if (current.getData().compareTo(key) > 0)
				current = current.getLeft();
			// ���� ��� < ã�� ��
			else
				current = current.getRight();
		}
		
		return null;
	}
	
	public void insert(T data) {
		TreeNode<T> new_node = new TreeNode<T>(data);	// �ڽ� ��� ���� ���ο� ���
		++size;
		
		// root ������ ���ο� ��尡 root �� ��
		if (root == null) {
			root = new_node;
			System.out.println("3. root �� " + root);
			return;
		}
		
		TreeNode<T> current = root;
		TreeNode<T> parent = null;
		
		while(true) {
			
			parent = current;
			
			// �߰��� ��庸�� ũ��
			if (data.compareTo(current.getData()) < 0) {
				current = current.getLeft();
				
				// �ڽ��� ���ٸ� �� �ڸ��� ����
				if (current == null) {
					parent.setLeft(new_node);
					return;
				}
			// �߰��� ��庸�� ������
			} else {
				current = current.getRight();
				
				// �ڽ��� ���ٸ� �� �ڸ��� ����
				if (current == null) {
					parent.setRight(new_node);
					return;
				}
			}
		}
	}
	
	public int size() {
		return size;
	}
	
}
