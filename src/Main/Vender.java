package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DataStructure.LinkedList;
import Money.Bill;
import Money.Coin;
import Money.Money;

public class Vender extends JFrame {
	
	/* ��� */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CHANGE_AMOUNT = 5;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	public static final int ITEM_LABEL_HEIGHT = 15;
	
	private static final int PRODUCT_START_X = 12;
	private static final int PRODUCT_START_Y = 60;
	public static final int PRODUCT_GAP = 10;
	
	private static final int INPUT_BILL_LIMIT = 3000;
	private static final int INPUT_FULL_LIMIT = 5000;
	
	public static final Font DEFAUlT_FONT = new Font("���� ���", Font.PLAIN, 12);
	
	// ���� ��� ����Ʈ ���߿� ť�� �����ؾߵ�
	private LinkedList<Beverage> beverages;
	private LinkedList<Money> money;
	private LinkedList<Money> inputed;
	
	/* GUI ������Ʈ */
	private JPanel contentPane;
	private JLabel inputed_money_label;
	private LinkedList<JButton> input_money_buttons;
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
		panel_2.setBounds(291, 226, 93, 66);
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
				displayInputedMoney();
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnNewButton);
		
		// ���� ��� �ʱ�ȭ
		initBeverages();
		
		// �Ž����� �ʱ�ȭ
		initMoney();
		
		// �ݾ� �Է� ��ư ����
		createInputMoneyButtons();
		
		// �� �Է� ��ư �̺�Ʈ �߰�
		initInputMoneyButtons();
		
		for (int i=0; i<beverages.size(); ++i) {
			Beverage b = beverages.get(i);
			display(contentPane, b, i);
		}
		
		setVisible(true);
	}
	
	/* ���Ǳ� â �� ���� */
	private void display(JPanel contentPane, Beverage b, int i) {
		JLabel before;
		
		int item_x = (i+1) * 12 + (i * PRODUCT_IMAGE_WIDTH);
		int item_y = PRODUCT_START_Y;
		
		// ���� �̸�
		JLabel item_name = getNameLabel(b, item_x, item_y);
		contentPane.add(item_name);
		
		// �� ���� ���� ����
		before = item_name;
		item_y += getGap(before);
		
		// ���� �̹��� (�Է� �ݾ� ���� �� �̹��� border ����)
		JLabel item_image = getImageLabel(b, item_x, item_y);
		contentPane.add(item_image);
		
		// �� ���� ���� ����
		before = item_image;
		item_y += getGap(before);
		
		// ���� ����
		JLabel item_price = getPriceLabel(b, item_x, item_y);
		contentPane.add(item_price);
		
		// �� ���� ���� ����
		before = item_price;
		item_y += getGap(before);
		
		// ���� ��ư (ǰ�� ǥ�� ��� ����)
		JButton item_buy_btn = getBuyButton(b, item_x, item_y);
		contentPane.add(item_buy_btn);
	}
	
	/* �� ���� ���� ��ȯ */
	private int getGap(JLabel before) {
		return PRODUCT_GAP + before.getHeight();
	}
	
	/* ���� ���� �޾� ���� �� ���� */
	private JLabel getNameLabel(Beverage b, int x, int y) {
		JLabel item_name = new JLabel();
		item_name.setText(b.getType());
		item_name.setHorizontalAlignment(SwingConstants.CENTER);
		item_name.setFont(DEFAUlT_FONT);
		item_name.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		return item_name;
	}
	
	/* ���� ���� �޾� �̹��� ���� */
	private JLabel getImageLabel(Beverage b, int x, int y) {
		JLabel item_image = new JLabel();
		
		if (canBuy(b)) {
			LineBorder border = new LineBorder(Color.red, 1, true);
			item_image.setBorder(border);			
		}
		
		item_image.setIcon(b.getIcon());
		item_image.setBounds(x, y, PRODUCT_IMAGE_WIDTH, PRODUCT_IMAGE_HEIGHT);
		return item_image;
	}
	
	/* ���� ���� �޾� ���� �� ���� */
	private JLabel getPriceLabel(Beverage b, int x, int y) {
		JLabel item_price = new JLabel();
		String price_text = String.valueOf(b.getPrice()) + "��";
		item_price.setText(price_text);
		item_price.setHorizontalAlignment(SwingConstants.CENTER);
		item_price.setFont(DEFAUlT_FONT);
		item_price.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		return item_price;
	}
	
	/* ���� ���� �޾� ���� ��ư ���� */
	private JButton getBuyButton(Beverage b, int x, int y) {
		JButton item_buy_btn = new JButton("����");
		item_buy_btn.setHorizontalAlignment(SwingConstants.CENTER);
		item_buy_btn.setFont(DEFAUlT_FONT);
		item_buy_btn.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		item_buy_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.printf("%s: %d��\n", b.getType(), b.getPrice());
			}
		});
		return item_buy_btn;
	}
	
	/* ��ηκ��� ImageIcon �� ������. */
	private ImageIcon getImageIcon(String path) {
		path = String.format("../images/%s", path);
		URL url = this.getClass().getResource(path);
		
		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		
		img = img.getScaledInstance(PRODUCT_IMAGE_WIDTH, PRODUCT_IMAGE_HEIGHT, Image.SCALE_SMOOTH);	// ĭ�� ���� �̹��� ������ ����
		icon = new ImageIcon(img);
		
		return icon;
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
		
		if (full + value > INPUT_FULL_LIMIT
				|| (bill >= INPUT_BILL_LIMIT
					&& value >= 1000))
			return false;
		
		Money.addMoney(inputed, value);
		return true;
	}
	
	/* �ݾ� �Է� ��ư Ŭ�� �� �̺�Ʈ */
	private void handleInputMoney(int value) {
		if (addInputMoney(value))
			displayInputedMoney();
		else
			System.out.println("�߰��� �� �����");
	}
	
	private void returnMoney() {
		inputed = new LinkedList<>();
		
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
			ImageIcon icon = getImageIcon(paths[i]);
			b = new Beverage(icon, types[i], prices[i]);
			beverages.add(b);
		}
	}
	
	/* �Ž����� �ʱ�ȭ(default) */
	
	private void initMoney() {
		
		money = new LinkedList<>();
		inputed = new LinkedList<>();
		
		int[] money_unit = new int[] {
				10, 50, 100, 500, 1000
		};
		
		for (int i=0; i<money_unit.length; ++i) {
			Money m = createMoneyWithType(money_unit[i], DEFAULT_CHANGE_AMOUNT);
			Money m2 = createMoneyWithType(money_unit[i], 0);
			money.add(m);
			inputed.add(m2);
		}
		
		System.out.printf("���Ǳ�� �� %,3d��b �� ������ �ֽ��ϴ�\n", Money.getFullPrice(money));
	}
	
	private void initInputedMoney() {
		
	}
	
	private Money createMoneyWithType(int price, int amount) {
		Money m;
		if (price >= 1000)
			m = new Bill(price, amount);
		else
			m = new Coin(price, amount);
		return m;
	}
	
	/* �ݾ� �Է� ��ư ���� */
	private void createInputMoneyButtons() {
		int[] buttons = {
			10, 50, 100, 500, 1000	
		};
		
		if (input_money_buttons == null)
			input_money_buttons = new LinkedList<>();
		
		for (int b : buttons) {
			JButton btn = new JButton(String.valueOf(b));
			input_money_buttons.add(btn);
		}
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
				
				input_money_pannel.add(btn);
			}
		}
	}
	
	/* ���Ǳ⿡ �Էµ� ������ ���Ḧ �� �� �ִ��� ���� ��ȯ */
	private boolean canBuy(Beverage b) {
		int money = 500;
		int price = b.getPrice();
		return money >= price;
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
