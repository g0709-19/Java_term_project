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
	
	/* 상수 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CHANGE_AMOUNT = 5;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	public static final int ITEM_LABEL_HEIGHT = 15;
	
	public static final int PRODUCT_GAP = 10;
	
	private static final int INPUT_BILL_LIMIT = 3000;
	private static final int INPUT_FULL_LIMIT = 5000;
	
	public static final Font DEFAUlT_FONT = new Font("맑은 고딕", Font.PLAIN, 12);
	
	/* 사용자 */
	private User user;
	
	// 음료 목록 리스트 나중에 큐로 구현해야됨
	private LinkedList<Beverage> beverages;
	private LinkedList<Money> money;
	private LinkedList<Money> inputed;
	
	/* GUI 컴포넌트 */
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
		
		JLabel lblNewLabel = new JLabel("자판기");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
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
		
		// 유저 초기화
		initUser();
		
		// 음료 목록 초기화
		initBeverages();
		
		// 아이템 정보 담을 리스트 초기화
		initItemInfoComponent(item_display_panel);
		
		// 거스름돈 초기화
		initMoney();
		
		// 입력 금액 초기화
		initInputedMoney();
		
		// 금액 입력 버튼 생성
		createInputMoneyButtons();
		
		createInputMoneyLabels();
		
		// 돈 입력 버튼 이벤트 추가
		initInputMoneyButtons();
		
		// 자판기 GUI 표시
		setVisible(true);
	}
	
	private void initUser() {
		user = new User();
	}
	
	/* 아이템 정보에 추가될 라벨, 버튼의 컴포넌트를 담을 리스트 초기화 */
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
	
	/* 입력 금액 출력 */
	public void displayInputedMoney() {
		int has = Money.getFullPrice(inputed);
		String result = String.format("%,3d원", has);
		
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
	
	/* 금액 입력 버튼 클릭 시 이벤트 */
	private void handleInputMoney(int value) {
		if (user.hasMoney(value) && addInputMoney(value)) {
			user.takeMoney(value);
			displayInputedMoney();
			displayUserMoney();
			updateItemInfoComponent();
		}
		else
			System.out.println("추가할 수 없어요");
	}
	
	private void returnMoney() {
		for (;inputed.size() > 0;) {
			Money m = inputed.remove(0);
			if (m.getAmount() <= 0) continue;
			System.out.printf("인출 %d %d\n", m.getPrice(), m.getAmount());
			
			LinkedList<Money> user_money = user.getMoney();
			Money.addMoney(user_money, m);
			
			displayUserMoney();
		}
		updateItemInfoComponent();
		initInputedMoney();
	}
	
	////////////////////////////////////////////////////
	
	/* 음료 재고 초기화(default) */
	
	private void initBeverages() {
		beverages = new LinkedList<>();
		Beverage b;
		String[] paths = new String[] {
			"chamdasu.jpg", "coffee.jpg", "pocari.jpg", "classy_coffee.jpg", "cola.jpg"	
		};
		String[] types = new String[] {
				"물", "커피", "이온음료", "고급커피", "탄산음료"
		};
		Integer[] prices = new Integer[] {
				450, 500, 550, 700, 750
		};
		for (int i=0; i<types.length; ++i) {
			b = new Beverage(paths[i], types[i], prices[i]);
			beverages.add(b);
		}
	}
	
	/* 거스름돈 초기화(default) */
	private void initMoney() {
		money = new LinkedList<>();
		Money.initMoneyList(money, 5);
		
		System.out.printf("자판기는 총 %,3d원b 을 가지고 있습니다\n", Money.getFullPrice(money));
	}
	
	/* 입력 금액 초기화 */
	private void initInputedMoney() {
		inputed = new LinkedList<>();
		Money.initMoneyList(inputed, 0);
		
		System.out.printf("입력금액 총 %,3d원b 을 가지고 있습니다\n", Money.getFullPrice(inputed));
	}
	
	/* 금액 입력 버튼 생성 */
	private void createInputMoneyButtons() {
		
		if (input_money_buttons == null)
			input_money_buttons = new LinkedList<>();
		
		for (int b : Money.money_unit) {
			JButton btn = new JButton(String.valueOf(b));
			input_money_buttons.add(btn);
		}
	}
	
	/* 금액 입력 버튼 밑 사용자 보유 개수 라벨 생성 */
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
	
	/* 금액 입력 버튼 이벤트 추가 */
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
	
	/* 금액 입력 버튼 */
	public void handleInputMoney() {
		
	}
	
	/////////////////////////////////////////////
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Vender();		// 자판기 창 생성
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
