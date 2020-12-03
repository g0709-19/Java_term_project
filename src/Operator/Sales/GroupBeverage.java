package Operator.Sales;

import DataStructure.BinarySearchTree;
import DataStructure.Stack;
import DataStructure.TreeNode;

// SalesItem �� ��¥���� ����
public class GroupBeverage implements Comparable<GroupBeverage> {
	
	//private static int ATTRIBUTE_LENGTH = 5;
	
	private String type;
	private BinarySearchTree<GroupDate> items;
	
	public GroupBeverage(String type) {
		
		this.type = type;
		items = new BinarySearchTree<>();
	}
	
	public BinarySearchTree<GroupDate> getItems() {
		return items;
	}
	
	public String[][] toRow() {
		
		//int size = items.size();
		String[][] row = new String[][] {};
		
		//int i = 0;
		TreeNode<GroupDate> root = items.getRoot();
		
		Stack<TreeNode<GroupDate>> stack = new Stack<>();
		
		TreeNode<GroupDate> temp = root;
		
		int sum = 0;
		
		// ���� ��ȸ
		while (true) {
			while (temp != null) {
				stack.push(temp);
				temp = temp.getLeft();
			}
			
			temp = stack.pop();
			if (temp == null) break;
			
			/* ����κ� */
			
			// ��¥���� ���̺� ����
			String[][] _row = temp.getData().toRow();
			
			// ���� ���
			for (int i=0; i<_row.length; ++i) {
				int sales = Integer.parseInt(_row[i][4]);
				sum += sales;
			}
			
			row = MyDate.append(row, _row);	// �� �迭 ��ġ�� �Լ�
			
			/*****************************/
			
			temp = temp.getRight();
		}
		
		row = MyDate.append(row, new String[][] {{
			null, null, null, null, String.valueOf(sum)
		}});
		
		return row;
	}

	public void insertToSalesItems(String date, String type, String price, String amount) {
		GroupDate key = new GroupDate(date);
		GroupDate searched = items.find(key);
		
		// ã������ ���ٸ� ����
		if (searched == null) {
			key.insertToSalesItems(date, type, price, amount);
			items.insert(key);
		}
		// ã������ �ִٸ� �ȿ� �ִ� Ʈ���� ����
		else {
			searched.insertToSalesItems(date, type, price, amount);
		}
	}
	
	public String getType() {
		return type;
	}
	
	private int compare(GroupBeverage g) {
		return type.compareTo(g.type);
	}
	
	@Override
	public int compareTo(GroupBeverage g) {
		return compare(g);
	}
}
