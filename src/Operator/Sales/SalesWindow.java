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
import javax.swing.DefaultComboBoxModel;
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
	
	private BinarySearchTree<GroupDate> sales_items_grouped_date;

	public static SalesWindow create() {
		if (sales_window == null)
			sales_window = new SalesWindow();
		return sales_window;
	}
	
	private SalesWindow() {
		
		initDateGroupedItems();
		load();
		
		setBounds(100, 100, 402, 490);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton return_to_operator = new JButton("돌아가기");
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"모두", "물"}));
		comboBox.setBounds(269, 10, 107, 23);
		panel_1.add(comboBox);
		
		JTable table = createTable(date_label.getText());
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 43, 366, 427);
		panel_1.add(scrollPane);
	}
	
	private JLabel createDateLabel() {
		
		LocalDate current = LocalDate.now();
		
		int year = current.getYear();
		int month = current.getMonthValue();
		
		JLabel label = new JLabel(String.format("%d-%d", year, month));
		label.setFont(new Font("굴림", Font.PLAIN, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(157, 12, 69, 19);
		
		return label;
	}
	
	private void initDateGroupedItems() {
		sales_items_grouped_date = new BinarySearchTree<>();
	}
	
	private JTable createTable(String date) {
		
		String[] attributes = new String[]{"날짜", "품목", "단가", "개수", "매출"};
		String[][] contents = new String[][] {};
		
		GroupDate key = new GroupDate(date+"-01");
		GroupDate searched = sales_items_grouped_date.find(key);
		
		int sum = 0;

		if (searched != null) {
			
			TreeNode<SalesItem> root = searched.getItems().getRoot();
			
			Stack<TreeNode<SalesItem>> stack = new Stack<>();
			
			TreeNode<SalesItem> temp = root;
			
			
			// 중위 순회
			while (true) {
				while (temp != null) {
					stack.push(temp);
					temp = temp.getLeft();
				}
				
				temp = stack.pop();
				if (temp == null) break;
				
				/* 실행부분 */
				
				// 날짜별로 테이블 만듬
				String[][] _row = temp.getData().toRow();
				
				// 매출 계산
				for (int i=0; i<_row.length; ++i) {
					int sales = Integer.parseInt(_row[i][4]);
					sum += sales;
				}
				
				// 날짜별로 테이블 만듬
				contents = GroupDate.append(contents, temp.getData().toRow());
				
				/*****************************/
				
				temp = temp.getRight();
			}
			
		}
		
		contents = GroupDate.append(contents, new String[][] {{
			null, null, null, null, String.valueOf(sum)
		}});
		
		JTable table = new JTable(contents, attributes);
		table.setEnabled(false);
		return table;
	}
	
	private void updateTable() {
		
		String date = date_label.getText();
		JTable table = createTable(date);
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
		GroupDate key = new GroupDate(date);
		GroupDate searched = sales_items_grouped_date.find(key);
		
		// 찾아진게 없다면 삽입
		if (searched == null) {
			key.insertToSalesItems(date, type, price, amount);
			sales_items_grouped_date.insert(key);
		}
		// 찾아진게 있다면 안에 있는 트리에 삽입
		else {
			searched.insertToSalesItems(date, type, price, amount);
		}
	}
	
	private void load() {
		
		System.out.println("나의 상대경로는 " + new File("").getAbsolutePath());
		File file = new File(".\\src\\data\\sales.txt");
		
		try {
			FileReader reader_f = new FileReader(file);
			BufferedReader reader;
			reader = new BufferedReader(reader_f);
		
			String current = null;
			
			// 파일의 끝까지 검사
			while ((current = reader.readLine()) != null) {
				String[] token = current.split(" ");	// 날짜, 품목, 단가, 개수로 분리
				
				String date = token[0];
				String type = token[1];
				String price = token[2];
				String amount = token[3];
				
				insertToGroupDate(date, type, price, amount);
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
