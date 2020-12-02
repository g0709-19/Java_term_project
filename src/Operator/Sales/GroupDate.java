package Operator.Sales;

import DataStructure.BinarySearchTree;
import DataStructure.Stack;
import DataStructure.TreeNode;

// SalesItem �� ��¥���� ����
public class GroupDate implements Comparable<GroupDate> {
	
	private static int ATTRIBUTE_LENGTH = 5;
	
	private String year_month;
	private BinarySearchTree<SalesItem> items;
	
	public GroupDate(String date) {
		
		String[] date_array = date.split("-");	// 2020-12-03
		year_month = String.format("%s-%s", date_array[0], date_array[1]);	// ��, �� ����
		items = new BinarySearchTree<>();
	}
	
	public BinarySearchTree<SalesItem> getItems() {
		return items;
	}
	
	public String[][] toRow() {
		
		//int size = items.size();
		String[][] row = new String[][] {};
		
		//int i = 0;
		TreeNode<SalesItem> root = items.getRoot();
		
		Stack<TreeNode<SalesItem>> stack = new Stack<>();
		
		TreeNode<SalesItem> temp = root;
		
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
			
			row = append(row, _row);	// �� �迭 ��ġ�� �Լ�
			
			/*****************************/
			
			temp = temp.getRight();
		}
		
		row = append(row, new String[][] {{
			null, null, null, null, String.valueOf(sum)
		}});
		
		return row;
	}
	
    public static String[][] append(String[][] a, String[][] b) {
    	String[][] result = new String[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	public void insertToSalesItems(String date, String type, String price, String amount) {
		SalesItem key = new SalesItem(date);
		SalesItem searched = items.find(key);
		
		// ã������ ���ٸ� ����
		if (searched == null) {
			key.insertToSalesItems(type, price, amount);
			items.insert(key);
			System.out.println("4. " + date + " �߰�");
		}
		// ã������ �ִٸ� �ȿ� �ִ� Ʈ���� ����
		else {
			System.out.println("5. ã���� ���� " + searched.getDate().toString());
			searched.insertToSalesItems(type, price, amount);
			System.out.println("4. ������ �־ " + date + " �� " + type + " " + price + " " + amount + " ����");
		}
	}
	
	// ���� ���̸� ����
	public void insert(String date, String type, String price, String amount) {
		SalesItem key = new SalesItem(date);
		SalesItem searched = null;
		
		TreeNode<SalesItem> root = items.getRoot();
		TreeNode<SalesItem> current = root;
		
		while (current != null) {
			// ���� ��� == ã�� ��
			System.out.println("groupdate. " + current.getData().getDate().toString() + " " + current.getData().isSameMonth(key));
			if (current.getData().isSameMonth(key)) {
				searched = current.getData();
				break;
			}
			// ���� ��� > ã�� ��
			else if (current.getData().compareTo(key) > 0)
				current = current.getLeft();
			// ���� ��� < ã�� ��
			else
				current = current.getRight();
		}
		
		// ã������ ���ٸ� ����
		if (searched == null) {
			key.insertToSalesItems(type, price, amount);
			items.insert(key);
		}
		// ã������ �ִٸ� �ȿ� �ִ� Ʈ���� ����
		else {
			searched.insertToSalesItems(type, price, amount);
		}
	}

	private int compare(GroupDate g) {
		return year_month.compareTo(g.year_month);
	}
	
	@Override
	public int compareTo(GroupDate g) {
		return compare(g);
	}
}
