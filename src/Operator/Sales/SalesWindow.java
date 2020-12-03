package Operator.Sales;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DataStructure.BinarySearchTree;
import DataStructure.Stack;
import DataStructure.TreeNode;
import Main.Vender;
import Operator.Operator;

public class SalesWindow extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static SalesWindow sales_window;
	
	private JScrollPane scrollPane;
	private JLabel date_label;
	private JComboBox<String> beverage_combo_box;
	
	private boolean is_group_date;
	
	private BinarySearchTree<GroupDate> sales_items_grouped_date;
	private BinarySearchTree<GroupBeverage> sales_items_grouped_beverage;

	public static SalesWindow create() {
		if (sales_window == null)
			sales_window = new SalesWindow();
		return sales_window;
	}
	
	private SalesWindow() {

		is_group_date = true;
		initDateGroupedItems();
		initBeverageGroupedItems();
		load();
		
		setBounds(100, 100, 402, 490);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton return_to_operator = new JButton("���ư���");
		return_to_operator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleReturnToOperator();
			}
		});
		return_to_operator.setBounds(12, 10, 93, 23);
		panel_1.add(return_to_operator);
		
		date_label = createDateLabel();
		panel_1.add(date_label);
		
		JButton previous_month = new JButton("<");
		previous_month.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handlePrevious();
			}
		});
		previous_month.setBounds(117, 10, 41, 23);
		panel_1.add(previous_month);
		
		JButton next_month = new JButton(">");
		next_month.setBounds(225, 10, 41, 23);
		panel_1.add(next_month);
		next_month.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleNext();
			}
		});
		
		beverage_combo_box = createComboBox();
		beverage_combo_box.setBounds(269, 10, 107, 23);
		panel_1.add(beverage_combo_box);
		
		JTable table = createTableWithDate(date_label.getText());
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 43, 366, 427);
		panel_1.add(scrollPane);
	}
	
	private JLabel createDateLabel() {
		
		LocalDate current = LocalDate.now();
		
		int year = current.getYear();
		int month = current.getMonthValue();
		
		JLabel label = new JLabel(String.format("%d-%d", year, month));
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(157, 12, 69, 19);
		
		return label;
	}
	
	private void initDateGroupedItems() {
		sales_items_grouped_date = new BinarySearchTree<>();
	}
	
	private void initBeverageGroupedItems() {
		sales_items_grouped_beverage = new BinarySearchTree<>();
	}
	
	private JComboBox<String> createComboBox() {
		
		TreeNode<GroupBeverage> root = sales_items_grouped_beverage.getRoot();
		
		Stack<TreeNode<GroupBeverage>> stack = new Stack<>();
		
		JComboBox<String> beverage_combo_box = new JComboBox<>();
		beverage_combo_box.addItem("");	// ���� ���� �� ��� ���� ǥ��
		
		TreeNode<GroupBeverage> temp = root;
		
		// ���� ��ȸ
		while (true) {
			while (temp != null) {
				stack.push(temp);
				temp = temp.getLeft();
			}
			
			temp = stack.pop();
			if (temp == null) break;
			
			/* ����κ� */
			
			String type = temp.getData().getType();
			beverage_combo_box.addItem(type);
			
			/*****************************/
			
			temp = temp.getRight();
		}
		
		beverage_combo_box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				handleSelectBeverage();
			}
		});
		
		return beverage_combo_box;
	}
	
	private JTable createTableWithDate(String date) {
		
		String[] attributes = new String[]{"��¥", "ǰ��", "�ܰ�", "����", "����"};
		String[][] contents = new String[][] {};
		
		GroupDate key = new GroupDate(date+"-01");
		GroupDate searched = sales_items_grouped_date.find(key);
		
		int sum = 0;

		if (searched != null) {
			
			TreeNode<SalesItem> root = searched.getItems().getRoot();
			
			Stack<TreeNode<SalesItem>> stack = new Stack<>();
			
			TreeNode<SalesItem> temp = root;
			
			
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
				
				// ��¥���� ���̺� ����
				contents = MyDate.append(contents, temp.getData().toRow());
				
				/*****************************/
				
				temp = temp.getRight();
			}
			
		}
		
		contents = MyDate.append(contents, new String[][] {{
			null, null, null, null, String.valueOf(sum)
		}});
		
		JTable table = new JTable(contents, attributes);
		table.setEnabled(false);
		return table;
	}
	
	private JTable createTableWithBeverage(String type) {
		
		String[] attributes = new String[]{"��¥", "ǰ��", "�ܰ�", "����", "����"};
		String[][] contents = new String[][] {};
		
		GroupBeverage key = new GroupBeverage(type);
		GroupBeverage searched = sales_items_grouped_beverage.find(key);
		
		int sum = 0;
		
		if (searched != null) {
			
			TreeNode<SalesItem> root = searched.getItems().getRoot();
			
			Stack<TreeNode<SalesItem>> stack = new Stack<>();
			
			TreeNode<SalesItem> temp = root;
			
			
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
				
				// ��¥���� ���̺� ����
				contents = MyDate.append(contents, temp.getData().toRow());
				
				/*****************************/
				
				temp = temp.getRight();
			}
			
		}
		
		contents = MyDate.append(contents, new String[][] {{
			null, null, null, null, String.valueOf(sum)
		}});
		
		JTable table = new JTable(contents, attributes);
		table.setEnabled(false);
		return table;
	}
	
	private void updateTable() {

		JTable table;
		if (is_group_date) {
			String date = date_label.getText();
			table = createTableWithDate(date);
		}
		else {
			String type = (String)beverage_combo_box.getSelectedItem();
			table = createTableWithBeverage(type);
		}
		
		scrollPane.setViewportView(table);
		
		revalidate();
		repaint();
	}
	
	private void setDateLabel(String date) {
		date_label.setText(date);
	}
	
	private String dateToString(LocalDate date) {
		
		String[] _date = date.toString().split("-");	// 2020-12-03
		
		return String.format("%s-%s", _date[0], _date[1]);
	}
	
	private void handleSelectBeverage() {
		
		String type = (String) beverage_combo_box.getSelectedItem();

		switch (type) {
		case "":	// ������ ��� ��� ���� ǥ��
			setGroupDateMode(true);
			break;
		default:	// �������� ��� type �� �´� ����� ���⸸ ǥ��
			setGroupDateMode(false);
			break;
		}
		
		updateTable();
	}
	
	private void setGroupDateMode(boolean value) {
		is_group_date = value;
	}
	
	private void handlePrevious() {
		
		String[] date = date_label.getText().split("-");	// 2020-12
		
		try {
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			
			LocalDate _date = LocalDate.of(year, month, 1);
			_date = _date.minusMonths(1);
			
			String format = dateToString(_date);
			setDateLabel(format);
			
			updateTable();
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void handleNext() {
		
		String[] date = date_label.getText().split("-");	// 2020-12
		
		try {
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			
			LocalDate _date = LocalDate.of(year, month, 1);
			_date = _date.plusMonths(1);
			
			String format = dateToString(_date);
			setDateLabel(format);
			
			updateTable();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void handleReturnToOperator() {
		JPanel main = Operator.operator;
		Vender.vender.setContentPane(main);
	}
	
	private void insertToGroupDate(String date, String type, String price, String amount) {
		GroupDate key = new GroupDate(date);	// ��¥���� ������ �ǹǷ� date �� �Ѱ���
		GroupDate searched = sales_items_grouped_date.find(key);
		
		// ã������ ���ٸ� ����
		if (searched == null) {
			key.insertToSalesItems(date, type, price, amount);
			sales_items_grouped_date.insert(key);
		}
		// ã������ �ִٸ� �ȿ� �ִ� Ʈ���� ����
		else {
			searched.insertToSalesItems(date, type, price, amount);
		}
	}
	
	private void insertToGroupBeverage(String date, String type, String price, String amount) {
		GroupBeverage key = new GroupBeverage(type);	// ���Ằ�� ������ �ǹǷ� type �� �Ѱ���
		GroupBeverage searched = sales_items_grouped_beverage.find(key);
		
		// ã������ ���ٸ� ����
		if (searched == null) {
			key.insertToSalesItems(date, type, price, amount);
			sales_items_grouped_beverage.insert(key);
		}
		// ã������ �ִٸ� �ȿ� �ִ� Ʈ���� ����
		else {
			searched.insertToSalesItems(date, type, price, amount);
		}
	}
	
	private void load() {
		
		System.out.println("���� ����δ� " + new File("").getAbsolutePath());
		File file = new File(".\\src\\data\\sales.txt");
		
		try {
			FileReader reader_f = new FileReader(file);
			BufferedReader reader;
			reader = new BufferedReader(reader_f);
		
			String current = null;
			
			// ������ ������ �˻�
			while ((current = reader.readLine()) != null) {
				String[] token = current.split(" ");	// ��¥, ǰ��, �ܰ�, ������ �и�
				
				String date = token[0];
				String type = token[1];
				String price = token[2];
				String amount = token[3];
				
				insertToGroupDate(date, type, price, amount);
				insertToGroupBeverage(date, type, price, amount);
				//insertToSalesItems(date, type, price, amount);
			}
			
			reader.close();
			reader_f.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
