package Main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DataStructure.LinkedList;

public class Vender extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// 음료 목록 리스트 나중에 큐로 구현해야됨
	private LinkedList<Beverage> beverages;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	public static final int ITEM_LABEL_HEIGHT = 15;
	
	private static final int PRODUCT_START_X = 12;
	private static final int PRODUCT_START_Y = 60;
	public static final int PRODUCT_GAP = 10;
	
	public static final Font DEFAUlT_FONT = new Font("맑은 고딕", Font.PLAIN, 12);
	
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
		
		// 음료 목록 초기화
		initBeverages();
		
		for (int i=0; i<beverages.size(); ++i) {
			Beverage b = beverages.get(i);
			display(contentPane, b, i);
		}
		
		setVisible(true);
	}
	
	
	/* 자판기 창 라벨 설정 */
	private void display(JPanel contentPane, Beverage b, int i) {
		JLabel before;
		
		int item_x = (i+1) * 12 + (i * PRODUCT_IMAGE_WIDTH);
		int item_y = PRODUCT_START_Y;
		
		// 음료 이름
		JLabel item_name = getNameLabel(b, item_x, item_y);
		contentPane.add(item_name);
		
		// 라벨 간의 간격 설정
		before = item_name;
		item_y += getGap(before);
		
		// 음료 이미지 (입력 금액 충족 시 이미지 border 생성)
		JLabel item_image = getImageLabel(b, item_x, item_y);
		contentPane.add(item_image);
		
		// 라벨 간의 간격 설정
		before = item_image;
		item_y += getGap(before);
		
		// 음료 가격
		JLabel item_price = getPriceLabel(b, item_x, item_y);
		contentPane.add(item_price);
		
		// 라벨 간의 간격 설정
		before = item_price;
		item_y += getGap(before);
		
		// 구매 버튼 (품절 표시 기능 포함)
		JButton item_buy_btn = getBuyButton(b, item_x, item_y);
		contentPane.add(item_buy_btn);
	}
	
	/* 라벨 간의 간격 반환 */
	private int getGap(JLabel before) {
		return PRODUCT_GAP + before.getHeight();
	}
	
	/* 음료 정보 받아 종류 라벨 생성 */
	private JLabel getNameLabel(Beverage b, int x, int y) {
		JLabel item_name = new JLabel();
		item_name.setText(b.getType());
		item_name.setHorizontalAlignment(SwingConstants.CENTER);
		item_name.setFont(DEFAUlT_FONT);
		item_name.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		return item_name;
	}
	
	/* 음료 정보 받아 이미지 생성 */
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
	
	/* 음료 정보 받아 가격 라벨 생성 */
	private JLabel getPriceLabel(Beverage b, int x, int y) {
		JLabel item_price = new JLabel();
		String price_text = String.valueOf(b.getPrice()) + "원";
		item_price.setText(price_text);
		item_price.setHorizontalAlignment(SwingConstants.CENTER);
		item_price.setFont(DEFAUlT_FONT);
		item_price.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		return item_price;
	}
	
	/* 음료 정보 받아 구매 버튼 생성 */
	private JButton getBuyButton(Beverage b, int x, int y) {
		JButton item_buy_btn = new JButton("구매");
		item_buy_btn.setHorizontalAlignment(SwingConstants.CENTER);
		item_buy_btn.setFont(DEFAUlT_FONT);
		item_buy_btn.setBounds(x, y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
		item_buy_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.printf("%s: %d원\n", b.getType(), b.getPrice());
			}
		});
		return item_buy_btn;
	}
	
	/* 경로로부터 ImageIcon 을 가져옴. */
	private ImageIcon getImageIcon(String path) {
		path = String.format("../images/%s", path);
		URL url = this.getClass().getResource(path);
		
		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		
		img = img.getScaledInstance(PRODUCT_IMAGE_WIDTH, PRODUCT_IMAGE_HEIGHT, Image.SCALE_SMOOTH);	// 칸에 맞춰 이미지 사이즈 조절
		icon = new ImageIcon(img);
		
		return icon;
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
			ImageIcon icon = getImageIcon(paths[i]);
			b = new Beverage(icon, types[i], prices[i]);
			beverages.add(b);
		}
	}
	
	/* 자판기에 입력된 돈으로 음료를 살 수 있는지 여부 반환 */
	private boolean canBuy(Beverage b) {
		int money = 500;
		int price = b.getPrice();
		return money >= price;
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
