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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DataStructure.LinkedList;
import Money.Money;

public class Vender extends JFrame {
	
	/* ��� */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CHANGE_AMOUNT = 5;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	public static final int ITEM_LABEL_HEIGHT = 15;
	
	public static final int PRODUCT_GAP = 10;
	
	private static final int INPUT_BILL_LIMIT = 3000;
	private static final int INPUT_FULL_LIMIT = 5000;
	
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
	
	
	public Vender() {
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
		Money.initMoneyList(money, 5);
		
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
	
	public void buy() {
		
	}
	
	public boolean canBuy(Beverage b) {
		if (inputed == null) return false;
		int full = Money.getFullPrice(inputed);
		return full >= b.getPrice();
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
	
	/* �ݾ� �Է� ��ư */
	public void handleInputMoney() {
		
	}
	
	/////////////////////////////////////////////
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Vender();		// ���Ǳ� â ����
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
