package Main;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DataStructure.LinkedList;
import Money.Money;
import Operator.Opeartor;
import Operator.Password;

public class Vender extends JFrame {
	
	public static Vender vender = null;
	
	/* ��� */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CHANGE_AMOUNT = 5;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	public static final int ITEM_LABEL_HEIGHT = 15;
	
	public static final int PRODUCT_GAP = 10;
	
	private static final int INPUT_BILL_LIMIT = 3000;
	private static final int INPUT_FULL_LIMIT = 5000;

	private static final String DEFAULT_PASSWORD = "1@000000";
	
	public static final Font DEFAUlT_FONT = new Font("���� ���", Font.PLAIN, 12);
	
	/* ����� */
	private User user;
	
	// ���� ��� ����Ʈ ���߿� ť�� �����ؾߵ�
	private LinkedList<Beverage> beverages;
	private LinkedList<Money> money;
	private LinkedList<Money> inputed;
	
	/* GUI ������Ʈ */
	private JPanel contentPane;
	private JLabel inputed_money_label;
	private LinkedList<JButton> input_money_buttons;
	private LinkedList<JLabel> input_money_labels;
	private LinkedList<ItemInfo> item_info_components;
	private JPanel input_money_pannel;
	private JLabel can_change_label;
	
	/* ���Ǳ� ������ ��й�ȣ */
	private Password password;
	
	
	private static void create() {
		if (vender == null)
			vender = new Vender();
	}
	
	private Vender() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 374, 32);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("���Ǳ�");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		input_money_pannel = new JPanel();
		input_money_pannel.setBounds(12, 385, 374, 66);
		contentPane.add(input_money_pannel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(291, 238, 93, 66);
		contentPane.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("\uC785\uB825\uB41C \uAE08\uC561");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		inputed_money_label = new JLabel("0\uC6D0");
		inputed_money_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(inputed_money_label);
		inputed_money_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("\uBC18\uD658");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnMoney();
				displayInputedMoney();
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnNewButton);
		
		JPanel item_display_panel = new JPanel();
		item_display_panel.setBounds(12, 85, 372, 133);
		contentPane.add(item_display_panel);
		
		// ���� �ʱ�ȭ
		initUser();
		
		// ���� ��� �ʱ�ȭ
		initBeverages();
		
		// ������ ���� ���� ����Ʈ �ʱ�ȭ
		initItemInfoComponent(item_display_panel);
		
		JPanel can_change_panel = new JPanel();
		can_change_panel.setBounds(291, 304, 93, 32);
		contentPane.add(can_change_panel);
		
		can_change_label = new JLabel("");
		can_change_label.setHorizontalAlignment(SwingConstants.CENTER);
		can_change_panel.add(can_change_label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(291, 343, 93, 38);
		contentPane.add(panel_1);
		
		JButton btnNewButton_1 = new JButton("\uAD00\uB9AC\uC790");
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleOperatorButton();
			}
		});
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label);
		
		password = new Password(DEFAULT_PASSWORD);
		
		// �Ž����� �ʱ�ȭ
		initMoney();
		
		// �Է� �ݾ� �ʱ�ȭ
		initInputedMoney();
		
		// �ݾ� �Է� ��ư ����
		createInputMoneyButtons();
		
		createInputMoneyLabels();
		
		// �� �Է� ��ư �̺�Ʈ �߰�
		initInputMoneyButtons();
		
		// ���Ǳ� GUI ǥ��
		setVisible(true);
	}
	
	private void initUser() {
		user = new User();
	}
	
	/* ������ ������ �߰��� ��, ��ư�� ������Ʈ�� ���� ����Ʈ �ʱ�ȭ */
	private void initItemInfoComponent(JPanel item_display_panel) {
		item_info_components = new LinkedList<>();
		for (int i=0; i<beverages.size(); ++i) {
			Beverage b = beverages.get(i);
			ItemInfo info = new ItemInfo(this, item_display_panel, b);
			item_info_components.add(info);
		}
	}
	
	public LinkedList<Money> getInputedMoney() {
		return inputed;
	}
	
	public LinkedList<Money> getMoney() {
		return money;
	}
	
	private void updateItemInfoComponent() {
		ItemInfo.updateAll(item_info_components);
	}
	
	/* �Է� �ݾ� ��� */
	public void displayInputedMoney() {
		int has = Money.getFullPrice(inputed);
		String result = String.format("%,3d��", has);
		
		inputed_money_label.setText(result);
		System.out.printf("display: %s\n", result);
	}
	
	private boolean addInputMoney(int value) {
		int bill = Money.getBillPrice(inputed);
		int full = Money.getFullPrice(inputed);
		
		System.out.printf("%d %d %d\n", full+value, bill, value);
		if (full + value > INPUT_FULL_LIMIT
				|| (bill >= INPUT_BILL_LIMIT
					&& value >= 1000))
			return false;
		
		Money.addMoney(inputed, value);
		return true;
	}
	
	private void displayUserMoney() {
		int size = input_money_labels.size();
		LinkedList<Money> money = user.getMoney();
		for (int i=0; i<size; ++i) {
			JLabel label = input_money_labels.get(i);
			int amount = Money.getMoneyAmount(money, Money.money_unit[i]);
			label.setText(String.valueOf(amount));
		}
	}
	
	private void displayChangeMoney(boolean value) {
		//int money = Money.getFullPrice(money);
		
		if (value) {
			can_change_label.setText("�Ž�����X");
		} else {
			can_change_label.setText("");
		}
	}
	
	/* �ݾ� �Է� ��ư Ŭ�� �� �̺�Ʈ */
	private void handleInputMoney(int value) {
		if (user.hasMoney(value) && addInputMoney(value)) {
			user.takeMoney(value);
			displayInputedMoney();
			displayUserMoney();
			updateItemInfoComponent();
		}
		else
			System.out.println("�߰��� �� �����");
	}
	
	private void returnMoney() {
		for (;inputed.size() > 0;) {
			Money m = inputed.remove(0);
			if (m.getAmount() <= 0) continue;
			System.out.printf("���� %d %d\n", m.getPrice(), m.getAmount());
			
			LinkedList<Money> user_money = user.getMoney();
			Money.addMoney(user_money, m);
			
			displayUserMoney();
		}
		updateItemInfoComponent();
		initInputedMoney();
	}
	
	////////////////////////////////////////////////////
	
	/* ���� ��� �ʱ�ȭ(default) */
	
	private void initBeverages() {
		beverages = new LinkedList<>();
		Beverage b;
		String[] paths = new String[] {
			"chamdasu.jpg", "coffee.jpg", "pocari.jpg", "classy_coffee.jpg", "cola.jpg"	
		};
		String[] types = new String[] {
				"��", "Ŀ��", "�̿�����", "���Ŀ��", "ź������"
		};
		Integer[] prices = new Integer[] {
				450, 500, 550, 700, 750
		};
		for (int i=0; i<types.length; ++i) {
			b = new Beverage(paths[i], types[i], prices[i]);
			beverages.add(b);
		}
	}
	
	/* �Ž����� �ʱ�ȭ(default) */
	private void initMoney() {
		money = new LinkedList<>();
		Money.initMoneyList(money, DEFAULT_CHANGE_AMOUNT);
		
		System.out.printf("���Ǳ�� �� %,3d��b �� ������ �ֽ��ϴ�\n", Money.getFullPrice(money));
	}
	
	/* �Է� �ݾ� �ʱ�ȭ */
	private void initInputedMoney() {
		inputed = new LinkedList<>();
		Money.initMoneyList(inputed, 0);
		
		System.out.printf("�Է±ݾ� �� %,3d��b �� ������ �ֽ��ϴ�\n", Money.getFullPrice(inputed));
	}
	
	/* �ݾ� �Է� ��ư ���� */
	private void createInputMoneyButtons() {
		
		if (input_money_buttons == null)
			input_money_buttons = new LinkedList<>();
		
		for (int b : Money.money_unit) {
			JButton btn = new JButton(String.valueOf(b));
			input_money_buttons.add(btn);
		}
	}
	
	/* �ݾ� �Է� ��ư �� ����� ���� ���� �� ���� */
	private void createInputMoneyLabels() {
		
		if (input_money_labels == null)
			input_money_labels = new LinkedList<>();
		
		LinkedList<Money> money = user.getMoney();
		
		for (int unit : Money.money_unit) {
			int amount = Money.getMoneyAmount(money, unit);
			JLabel label = new JLabel(String.valueOf(amount));
			input_money_labels.add(label);
		}
	}
	
	public LinkedList<ItemInfo> getItemInfoes() {
		return item_info_components;
	}
	
	public void buy(int price) {
		int change = Money.getFullPrice(inputed) - price;
		System.out.println("�Ž������� " + change);
		
		LinkedList<Money> price_money = Money.subMoney(inputed, price);
		
		System.out.printf("[1] money: %d\n", Money.getFullPrice(money));
		
		if (Money.getFullPrice(price_money) != price) {
			Money.addChange(money, price);
			System.out.println("aaaaaaaaaa "+price);
		}
		else
			Money.addMoney(money, price_money);		// ���Ḧ �����Ƿ� ���ǱⰡ ���� ����
		
		System.out.printf("[2] money: %d\n", Money.getFullPrice(money));
		Money.subMoney(money, change);	// �Ž������� ���Ǳ⿡�� ��, money �� �� �߰��ϰ� ���� �ͺ��� ���ݿ��� �Ž����� �� ���� ���°� ȿ����
		System.out.printf("[3] money: %d\n", Money.getFullPrice(money));
		
		initInputedMoney();
		Money.addChange(inputed, change);	// �Է±ݾ׿� �Ž����� �־���
		displayInputedMoney();
	}
	
	public boolean canBuy(Beverage b) {
		if (inputed == null) return false;
		int full = Money.getFullPrice(inputed);			// �Էµ� �ݾ��� �����ϴ��� üũ
		return full >= b.getPrice() && canChange(b);
	}
	
	public boolean canChange(Beverage b) {
		
		boolean can = false;
		do {
			if (money == null) break;
			
			int full = Money.getFullPrice(inputed);		// �Էµ� �ݾ��� �����ϴ��� üũ
			if (full < b.getPrice()) break;
			
			int full_change = Money.getFullPrice(money) + b.getPrice();	// ���Ǳ⿡ �Ž����� �ִ��� üũ
			
			if (full_change < (full - b.getPrice())) break;
			
			can = true;
			break;
		} while(true);
		
		displayChangeMoney(!can);
		return can;
	}
	
	/* �ݾ� �Է� ��ư �̺�Ʈ �߰� */
	private void initInputMoneyButtons() {
		if (input_money_buttons != null) {
			
			for (int i=0; i<input_money_buttons.size(); ++i) {
				JButton btn = input_money_buttons.get(i);
				
				btn.addActionListener(new ActionListener() {
					final int money = Integer.parseInt(btn.getText());
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(money);
						handleInputMoney(money);
					}
				});
				
				btn.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel label = input_money_labels.get(i);
				label.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.add(btn);
				panel.add(input_money_labels.get(i));
				input_money_pannel.add(panel);
			}
			
		}
	}
	
	/////////////////////////////////////////////
	
	private void handleOperatorButton() {
		
		boolean isCorrect = true;
		do {
			int result = showPasswordInput(isCorrect);
			if (result < 0)
				isCorrect = false;
			else if (result == 0)
				return;
		}
		while (!isCorrect);
		
		Opeartor operator = new Opeartor();
		setContentPane(operator);
		revalidate();
		repaint();
	}
	
	private int showPasswordInput(boolean isCorrect) {
		JPanel panel = new JPanel();
		
		String msg = isCorrect ? "������ ��й�ȣ�� �Է����ּ���." : "�˸��� ��й�ȣ�� �Է����ּ���.";
		JLabel label = new JLabel(msg);
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"Ȯ��", "���"};
		int option = JOptionPane.showOptionDialog(null, panel, "������ ��й�ȣ �Է�",
		                            JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                            null, options, options[1]);
		
		if(option == 0) // Ȯ�ι�ư ������ ��
		{
		       String password = new String(pass.getPassword());
		       
		       if (this.password.equals(password))
		    	   return 1;
		       else
		    	   return -1;
		}
		
		return 0;
	}
	
	public Password getPassword() {
		return password;
	}
	
	/////////////////////////////////////////////
	
	public JPanel getContentPane() {
		return contentPane;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vender.create();		// ���Ǳ� â ����
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
